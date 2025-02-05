<template>
  <div class="feed">
    <div v-for="post in reports" :key="post.id" class="post">
      <p class="date">
        <NuxtTime :datetime="post.reportDate" day="2-digit" month="2-digit" year="numeric" hour="2-digit"
                                minute="2-digit"/>
      </p>
      <p class="content">{{ post.content }}</p>
    </div>
    <div v-if="(numPages + 1) >= numTotalPages">
      <UButton @click="requetRepport">Charger plus...</UButton>
    </div>
  </div>
</template>

<script setup lang="ts">
import { callAPIServices } from "~/services/callAPIServices";
import type { Reports } from "~/model/Reports.js";
import type { Pages } from "~/model/Pages.js";
import { onMounted, ref } from "vue";

const api = new callAPIServices();
const reports = ref<Reports[]>([]);
const numPages = ref<number>(0);
const numTotalPages = ref<number>(0);

onMounted(async () => {
  await requetRepport();
});

async function requetRepport() {
  try {
    const response = await api.fetchAPIGetPaginated('report/history', numPages.value.toString(), "10") as Pages;
    console.log(response.content);
    // Mise à jour de reports en concaténant le contenu
    reports.value = reports.value.concat(response.content as Reports[]);
    numTotalPages.value = response.totalPages;
    numPages.value++;
    console.log(reports.value);
  } catch (error) {
    console.error('Erreur lors de la récupération des données :', error);
  }
}
</script>


<style scoped>
.feed {
  width: 100%;
  max-width: 600px;
  margin: 0 auto;
}
.post {
  border-bottom: 1px solid #ccc;
  padding: 10px 0;
}
.date {
  font-size: 0.8em;
  color: #888;
}
.content {
  font-size: 1em;
}
</style>