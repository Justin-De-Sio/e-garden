package com.e_garden.api.videos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping("/rtsp")
public class RtspRecorderController {

    private static final String OUTPUT_DIRECTORY = "rtsp" + File.separator + "videos";

    private static final String RTSP_URL_ENV = "RTSP_URL";
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final VideoService videoService;

    @Autowired
    public RtspRecorderController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/record")
    public Response recordRtspStream(@RequestParam(required = false, defaultValue = "5") int duration) {
        String rtspUrl = System.getenv(RTSP_URL_ENV);
        if (rtspUrl == null || rtspUrl.isEmpty()) {
            return new Response(false, "RTSP_URL environment variable is not set or empty.");
        }

        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + ".mp4";
        String filePath = OUTPUT_DIRECTORY + File.separator + fileName;


        // Crée le répertoire de sortie si nécessaire
        File outputDir = new File(OUTPUT_DIRECTORY);
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                return new Response(false, "Failed to create output directory: " + OUTPUT_DIRECTORY);
            }
        }

        // Commande FFmpeg avec durée spécifiée
        String command = String.format(
                "ffmpeg -rtsp_transport tcp -timeout 10000000 -analyzeduration 100000000 -probesize 50000000 -i %s -t %d -s 2560x1920 -r 20 -c:v libx264 -preset ultrafast -c:a aac -b:a 128k -f mp4 %s",
                rtspUrl, duration, filePath
        );

        executor.submit(() -> runRecordingProcess(command, fileName, OUTPUT_DIRECTORY + File.separator + fileName));

        return new Response(true, "Recording started for " + duration + " seconds.");
    }

    @Async
    private void runRecordingProcess(String command, String fileName, String filePath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Lire les logs dans un thread séparé
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            // Attendre la fin du processus avec un timeout
            boolean finished = process.waitFor(120, TimeUnit.SECONDS); // Timeout de 120 secondes
            if (!finished) {
                process.destroy(); // Forcer l'arrêt si le timeout est atteint
                System.err.println("Recording process timed out.");

            }

            int exitCode = process.exitValue();
            if (exitCode == 0) {
                System.out.println("Recording completed successfully! Saved to: " + fileName);
                Video video = new Video(fileName, filePath);
                videoService.saveVideo(video);

            } else {
                System.err.println("Recording failed with exit code: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred during the recording: " + e.getMessage());
        }
    }

    // Classe pour une réponse JSON standardisée
    private static class Response {
        private final boolean success;
        private final String message;

        public Response(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}
