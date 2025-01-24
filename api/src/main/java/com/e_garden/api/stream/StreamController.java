package com.e_garden.api.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.e_garden.api.stream.StreamConfig.OUTPUT_DIRECTORY;


/**
 * REST controller for managing HLS streaming endpoints.
 * Provides APIs to start streaming and serve HLS files.
 */
@RestController
@RequestMapping("/stream")
@Secured("ADMINISTRATEUR")
public class StreamController {

    @Autowired
    private StreamService streamService;

    /**
     * Serves the HLS playlist (.m3u8 file).
     *
     * @return a ResponseEntity containing the HLS playlist as a FileSystemResource
     */
    @GetMapping(value = "/playlist", produces = "application/vnd.apple.mpegurl")
    public ResponseEntity<FileSystemResource> getHlsPlaylist() {
        if (!streamService.isPlaylistAvailable()) {
            return ResponseEntity.notFound().build();
        }

        File file = new File(OUTPUT_DIRECTORY + "/output.m3u8");
        FileSystemResource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=output.m3u8")
                .contentType(MediaType.valueOf("application/vnd.apple.mpegurl"))
                .body(resource);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<InputStreamResource> getHlsSegment(@PathVariable String filename) throws IOException {
        File file = new File(OUTPUT_DIRECTORY + File.separator + filename);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        System.out.println("Serving file: " + file.getAbsolutePath());

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("video/mp2t"))
                .contentLength(file.length())
                .body(resource);
    }

}