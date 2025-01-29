package com.e_garden.api.videos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Service principal pour gérer les vidéos.
 * Contient les fonctionnalités d'enregistrement de vidéos via RTSP
 * et de gestion des fichiers enregistrés.
 */
@Service
public class VideoService {

    // Répertoire de sortie pour stocker les vidéos enregistrées
    protected static final String OUTPUT_DIRECTORY = "videos";
    private static final String RTSP_URL = System.getenv("RTSP_URL");

    private final VideoRepository videoRepository; // Accès au dépôt des vidéos
    private final FfmpegService ffmpegService;     // Service dédié à l'exécution des commandes FFmpeg

    /**
     * Constructeur avec injection des dépendances.
     *
     * @param videoRepository Référentiel JPA pour stocker les vidéos
     * @param ffmpegService   Service pour exécuter les commandes FFmpeg
     */
    @Autowired
    public VideoService(VideoRepository videoRepository, FfmpegService ffmpegService) {
        this.videoRepository = videoRepository;
        this.ffmpegService = ffmpegService;
    }


    /**
     * Planifie l'enregistrement automatique d'une vidéo de 24 heures chaque jour à minuit.
     */
    @Scheduled(cron = "0 0 0 * * *") // Exécution chaque jour à minuit
    public void scheduleDailyRecording() {
        System.out.println("Démarrage de l'enregistrement pour 24h...");
        startRecording(Duration.ofHours(24));
    }

    /**
     * Démarre l'enregistrement d'un flux RTSP pendant une durée de 24h.
     *
     * @param duration Durée de l'enregistrement (24 heures)
     * @return Réponse HTTP immédiate avec le nom du fichier
     */
    public ResponseEntity<String> startRecording(Duration duration) {
        if (RTSP_URL == null || RTSP_URL.isEmpty()) {
            return ResponseEntity.badRequest().body("RTSP_URL environment variable is not set or empty.");
        }

        // Génération d'un fichier avec la date du jour
        String fileName = LocalDate.now() + ".mp4";
        String filePath = OUTPUT_DIRECTORY + File.separator + fileName;

        // Création du répertoire de sortie
        File outputDir = new File(OUTPUT_DIRECTORY);
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            return ResponseEntity.internalServerError().body("Failed to create output directory.");
        }

        // Démarrage de l'enregistrement en arrière-plan
        startRecordingAsync(RTSP_URL, filePath, fileName, duration);

        return ResponseEntity.ok(String.format("Enregistrement démarré pour %d secondes. Fichier: %s", duration.getSeconds(), fileName));
    }

    /**
     * Exécute l'enregistrement vidéo en arrière-plan avec FFmpeg.
     */
    @Async
    protected void startRecordingAsync(String rtspUrl, String filePath, String fileName, Duration duration) {
        String command = String.format(
                "ffmpeg -rtsp_transport tcp -probesize 10000000 -analyzeduration 10000000 -i %s " +
                        "-vf \"fps=5,scale=1280:720\" -c:v libx264 -preset ultrafast -crf 30 -c:a copy -t %d -f mp4 %s",
                rtspUrl, duration.getSeconds(), filePath
        );



        ffmpegService.executeFfmpegCommand(command);
        videoRepository.save(new Video(fileName, duration));

        // Suppression des vidéos de plus de 7 jours (optionnel)
        deleteOldVideos(7);
    }

    /**
     * Récupère une vidéo en fonction de son nom de fichier.
     */
    public ResponseEntity<Resource> getVideo(String fileName) {
        try {
            Path filePath = Paths.get(OUTPUT_DIRECTORY, fileName);
            File videoFile = filePath.toFile();

            if (!videoFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Resource videoResource = new UrlResource(filePath.toUri());
            if (!videoResource.exists() || !videoResource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + videoFile.getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .body(videoResource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Supprime les vidéos de plus de `minutes` et retire les entrées de la base de données.
     */
    private void deleteOldVideos(int minutes) {
        File directory = new File(OUTPUT_DIRECTORY);
        if (!directory.exists()) return;

        File[] files = directory.listFiles();
        if (files == null) return;

        LocalDateTime cutoffDateTime = LocalDateTime.now().minusDays(minutes);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");

        for (File file : files) {
            try {
                String fileName = file.getName();

                // Vérifier que le fichier est bien un .mp4
                if (!fileName.endsWith(".mp4")) continue;

                // Vérifier que le fichier respecte le format attendu (yyyy-MM-dd_HH-mm.mp4)
                if (!fileName.matches("\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}\\.mp4")) {
                    System.err.println("❌ Fichier ignoré : " + fileName + " (format incorrect)");
                    continue;
                }

                // Extraire la date depuis le nom du fichier
                LocalDateTime fileDateTime = LocalDateTime.parse(fileName.replace(".mp4", ""), formatter);

                if (fileDateTime.isBefore(cutoffDateTime)) {
                    if (file.delete()) {
                        System.out.println("🗑️ Vidéo supprimée : " + fileName);

                        // Supprimer l'entrée associée dans la base de données
                        videoRepository.deleteByFileName(fileName);
                        System.out.println("🗃️ Entrée supprimée de la base de données : " + fileName);
                    } else {
                        System.err.println("❌ Échec de la suppression du fichier : " + fileName);
                    }
                }
            } catch (DateTimeParseException e) {
                System.err.println("❌ Erreur de format de date pour le fichier : " + file.getName());
            } catch (Exception e) {
                System.err.println("❌ Erreur lors de la suppression des vidéos : " + e.getMessage());
            }
        }
    }



    /**
     * Récupère la liste de toutes les vidéos enregistrées.
     */
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }
}
