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
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @Secured("ADMINISTRATEUR")
    @PostMapping("/record")
    public ResponseEntity<String> recordVideo(@RequestParam(defaultValue = "5") int duration) {
        return videoService.startRecording(duration);
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
