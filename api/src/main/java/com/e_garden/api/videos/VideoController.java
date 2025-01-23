package com.e_garden.api.videos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
@CrossOrigin
@Secured("ADMINISTRATEUR")
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    /**
     * Démarre l'enregistrement d'une vidéo.
     * La durée doit être au format ISO-8601. Exemples:
     * - PT5M (5 minutes)
     * - PT30S (30 secondes)
     * - PT1H (1 heure)
     * - PT1H30M (1 heure et 30 minutes)
     */
    @PostMapping("/record")
    public ResponseEntity<String> recordVideo(@RequestBody RecordingRequest request) {
        return videoService.startRecording(request.getDuration());
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getVideo(@PathVariable String fileName) {
        return videoService.getVideo(fileName);
    }

    @GetMapping("/")
    public List<Video> getAllVideos() {
        return videoService.getAllVideos();
    }
}


