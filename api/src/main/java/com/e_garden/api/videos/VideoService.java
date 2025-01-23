package com.e_garden.api.videos;

import com.e_garden.api.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
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
    private static final String OUTPUT_DIRECTORY = "rtsp" + File.separator + "videos";

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
     * Lance l'enregistrement d'un flux RTSP pour une durée donnée.
     *
     * @param duration Durée de l'enregistrement en secondes
     * @return Réponse HTTP avec un message de succès ou une erreur
     */
    public ResponseEntity<String> startRecording(int duration) {
        // Récupération de l'URL RTSP à partir de la variable d'environnement
        String rtspUrl = System.getenv("RTSP_URL");
        if (rtspUrl == null || rtspUrl.isEmpty()) {
            return ResponseEntity.badRequest().body("RTSP_URL environment variable is not set or empty.");
        }

        // Génération d'un nom de fichier unique pour la vidéo
        String fileName = UUID.randomUUID().toString() + ".mp4";
        String filePath = OUTPUT_DIRECTORY + File.separator + fileName;

        // Création du répertoire de sortie s'il n'existe pas
        File outputDir = new File(OUTPUT_DIRECTORY);
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            return ResponseEntity.internalServerError().body("Failed to create output directory.");
        }

        // Construction de la commande FFmpeg pour enregistrer la vidéo
        String command = String.format(
                "ffmpeg -rtsp_transport tcp -timeout 10000000 -i %s -t %d -s 1920x1080 -r 30 -c:v libx264 -preset ultrafast -f mp4 %s",
                rtspUrl, duration, filePath
        );

        // Exécution de la commande FFmpeg via le service dédié
        ffmpegService.executeFfmpegCommand(command);

        // Sauvegarde des métadonnées de la vidéo dans le dépôt
        videoRepository.save(new Video(fileName, filePath));

        // Retourne une réponse HTTP de succès
        return ResponseEntity.ok("Recording started for " + duration + " seconds.");
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
            Path filePath = Path.of(OUTPUT_DIRECTORY, fileName);
            File videoFile = filePath.toFile();

            // Vérifier que le fichier existe
            if (!videoFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            // Charger le fichier en tant que ressource
            Resource videoResource = new UrlResource(filePath.toUri());
            if (!videoResource.exists() || !videoResource.isReadable()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            // Retourner la ressource avec les en-têtes HTTP appropriés
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + videoFile.getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .body(videoResource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
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
