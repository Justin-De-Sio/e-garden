<template>
    <div class="flux_video">
      <video id="video" controls autoplay muted playsinline></video>
      <div class="liveContainer">
        <div class="cercle"></div>
        <h4>En Direct</h4>
      </div>
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
        return {
          ...options,
          credentials: 'include', 
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

<style lang="css" scoped>

    video::-webkit-media-controls-timeline {
      display: none !important;
    }
    .flux_video {
    width: 100%;
    max-width: 800px; 
    height: auto;
    position: relative;
    background-color: black; 
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 0.8rem;
    overflow: hidden; 
  }

  .flux_video video { 
    width: 100%;  
    height: auto;
    max-height: 20rem; 
    border-radius: inherit; 
    object-fit: cover; 
  }

    .liveContainer{
      display: flex;
      justify-content: center;
      align-items: center;
      gap: 0.3rem;
      width: 15%;
      height: 8%;
      background-color: black;
      border-radius: 12rem;
      position: absolute;
      top: 20px;
      right: 20px;
      opacity: 0.6;
    }

    .cercle{
      width: 0.7rem;
      height: 0.7rem;
      background-color: #ECA9A8;
      border-radius: 50%;
      opacity: 1;
    }

    .liveContainer h4{
      color: white;
      font-family: "Gilroy-medium";
      font-size: 0.7rem;
    }

</style>