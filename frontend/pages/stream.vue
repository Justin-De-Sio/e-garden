<template>
  <div>
    <h1>Stream page</h1>
  </div>
  <div>
    <video id="video" controls></video>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import Hls from "hls.js";

const videoSrc = '/api/stream/playlist';
definePageMeta({
  middleware: "auth"
})

onMounted(() => {
  const video = document.getElementById('video') as HTMLVideoElement;

    const sessionCookie = useCookie('session');
    const bearerToken = sessionCookie?.value;
    if (!bearerToken) {
      console.warn('Le token est manquant dans le cookie session.');
      throw new Error('Utilisateur non authentifié.');
    }

  if (Hls.isSupported()) {
    const hls = new Hls({
      xhrSetup: (xhr) => {
        xhr.setRequestHeader('Authorization', `Bearer ${bearerToken}`);
      },
      fetchSetup: (url, options) => {
        // Assurez-vous que les cookies sont envoyés
        return {
          ...options,
          credentials: 'include', // Envoi des cookies avec la requête
        };
      },
    });

    hls.loadSource(videoSrc);
    hls.attachMedia(video);

    hls.on(Hls.Events.MANIFEST_PARSED, () => {
      console.log('HLS.js is ready!');
    });
  } else if (video.canPlayType('application/vnd.apple.mpegurl')) {
    video.src = videoSrc;
    video.addEventListener('loadedmetadata', () => {
      console.log('Native HLS support detected');
    });
  }
});
</script>

<style scoped>
/* Ajoutez vos styles ici */
</style>
