<template>
  <div class="wrapper">
    <UTable
      v-if="rows.length > 0"
      :rows="rows"
      :columns="columns"
    />
    <p v-else class="text-center py-3">Aucune donnée disponible.</p>
    <div class="flex justify-end px-3 py-3.5 border-t border-gray-200 dark:border-gray-700">
      <UPagination
          @click="getReportData"
        v-model="page"
        :page-count="pageCount"
        :total="totalPages"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { callAPIServices } from '~/services/callAPIServices';
import type {Pages} from "~/model/Pages";
import type {Reports} from "~/model/Reports";

const page = ref(1); 
const pageSize = 5;
const pageCount = ref(1);
const totalPages = ref(0);
const varGetReportData = ref<Reports[]>([]);
const api = new callAPIServices();


const columns = [
  { key: 'global_name', label: 'Prénom / Nom' },
  { key: 'report', label: 'Justification' },
  { key: 'validated', label: 'Status' },
];

const rows = computed(() => {
  if (!varGetReportData.value.length) return [];
  return varGetReportData.value.map((report) => ({
    global_name: `${report.user.name} ${report.user.surname}`,
    report: report.content || "Aucun contenu",
    validated: report.validated ? "✅" : "❌",
  }));
});

// Fonction pour récupérer les données depuis l'API
async function getReportData() {
  try {
    const response = await api.fetchAPIGetPaginated("report", page.value - 1, pageSize) as Pages;
    if (response?.content) {
      varGetReportData.value = response.content as Reports[];
      totalPages.value = response.totalPages;
    } else {
      console.warn("No content found in API response");
    }
  } catch (error) {
    console.error("Erreur lors de la récupération des données :", error);
    varGetReportData.value = []; 
  }
}

onMounted(getReportData);
</script>

<style scoped>
.wrapper {

  max-width: 40rem;
  border-radius: 0.8rem;
  background-color: white;
}
.text-center {
  text-align: center;
}
</style>
