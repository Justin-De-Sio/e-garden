package com.e_garden.api.videos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.UUID;


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
     * Lance l'enregistrement d'un flux RTSP pour une durée donnée de manière asynchrone.
     *
     * @param duration Durée de l'enregistrement
     * @return Réponse HTTP immédiate avec le nom du fichier
     */
    public ResponseEntity<String> startRecording(Duration duration) {
        // Vérification de l'URL RTSP

        if (RTSP_URL == null || RTSP_URL.isEmpty()) {
            return ResponseEntity.badRequest().body("RTSP_URL environment variable is not set or empty.");
        }

        // Génération d'un nom de fichier unique
        String fileName = UUID.randomUUID() + ".mp4";
        String filePath = OUTPUT_DIRECTORY + File.separator + fileName;

        // Création du répertoire de sortie
        File outputDir = new File(OUTPUT_DIRECTORY);
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            return ResponseEntity.internalServerError().body("Failed to create output directory.");
        }

        // Démarrage de l'enregistrement en arrière-plan
        startRecordingAsync(RTSP_URL, filePath, fileName, duration);

        // Retourne une réponse immédiate
        return ResponseEntity.ok(String.format("Enregistrement démarré pour %d secondes. ID: %s", duration.getSeconds(), fileName));
    }

    @Async
    protected void startRecordingAsync(String rtspUrl, String filePath, String fileName, Duration duration) {
        // Construction de la commande FFmpeg
        String command = String.format(
                "ffmpeg -rtsp_transport tcp -timeout 10 -i %s -t %d -s 1920x1080 -r 30 -c:v libx264 -preset ultrafast -f mp4 %s",
                rtspUrl, duration.getSeconds(), filePath
        );

        // Exécution de la commande FFmpeg
        ffmpegService.executeFfmpegCommand(command);

        // Sauvegarde des métadonnées
        videoRepository.save(new Video(fileName, duration));
    }

    /**
     * Récupère une vidéo en fonction de son nom de fichier.
     *
     * @param fileName Nom du fichier vidéo à récupérer
     * @return La ressource vidéo en tant que réponse HTTP, ou une erreur si le fichier est introuvable
     */
    public ResponseEntity<Resource> getVideo(String fileName) {
        try {
            // Construire le chemin complet du fichier vidéo
            Path filePath = Paths.get(OUTPUT_DIRECTORY, fileName);
            File videoFile = filePath.toFile();

            // Vérifier que le fichier existe
            if (!videoFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Charger le fichier en tant que ressource
            Resource videoResource = new UrlResource(filePath.toUri());
            if (!videoResource.exists() || !videoResource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Retourner la ressource avec les en-têtes HTTP appropriés
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + videoFile.getName() + "\"").header(HttpHeaders.CONTENT_TYPE, "video/mp4").body(videoResource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Récupère la liste de toutes les vidéos enregistrées.
     *
     * @return Liste des vidéos avec leurs métadonnées
     */
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }
}
