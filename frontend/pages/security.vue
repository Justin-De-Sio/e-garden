<template>
  <div class="wrapper_page">
    <NavbarVertical />
    <div class="content_wrapper">
      <div class="left_section">
        <header_title 
          title="Section Sécurité" 
          subtitle="Retrouvez toutes les fonctionnalités disponibles pour une gestion de votre potager pilotée" 
          class="header_title"
        />
        
        <div class="indicators_section">
          <h2>Indicateurs</h2>
          <div class="indicators_grid">
            <indicators 
              iconBackgroundColor="#95BD75"
              iconPath="/assets/passages.png"
              subtitle="Sur les 30 derniers jours"
              :title="`${number_passage || 0} passages`"
            />
            <indicators 
              iconBackgroundColor="#ECA9A8"
              iconPath="/assets/exclamation.png"
              subtitle="Sur les 30 derniers jours"
              :title="`${number_cr || 0} rapports non remis`"
            />
          </div>
        </div>

        <div class="media_section">
          <div class="media_item camera_wrapper">
            <h2>Caméra</h2>
            <camera />
          </div>
          <div class="media_item report_wrapper">
            <h2>Comptes-rendus</h2>
            <newReport />
          </div>
        </div>
      </div>
      
      <div class="right_section">
        <calendar />
      </div>
    </div>
    
    <footer>E-Garden • © 2025</footer>
  </div>
</template>

<script setup lang="ts">
import indicators from "~/components/indicators.vue";
import header_title from "~/components/header_title.vue";
import camera from "~/components/camera-direct.vue";
import NavbarVertical from "~/components/navbar_vertical.vue";
import { callAPI } from "~/services/callAPI";
import newReport from "~/components/new-report.vue";
import calendar from "~/components/calendar.vue";
import { ref, onMounted } from "vue";

const api = new callAPI();
const number_passage = ref(0);
const number_cr = ref(0);

definePageMeta({
  middleware: "auth",
  role: ["ADMINISTRATEUR"],
});

async function getData() {
  try {
    const stats_passages = await api.fetchAPIGet("/event/statistique");
    const stats_cr = await api.fetchAPIGet("/report/statistique");
    number_passage.value = stats_passages;
    number_cr.value = stats_cr;
  } catch (error) {
    console.error("Erreur :", error);
  }
}

onMounted(() => {
  getData();
});
</script>

<style scoped>
.wrapper_page {
  display: flex;
  flex-direction: column;
  width: 100vw;
  min-height: 100vh;
  overflow: hidden;
}

.content_wrapper {
  display: flex;
  flex-direction: row;
  gap: 2rem;
  padding: 2rem;
  flex-grow: 1;
}

.left_section {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  flex: 2;
}

.indicators_section h2, .media_item h2 {
  font-family: "Aeonik-Regular";
  font-size: clamp(1rem, 2vw, 1.5rem);
  margin-bottom: 1rem;
}

.indicators_grid {
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
  justify-content: flex-start;
}

.media_section {
  display: flex;
  flex-wrap: wrap;
  gap: 2rem;
}

.camera_wrapper, .report_wrapper {
  display: flex;
  flex-direction: column;
  justify-content: center;
  flex: 1;
}

.right_section {
  display: flex;
  justify-content: center;
  align-items: start;
  flex: 1;
}

footer {
  text-align: center;
  font-family: "Aeonik-Regular";
  padding: 1rem;
  background: #f8f8f8;
}

@media screen and (max-width: 1200px) {
  .content_wrapper {
    flex-direction: column;
    padding: 1rem;
  }
  .media_section {
    flex-direction: column;
  }
}
</style>