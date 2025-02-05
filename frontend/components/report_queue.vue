<template>
  <div class="wrapperContainer">
  <div class="feed">
    <div v-for="post in reports" :key="post.id" class="post">
      <p class="date">
        <NuxtTime :datetime="post.reportDate" day="2-digit" month="2-digit" year="numeric" hour="2-digit"
                  minute="2-digit"/>
      </p>
      <p class="content">{{ post.content }}</p>
    </div>
    <div ref="loadTrigger" v-if="(numPages + 1) <= numTotalPages" class="loading-trigger"></div>
  </div>
  </div>
</template>

<script setup lang="ts">
import { callAPIServices } from "~/services/callAPIServices";
import type { Reports } from "~/model/Reports.js";
import type { Pages } from "~/model/Pages.js";

const api = new callAPIServices();
const reports = ref<Reports[]>([]);
const numPages = ref<number>(0);
const numTotalPages = ref<number>(0);
const loadTrigger = ref<HTMLElement | null>(null);
let observer: IntersectionObserver | null = null;

async function requetRepport() {
  try {
    const response = await api.fetchAPIGetPaginated('report/history', numPages.value.toString(), "10") as Pages;
    reports.value = reports.value.concat(response.content as Reports[]);
    numTotalPages.value = response.totalPages;
    numPages.value++;
  } catch (error) {
    console.error('Erreur lors de la récupération des données :', error);
  }
}

// Observer pour détecter quand l'utilisateur atteint le bas de la page
const createObserver = () => {
  observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting) {
          requetRepport();
        }
      },
      { rootMargin: "100px" } // Charge avant d'atteindre complètement le bas
  );

  if (loadTrigger.value) {
    observer.observe(loadTrigger.value);
  }
};

onMounted(async () => {
  await requetRepport();

  createObserver();
});

onUnmounted(() => {
  if (observer && loadTrigger.value) {
    observer.unobserve(loadTrigger.value);
  }
});
</script>


<style scoped>
.loading-trigger {
  height: 50px;
}
.wrapperContainer {
  flex-direction: row;
  align-items: center;
  width: 50%;
  max-height: 100%;
  gap: 1rem;
  background-color: white;
  border-radius: 20px;
  padding: 1.5rem;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

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