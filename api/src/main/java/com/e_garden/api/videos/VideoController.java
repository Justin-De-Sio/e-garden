package com.e_garden.api.videos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/videos")
@CrossOrigin
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/record")
    public ResponseEntity<String> recordVideo(@RequestParam(defaultValue = "5") int duration) {
        return videoService.startRecording(duration);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getVideo(@PathVariable String fileName) {
        return videoService.getVideo(fileName);
    }
}
