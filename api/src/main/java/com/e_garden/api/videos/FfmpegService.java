package com.e_garden.api.videos;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * Service dédié à l'exécution de commandes FFmpeg.
 * Permet de lancer des processus FFmpeg de manière asynchrone pour
 * manipuler, convertir ou enregistrer des flux vidéo.
 */
@Service
public class FfmpegService {

    /**
     * Exécute une commande FFmpeg de manière asynchrone.
     *
     * @param command La commande FFmpeg à exécuter (inclut tous les arguments nécessaires).
     */
    @Async
    public void executeFfmpegCommand(String command) {
        try {
            // Crée un nouveau processus à partir de la commande spécifiée
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true); // Redirige les erreurs dans le flux de sortie standard
            Process process = processBuilder.start(); // Démarre le processus

            // Démarre un thread pour lire les logs produits par FFmpeg
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("[FFmpeg] " + line); // Affiche chaque ligne de sortie dans la console
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // Capture et affiche les erreurs liées à la lecture des logs
                }
            }).start();

            // Attend que le processus se termine avec un timeout de 120 secondes
            boolean finished = process.waitFor(120, TimeUnit.SECONDS);
            if (!finished) {
                // Si le processus dépasse le timeout, il est forcé à se terminer
                process.destroy();
                System.err.println("[FFmpeg] Process timed out and was terminated.");
            }

            // Vérifie le code de sortie pour déterminer si le processus a réussi ou échoué
            int exitCode = process.exitValue();
            if (exitCode == 0) {
                System.out.println("[FFmpeg] Command executed successfully."); // Commande réussie
            } else {
                System.err.println("[FFmpeg] Command failed with exit code: " + exitCode); // Échec avec un code d'erreur
            }

        } catch (Exception e) {
            // Capture et affiche toutes les exceptions survenues lors de l'exécution de la commande
            e.printStackTrace();
            System.err.println("[FFmpeg] Error during command execution: " + e.getMessage());
        }
    }
}
