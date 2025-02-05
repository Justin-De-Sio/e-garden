<script setup lang="ts">
import { ref, onMounted } from 'vue';

definePageMeta({
  middleware: "auth",
  role: ["ADMINISTRATEUR"],
});

const videoUrl = ref<string | null>(null);
const route = useRoute();
const today = new Date();
const year = route.query.year as string || today.getFullYear().toString();
const month = route.query.month as string || (today.getMonth() + 1).toString();// Mois (0-11) donc +1 pour avoir (1-12)
const day = route.query.day as string || today.getDate().toString();

onMounted(() => getVideo(year, month, day));

function getVideo(year: string, month: string, day: string) {
    videoUrl.value = `/api/videos/stream-by-date?year=${year}&month=${month}&day=${day}`;
    console.log("Video URL:", videoUrl.value);
}
</script>

<template>
  <div>
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
