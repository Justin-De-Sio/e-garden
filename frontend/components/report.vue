<template>
  <div class="wrapper">
    <div class="flex px-3 py-3.5 border-b border-gray-200 dark:border-gray-700">
      <UInput v-model="q" placeholder="Rechercher..." />
    </div>

    <UTable :rows="filteredRows" :columns="columns" />
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { callAPI } from '~/services/callAPI';

// **Interfaces**
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


const api = new callAPI();

// **Données**
const varGetReportData = ref<any[]>([]); 
const q = ref(''); 


const columns = [
  { key: 'global_name', label: 'Prénom / Nom' },
  { key: 'email', label: 'Email' },
  { key: 'class', label: 'Classe' },
  { key: 'group', label: 'Groupe' },
  { key: 'role', label: 'Rôle' },
  { key: 'report', label: 'Compte-rendu' },
  { key: 'validated', label: 'Validation' },
  { key: 'reportDate', label: 'Date du Compte-rendu' },
];


const filteredRows = computed(() => {
  if (!q.value) {
    return varGetReportData.value;
  }

  return varGetReportData.value.filter((row) =>
    Object.values(row).some((value) =>
      String(value).toLowerCase().includes(q.value.toLowerCase())
    )
  );
});


async function getReportData() {
  try {

    const response = await api.fetchAPIGetPaginated("report", 0, 5) as ApiResponse<Report[]>;

    // Transforme les données pour qu'elles soient prêtes à être affichées
    varGetReportData.value = response.content.map((report) => ({
      global_name: `${report.user.name} ${report.user.surname}`,
      email: report.user.email,
      class: report.user.className,
      group: `Groupe ${report.user.groupNumber}`,
      role: report.user.role,
      report: report.content || "Aucun contenu",
      validated: report.validated ? "Validé" : "Non validé",
      reportDate: new Date(report.reportDate).toLocaleString(),
    }));

    console.log("Données transformées :", varGetReportData.value);
  } catch (error) {
    console.error("Erreur lors de la récupération des données :", error);
  }
}


onMounted(() => {
  getReportData();
});
</script>

<style scoped>
.wrapper {
  background-color: white;
}
</style>
