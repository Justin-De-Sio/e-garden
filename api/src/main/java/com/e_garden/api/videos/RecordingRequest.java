package com.e_garden.api.videos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;

/**
 * Classe DTO pour la requête d'enregistrement vidéo.
 * La durée peut être spécifiée avec ou sans le préfixe PT.
 * Exemples valides :
 * - "1H" -> 1 heure
 * - "30M" -> 30 minutes
 * - "45S" -> 45 secondes
 * - "1H30M" -> 1 heure et 30 minutes
 */

public class RecordingRequest {
    private Duration duration;

    @JsonProperty("duration")
    public void setDuration(String durationStr) {
        // Ajoute le préfixe PT si nécessaire
        if (!durationStr.startsWith("PT")) {
            durationStr = "PT" + durationStr;
        }
        this.duration = Duration.parse(durationStr);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}