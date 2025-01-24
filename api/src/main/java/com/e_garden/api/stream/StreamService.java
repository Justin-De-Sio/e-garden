package com.e_garden.api.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

import static com.e_garden.api.stream.StreamConfig.OUTPUT_DIRECTORY;


/**
 * Service for handling RTSP to HLS streaming operations.
 * Responsible for starting and managing FFmpeg processes.
 */
@Service
public class StreamService {

    private final Process streamingProcess;

    @Autowired
    public StreamService(Process streamingProcess) {
        this.streamingProcess = streamingProcess;
    }

    /**
     * Checks if the FFmpeg process is running.
     *
     * @return true if the process is active, false otherwise
     */
    public boolean isStreaming() {
        return streamingProcess != null && streamingProcess.isAlive();
    }

    /**
     * Stops the FFmpeg process if it is running.
     */
    public void stopStreaming() {
        if (streamingProcess != null) {
            streamingProcess.destroy();
            System.out.println("Stream stopped.");
        }
    }
    /**
     * Checks if the HLS playlist file exists.
     *
     * @return true if the playlist exists, false otherwise
     */
    public boolean isPlaylistAvailable() {
        File playlist = new File(OUTPUT_DIRECTORY + "/output.m3u8");
        return playlist.exists();
    }
}
