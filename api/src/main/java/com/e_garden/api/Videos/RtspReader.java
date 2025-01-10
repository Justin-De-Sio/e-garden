package com.e_garden.api.Videos;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.bytedeco.opencv.global.opencv_imgcodecs; // pour imencode()
import org.springframework.stereotype.Service;

@Service
public class RtspReader {

    private volatile boolean capturing = false;
    private VideoCapture capture;

    public synchronized void startCapture(String rtspUrl) {
        if (capturing) {
            return;
        }
        capturing = true;

        capture = new VideoCapture(rtspUrl);
        if (!capture.isOpened()) {
            capturing = false;
            throw new RuntimeException("Impossible d'ouvrir le flux RTSP : " + rtspUrl);
        }
    }

    public synchronized byte[] getNextFrameAsJpeg() {
        if (!capturing || capture == null) {
            return null;
        }

        // Lire la frame
        Mat frame = new Mat();
        if (!capture.read(frame)) {
            // Fin de flux ou erreur de lecture
            return null;
        }

        // Encodage en JPEG via BytePointer
        BytePointer buffer = new BytePointer();
        boolean ok = opencv_imgcodecs.imencode(".jpg", frame, buffer);
        if (!ok) {
            buffer.deallocate();
            return null;
        }

        // Extraire les données encodées dans un tableau d'octets
        byte[] jpegBytes = new byte[(int) buffer.limit()];
        buffer.get(jpegBytes);
        buffer.deallocate();

        return jpegBytes;
    }

    public synchronized void stopCapture() {
        capturing = false;
        if (capture != null) {
            capture.release();
            capture = null;
        }
    }
}
