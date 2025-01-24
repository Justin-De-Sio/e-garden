package com.e_garden.api.stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * Configuration class for initializing the RTSP to HLS streaming process.
 */
@Configuration
public class StreamConfig {

    protected static final String OUTPUT_DIRECTORY = "videos" + File.separator + "stream";
    private static final String OUTPUT_FILE = OUTPUT_DIRECTORY  + File.separator + "output.m3u8";
    private static final String RTSP_URL = System.getenv("RTSP_URL");


    /**
     * Bean for managing the streaming process.
     *
     * @return the Process of the FFmpeg stream
     * @throws IOException if the FFmpeg process fails to start
     */
    @Bean
    public Process streamingProcess() throws IOException {
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
        return processBuilder.start();
    }
}
