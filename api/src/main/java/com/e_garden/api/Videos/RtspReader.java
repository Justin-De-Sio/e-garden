// RtspReader.java
package com.e_garden.api.Videos;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import static org.bytedeco.opencv.global.opencv_videoio.CAP_FFMPEG;

import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

@Service
public class RtspReader implements AutoCloseable {
    private static final Logger logger = Logger.getLogger(RtspReader.class.getName());
    private final AtomicBoolean capturing = new AtomicBoolean(false);
    private VideoCapture capture;
    private final Object lock = new Object();

    public void startCapture(String rtspUrl) {
        if (!capturing.compareAndSet(false, true)) {
            logger.info("Capture already in progress");
            return;
        }

        synchronized (lock) {
            try {
                capture = new VideoCapture(rtspUrl);
                capture.set(CAP_FFMPEG, 1);

                if (!capture.isOpened()) {
                    capturing.set(false);
                    throw new RuntimeException("Failed to open RTSP stream: " + rtspUrl);
                }
                logger.info("Successfully started capture for: " + rtspUrl);
            } catch (Exception e) {
                capturing.set(false);
                throw new RuntimeException("Error initializing capture: " + e.getMessage(), e);
            }
        }
    }

    public byte[] getNextFrameAsJpeg() {
        if (!capturing.get() || capture == null) {
            return null;
        }

        synchronized (lock) {
            try (Mat frame = new Mat()) {
                if (!capture.read(frame)) {
                    logger.warning("Failed to read frame");
                    return null;
                }

                if (frame.empty()) {
                    logger.warning("Empty frame received");
                    return null;
                }

                try (BytePointer buffer = new BytePointer()) {
                    if (!opencv_imgcodecs.imencode(".jpg", frame, buffer)) {
                        logger.warning("Failed to encode frame to JPEG");
                        return null;
                    }

                    byte[] jpegBytes = new byte[(int) buffer.limit()];
                    buffer.get(jpegBytes);
                    return jpegBytes;
                }
            }
        }
    }

    public void stopCapture() {
        if (capturing.compareAndSet(true, false)) {
            synchronized (lock) {
                if (capture != null) {
                    capture.release();
                    capture = null;
                }
            }
            logger.info("Capture stopped");
        }
    }

    @Override
    public void close() {
        stopCapture();
    }
}
