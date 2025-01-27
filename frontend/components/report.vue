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
        v-model="page"
        :page-count="totalPages"
        :total="totalElements"
        @update:modelValue="handlePageChange"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { callAPI } from '~/services/callAPI';

// Interfaces
interface User {
  id: number;
  name: string;
  surname: string;
  email: string;
  role: string;
  className: string;
  groupNumber: number;
  locked: boolean;
}

interface Report {
  id: number;
  user: User;
  reportDate: string;
  content: string;
  validated: boolean;
  createdAt: string;
}

interface ApiResponse<T> {
  content: T;
  pageNumber: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
}


const page = ref(1); 
const pageSize = 5; 
const totalPages = ref(1); 
const totalElements = ref(0); 
const varGetReportData = ref<Report[]>([]); 
const api = new callAPI();


const columns = [
  { key: 'global_name', label: 'Prénom / Nom' },
  { key: 'report', label: 'Compte-rendu' },
  { key: 'validated', label: 'Validation' },
];


const rows = computed(() => {
  if (!varGetReportData.value.length) return [];
  return varGetReportData.value.map((report) => ({
    global_name: `${report.user.name} ${report.user.surname}`,
    report: report.content || "Aucun contenu",
    validated: report.validated ? "Validé" : "Non validé",
  }));
});

// Fonction pour récupérer les données depuis l'API
async function getReportData(pageNumber = 0) {
  try {
    const response = await api.fetchAPIGetPaginated("report", pageNumber, pageSize) as ApiResponse<Report[]>;

    varGetReportData.value = response.content;
    totalPages.value = response.totalPages;
    totalElements.value = response.totalElements;
  } catch (error) {
    console.error("Erreur lors de la récupération des données :", error);
    varGetReportData.value = []; 
  }
}


function handlePageChange(newPage: number) {
  page.value = newPage;
  getReportData(newPage - 1);
}


onMounted(() => {
  getReportData();
});
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
