<template>
  <div>
    <h2>Information sur le serveur</h2>
    <div v-if="infos">
      <p>Taille du stockage total du serveur : {{infos.totalStorage}}</p>
      <p>Taille du stockage disponible  du serveur : {{infos.freeStorage}}</p>
      <p>Taille de la mémoire RAM : {{infos.totalMemory}} </p>
      <p>Utilisation de la mémoire RAM : {{infos.usedMemory}} </p>
      <p>Utilisation du CPU : {{infos.cpuLoad}}</p>
      <p>Date du dernier redémarrage :
        <NuxtTime :datetime="infos.lastBoot" day="2-digit" month="2-digit" year="numeric"
                  hour="2-digit" minute="2-digit"/>
      </p>
      <p>Système d'exploitation : {{infos.osName}} Version : {{infos.osVersion}} </p>
      <p>Version Java : {{infos.javaVersion}} </p>
      <p>Version de node : {{infos.nodeVersion}}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import {callAPIServices} from "~/services/callAPIServices";
import type {ServerInfo} from "~/model/ServerInfo";
import {ref} from "vue";
const api = new callAPIServices();

let infos = ref<ServerInfo>();

async function fetchServeurInfo() {
  try {
    const data = await api.fetchAPIGet("server-info") as ServerInfo;
    if (data) {
      infos.value = data;
    } else {
      console.warn("No content found in API response");
    }
  } catch (error) {
    console.error("Error fetching report data:", error);
  }
}

onMounted(fetchServeurInfo);
</script>

<style scoped>

</style>