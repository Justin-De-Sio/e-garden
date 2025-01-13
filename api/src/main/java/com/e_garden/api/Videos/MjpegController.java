package com.e_garden.api.Videos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/stream")
public class MjpegController {
    private static final Logger logger = Logger.getLogger(MjpegController.class.getName());
    private static final String FRAME_BOUNDARY = "--frame";
    private static final String CONTENT_TYPE_HEADER = "Content-Type: image/jpeg";
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;

    @Autowired
    private RtspReader rtspReader;

    private final AtomicBoolean isStreaming = new AtomicBoolean(false);

    @GetMapping("/start")
    public ResponseEntity<String> startStream() {
        if (!isStreaming.compareAndSet(false, true)) {
            return ResponseEntity.badRequest().body("Stream is already running");
        }

        String streamUrl = System.getenv("RTSP_URL");
        if (streamUrl == null || streamUrl.trim().isEmpty()) {
            isStreaming.set(false);
            return ResponseEntity.badRequest().body("No RTSP URL provided");
        }
        logger.info("Starting validation for RTSP URL: " + streamUrl);

        try {
            /*
            if (!validateRtspStream(streamUrl)) {
                isStreaming.set(false);
                return ResponseEntity.badRequest().body("Invalid RTSP stream URL");
            }


             */
            rtspReader.startCapture(streamUrl);
            logger.info("Stream started for: " + streamUrl);
            return ResponseEntity.ok("Capture started for: " + streamUrl);
        } catch (Exception e) {
            isStreaming.set(false);
            logger.severe("Failed to start stream: " + e.getMessage());
            return ResponseEntity.internalServerError()
                    .body("Failed to start stream: " + e.getMessage());
        }
    }

    @GetMapping("/stop")
    public ResponseEntity<String> stopStream() {
        if (!isStreaming.compareAndSet(true, false)) {
            return ResponseEntity.badRequest().body("No active stream to stop");
        }

        try {
            rtspReader.stopCapture();
            logger.info("Stream stopped successfully");
            return ResponseEntity.ok("Capture stopped successfully");
        } catch (Exception e) {
            logger.severe("Failed to stop stream: " + e.getMessage());
            return ResponseEntity.internalServerError()
                    .body("Failed to stop stream: " + e.getMessage());
        }
    }

    @GetMapping(path = "/video", produces = MediaType.IMAGE_JPEG_VALUE)
    public void streamVideo(HttpServletResponse response) {
        if (!isStreaming.get()) {
            try {
                response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "No active stream");
            } catch (IOException e) {
                logger.severe("Error sending response: " + e.getMessage());
            }
            return;
        }

        configureResponse(response);

        int retryCount = 0;
        while (isStreaming.get()) {
            try {
                if (!writeFrame(response)) {
                    if (++retryCount > MAX_RETRIES) {
                        logger.severe("Max retries exceeded for frame writing");
                        break;
                    }
                    TimeUnit.MILLISECONDS.sleep(RETRY_DELAY_MS);
                    continue;
                }
                retryCount = 0;
            } catch (IOException e) {
                logger.info("Client disconnected: " + e.getMessage());
                break;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warning("Stream interrupted: " + e.getMessage());
                break;
            }
        }
    }

    private void configureResponse(HttpServletResponse response) {
        response.setContentType("multipart/x-mixed-replace; boundary=frame");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
    }

    private boolean validateRtspStream(String rtspUrl) {
        try {
            Process process = new ProcessBuilder("ffmpeg", "-i", rtspUrl,
                    "-t", "1", "-f", "null", "-")
                    .redirectErrorStream(true)
                    .start();

            boolean valid = process.waitFor(20, TimeUnit.SECONDS) && process.exitValue() == 0;
            process.destroyForcibly();
            return valid;
        } catch (Exception e) {
            logger.warning("Stream validation failed: " + e.getMessage());
            return false;
        }
    }

    private boolean writeFrame(HttpServletResponse response) throws IOException {
        byte[] jpegData = rtspReader.getNextFrameAsJpeg();
        if (jpegData == null || jpegData.length == 0) {
            return false;
        }

        try {
            response.getOutputStream().write((FRAME_BOUNDARY + "\r\n").getBytes());
            response.getOutputStream().write((CONTENT_TYPE_HEADER + "\r\n\r\n").getBytes());
            response.getOutputStream().write(jpegData);
            response.getOutputStream().write("\r\n".getBytes());
            response.flushBuffer();
            return true;
        } catch (IOException e) {
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error writing frame");
            }
            throw e;
        }
    }
}