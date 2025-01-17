package com.e_garden.api.Videos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    @Autowired
    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Video saveVideo(Video video) {
        return videoRepository.save(video);
    }

    public Video getVideoByDate(LocalDateTime dateTime) {
        // TODO formater la date et réfléchir comment on nomme le fichier
        return videoRepository.findVideoByFileName(dateTime.toString());
    }

    // TODO à tester
    public List getAllPicture() {
        return videoRepository.findAllByExtension(1);
    }
}
