<template>
  <div>
  <h1>Table de tous les rapports</h1>
    <div v-if="!report.length">Loading...</div>
    <UTable v-else :columns="columns" :rows="report">
      <template #user="{ row }">
        {{ row.user.email }}
      </template>
    </UTable>
    <div class="flex justify-end px-3 py-3.5 border-t border-gray-200 dark:border-gray-700">
      <UPagination @click="fetchReport" v-model="page" :page-count="pageCount" :total="pageTotal"/>
    </div>
  </div>
</template>

<script setup lang="ts">
import {callAPI} from "~/services/callAPI";
import {ref, onMounted} from "vue";

// Typage des données du rapport
interface Report {
  id: number;
  content: string;
  createdAt: string;
  reportDate: string;
  user: {
    id: number;
    name: string;
    email: string;
  };
}

const columns = [
  {key: "id", label: "ID"},
  {key: "content", label: "Rapport", sortable: true},
  {key: "createdAt", label: "Date de création", sortable: true},
  {key: "reportDate", label: "Date d'enregistrement", sortable: true},
  {key: "user", label: "Utilisateur (Email)", sortable: true},
];

const api = new callAPI();
const report = ref<Report[]>([]);
const page = ref(1)
let pageCount = 1;
let pageTotal = 0;

// Fonction pour récupérer les données de l'API
async function fetchReport() {
  try {
    const data = await api.fetchAPIGetPaginated("report", page.value - 1, 20);
    if (data?.content) {
      report.value = data.content.map((item: Report) => ({
        ...item,
        user: item.user.email, // Remplace `user` par l'email
      }));
      pageTotal = data.totalPages;
    } else {
      console.warn("No content found in API response");
    }
  } catch (error) {
    console.error("Error fetching report data:", error);
  }
}

// Appel lors du montage du composant
onMounted(fetchReport);
</script>

<style scoped>
/* Ajoutez vos styles ici */
</style>
