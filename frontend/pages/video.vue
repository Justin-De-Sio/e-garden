<script setup lang="ts">
import { ref, onMounted } from 'vue';

definePageMeta({
  middleware: "auth",
  role: ["ADMINISTRATEUR"],
});

// The video URL is constructed to point to our proxy endpoint.
// The JWT token stored in the cookie will be automatically attached via the proxy middleware.
const videoUrl = ref<string | null>(null);
const loading = ref(false);

onMounted(() => getVideo("2025", "02", "01"));

async function getVideo(year: string, month: string, day: string) {
  loading.value = true;
  videoUrl.value = null; // Clear previous video URL to force update

  try {
    // Build the URL to stream the video.
    // The proxy (configured to intercept `/api`) will forward this request to your backend.
    videoUrl.value = `/api/videos/stream-by-date?year=${year}&month=${month}&day=${day}`;
    console.log("Video URL:", videoUrl.value);
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
    <button @click="getVideo('2025', '02', '01')" :disabled="loading">
      {{ loading ? "Chargement..." : "Charger la vidéo" }}
    </button>

    <p v-if="loading">Chargement de la vidéo...</p>
    <p v-if="!loading && !videoUrl">Aucune vidéo disponible</p>

    <video v-if="videoUrl" controls>
      <source :src="videoUrl" type="video/mp4" />
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
