package com.e_garden.api.stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import jakarta.annotation.PreDestroy;

import java.io.File;
import java.io.IOException;
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
        if (currentProcess != null && currentProcess.isAlive()) {
            currentProcess.destroyForcibly();
            try {
                boolean terminated = currentProcess.waitFor(5, TimeUnit.SECONDS);
                if (terminated) {
                    logger.info("Stream FFmpeg arrêté avec succès lors de l'arrêt du serveur.");
                } else {
                    logger.warning("Le stream FFmpeg n'a pas pu être arrêté proprement lors de l'arrêt du serveur.");
                }
            } catch (InterruptedException e) {
                logger.severe("Erreur lors de l'arrêt du stream FFmpeg pendant l'arrêt du serveur: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Bean for managing the streaming process.
     *
     * @return the Process of the FFmpeg stream
     * @throws IOException if the FFmpeg process fails to start
     */
    @Bean
    @Lazy
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
                "-tune", "zerolatency",            // Optimisation pour la latence
                "-f", "hls",                       // Format de sortie HLS
                "-hls_time", "4",                  // Durée des segments en secondes
                "-hls_list_size", "5",             // Nombre de segments dans la playlist
                "-hls_flags", "delete_segments",   // Supprimer les anciens segments
                OUTPUT_FILE                         // Fichier de sortie
        );

        processBuilder.redirectErrorStream(true);
        currentProcess = processBuilder.start();
        logger.info("Started new FFmpeg process");
        return currentProcess;
    }
}
