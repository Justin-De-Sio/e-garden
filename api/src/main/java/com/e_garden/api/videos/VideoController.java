package com.e_garden.api.videos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Le type Video controller.
 */
@RestController
@RequestMapping("/videos")
@CrossOrigin
@Secured("ADMINISTRATEUR")
public class VideoController {

    private final VideoService videoService;

    /**
     * Instancie un nouveau Video controller.
     *
     * @param videoService le video service
     */
    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }


    /**
     * Démarre l'enregistrement d'une vidéo.
     * La durée doit être au format ISO-8601.
     * Exemples :
     * - PT5M (5 minutes) ;
     * - PT30S (30 secondes) ;
     * - PT1H (1 heure) ;
     * - PT1H30M (1 heure et 30 minutes).
     *
     * @param request le request
     * @return le response entity
     */
    @PostMapping("/record")
    public ResponseEntity<String> recordVideo(@RequestBody RecordingRequest request) {
        return videoService.startRecording(request.getDuration());
    }

    /**
     * Gets video by date.
     *
     * @param year    l'année
     * @param month   le mois
     * @param day     le jour
     * @param headers headers
     * @return le video by date
     */
    @GetMapping("/stream-by-date")
    public ResponseEntity<ResourceRegion> getVideoByDate(@RequestParam(defaultValue = "2000") Integer year,
                                                   @RequestParam(defaultValue = "01") Integer month,
                                                   @RequestParam(defaultValue = "01") Integer day,
                                                   @RequestHeader HttpHeaders headers) {
        if (year == 2000 && month == 1 && day == 1) {
            year = LocalDateTime.now().getYear();
            month = LocalDateTime.now().getMonthValue();
            day = LocalDateTime.now().getDayOfMonth();
        }
        if (year > LocalDateTime.now().getYear() || month > 12 || day > 31 || month < 1 || day < 1) {
            ResponseEntity.badRequest().build();
        }
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0);
        try {
            return videoService.streamVideo(dateTime, headers);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * Gets video.
     *
     * @param fileName le filename
     * @return le video
     */
    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getVideo(@PathVariable String fileName) {
        return videoService.getVideo(fileName);
    }

    /**
     * Gets last videos.
     *
     * @return le last videos
     */
    @GetMapping("/last-videos")
    public List<Video> getLastVideos() {
        return videoService.getLastVideos();
    }

    /**
     * Gets all videos.
     *
     * @return all videos
     */
    @GetMapping("/")
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }

    /**
     * Endpoint pour récupérer les vidéos du mois et de l'année demandés.
     * Exemple d'appel : GET /videos/month-videos?year=2025&month=2
     *
     * @param year  l'année demandée
     * @param month le mois demandé (entre 1 et 12)
     * @return la liste des vidéos correspondant au mois demandé
     */
    @GetMapping("/month-videos")
    public ResponseEntity<List<Video>> getVideosByMonth(@RequestParam int year, @RequestParam int month) {
        List<Video> videos = videoService.getVideosByMonth(year, month);
        return ResponseEntity.ok(videos);
    }
}