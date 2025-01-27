package com.e_garden.api.stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import jakarta.annotation.PreDestroy;

import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Configuration class for initializing the RTSP to HLS streaming process.
 */
@Configuration
public class StreamConfig {

    private static final Logger logger = Logger.getLogger(StreamConfig.class.getName());
    protected static final String OUTPUT_DIRECTORY = "videos" + File.separator + "stream";
    private static final String OUTPUT_FILE = OUTPUT_DIRECTORY + File.separator + "output.m3u8";
    private static final String RTSP_URL = System.getenv("RTSP_URL");
    private Process currentProcess;

    @PreDestroy
    public void stopStreaming() {
        if (currentProcess != null) {
            logger.info("Attempting to stop FFmpeg streaming process...");

            // Close the process streams to release resources
            try {
                currentProcess.getOutputStream().close();
                currentProcess.getInputStream().close();
                currentProcess.getErrorStream().close();
            } catch (IOException e) {
                logger.warning("Failed to close FFmpeg process streams: " + e.getMessage());
            }

            // Destroy the process forcibly if it's still running
            if (currentProcess.isAlive()) {
                currentProcess.destroyForcibly();
                try {
                    if (currentProcess.waitFor(5, TimeUnit.SECONDS)) {
                        logger.info("FFmpeg process stopped successfully.");
                    } else {
                        logger.warning("FFmpeg process did not stop within the expected time.");
                    }
                } catch (InterruptedException e) {
                    logger.severe("Thread interrupted while stopping FFmpeg process: " + e.getMessage());
                    Thread.currentThread().interrupt(); // Restore the interrupt status
                }
            } else {
                logger.info("FFmpeg process was already stopped.");
            }
        } else {
            logger.info("No FFmpeg process was running to stop.");
        }
    }

    /**
     * Bean for managing the streaming process.
     *
     * @return the Process of the FFmpeg stream
     * @throws IOException if the FFmpeg process fails to start
     */
    @Bean
    public Process streamingProcess() throws IOException {
        if (currentProcess != null && currentProcess.isAlive()) {
            logger.info("FFmpeg process already running");
            return currentProcess;
        }

        if (RTSP_URL == null || RTSP_URL.isEmpty()) {
            throw new IllegalStateException("RTSP_URL environment variable is not set");
        }

        File outputDir = new File(OUTPUT_DIRECTORY);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
                "ffmpeg",
                "-rtsp_transport", "tcp",          // Forcer l'utilisation de TCP
                "-i", RTSP_URL,                     // URL du flux RTSP
                "-c:v", "libx264",                 // Codec vidéo
                "-preset", "ultrafast",            // Préconfiguration rapide
                "-an",
                "-tune", "zerolatency",            // Optimisation pour la latence
                "-f", "hls",                       // Format de sortie HLS
                "-hls_time", "4",                  // Durée des segments en secondes
                "-hls_list_size", "5",             // Nombre de segments dans la playlist
                "-hls_flags", "delete_segments",   // Supprimer les anciens segments
                OUTPUT_FILE                         // Fichier de sortie
        );

        processBuilder.redirectErrorStream(true);
        currentProcess = processBuilder.start();
//        startFfmpegLogReaderThread(currentProcess.getInputStream());

        logger.info("Started new FFmpeg process");
        return currentProcess;
    }

    /**
     * Reads the combined stdout/stderr stream from FFmpeg and logs it line by line.
     */
    private void startFfmpegLogReaderThread(InputStream ffmpegStream) {
        Thread logThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(ffmpegStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // You can adjust the log level or use a different logger here as needed
                    logger.info("[FFmpeg] " + line);
                }
            } catch (IOException e) {
                logger.severe("Error reading FFmpeg process output: " + e.getMessage());
            }
        }, "ffmpeg-log-reader");

        // It’s generally good practice to set this thread as a daemon
        // if you don’t need it to prevent the JVM from exiting.
        logThread.setDaemon(true);
        logThread.start();
    }
}


