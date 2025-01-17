package com.e_garden.api.Videos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@RestController
@RequestMapping("/stream")
public class MjpegController {

    private RtspReader rtspReader;

    @Autowired
    public MjpegController(RtspReader rtspReader) {
        this.rtspReader = rtspReader;
    }

    @GetMapping(value = "/start")
    @Secured({"ADMINISTRATEUR"})
    public String startStream() {
        // Lance la capture
        String rtspUrl =    System.getenv("RTSP_URL");

        rtspReader.startCapture(rtspUrl);

        return "Capture démarrée pour : " + rtspUrl;
    }

    @GetMapping(value = "/stop")
    @Secured({"ADMINISTRATEUR"})
    public String stopStream() {
        rtspReader.stopCapture();
        return "Capture arrêtée.";
    }

    /**
     * Ce endpoint diffuse un flux MJPEG.
     */
    @GetMapping(value = "/video", produces = MediaType.IMAGE_JPEG_VALUE)
    @Secured({"ADMINISTRATEUR"})
    public void getVideo(HttpServletResponse response) throws IOException {
        // Type de contenu : multipart/x-mixed-replace
        response.setContentType("multipart/x-mixed-replace; boundary=frame");

        // Boucle infinie jusqu'à ce que le client se déconnecte ou qu'il y ait une erreur
        while (true) {
            byte[] jpegData = rtspReader.getNextFrameAsJpeg();
            if (jpegData == null) {
                // On sort de la boucle si plus de frame dispo (ou flux terminé)
                break;
            }

            // Ecriture de la boundary
            response.getOutputStream().write(("--frame\r\n").getBytes());
            response.getOutputStream().write(("Content-Type: image/jpeg\r\n\r\n").getBytes());
            // Ecriture des données JPEG
            response.getOutputStream().write(jpegData);
            response.getOutputStream().write("\r\n".getBytes());

            // Force l’envoi au fur et à mesure
            response.flushBuffer();

            // (Optionnel) Sleep pour réguler la fréquence, ou pas
            // try { Thread.sleep(40); } catch (InterruptedException e) { /* ignorer */ }
        }
    }
}
