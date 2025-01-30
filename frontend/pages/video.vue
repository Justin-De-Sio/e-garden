<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { callAPI } from '~/services/callAPI';

const api = new callAPI();
const videoUrl = ref<string | null>(null);
const loading = ref(false); // Pour afficher un indicateur de chargement si besoin

onMounted(() => getVideo("2025", "01", "30"));

async function getVideo(year: string, month: string, day: string) {
  loading.value = true;
  videoUrl.value = null; // Efface l'ancienne vidéo pour forcer la mise à jour

  try {
    const response = await api.fetchAPIGet(`videos/date?year=${year}&month=${month}&day=${day}`);

    // Vérifiez que la réponse est bien un objet `Response`
    console.log(response);
    const blob = await response.blob();
    videoUrl.value = URL.createObjectURL(response.blob); // Créer une URL temporaire pour <video>
    console.log(videoUrl);
  } catch (error) {
    console.error("Erreur lors de la récupération de la vidéo:", error);
  } finally {
    loading.value = false;
  }
  console.log("Vidéo chargée:", videoUrl.value);
}
</script>

<template>
  <div>
    <button @click="getVideo('2025', '01', '30')" :disabled="loading">
      {{ loading ? "Chargement..." : "Charger la vidéo" }}
    </button>

    <p v-if="loading">Chargement de la vidéo...</p>
    <p v-if="!loading && !videoUrl">Aucune vidéo disponible</p>

    <video v-if="videoUrl" controls>
      <source :src="videoUrl" type="video/mp4">
      Votre navigateur ne supporte pas la lecture de vidéos.
    </video>
  </div>
</template>

<style scoped>
video {
  max-width: 100%;
  height: auto;
  margin-top: 10px;
}
</style>
