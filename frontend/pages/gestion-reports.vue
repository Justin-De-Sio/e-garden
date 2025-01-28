<template>
  <div class="wrapper_page">
    <NavbarVertical></NavbarVertical>
    <div class="header_bis">
      <header_title title="Table Rapport" subtitle="Gestion de tous les rapports existant"></header_title>
    </div>
    <div v-if="!report.length">
      <UTable
          loading
          :loading-state="{ icon: 'i-heroicons-arrow-path-20-solid', label: 'Loading...' }"
          :progress="{ color: 'primary', animation: 'carousel' }"
          class="w-full"
          :columns="columns"
      />
    </div>
    <UTable v-else :columns="columns" :rows="report">
      <template #createdAt-data="{ row }">
        <NuxtTime :datetime="row.createdAt" day="2-digit" month="2-digit" year="numeric" hour="2-digit"
                  minute="2-digit"/>
      </template>
      <template #reportDate-data="{ row }">
        <NuxtTime :datetime="row.reportDate" day="2-digit" month="2-digit" year="numeric" hour="2-digit"
                  minute="2-digit"/>
      </template>
      <template #user-data="{ row }">
        {{ row.user.email }}
      </template>
      <template #actions-data="{ row }">
        <UButton color="orange" variant="solid" @click="deleteReport(row.id)" ><span>Supprimer</span></UButton>
      </template>
    </UTable>
    <div class="flex justify-end px-3 py-3.5 border-t border-gray-200 dark:border-gray-700" v-if="pageTotal>1">
      <UPagination @click="fetchReport" v-model="page" :page-count="pageCount" :total="pageTotal"/>
    </div>
  </div>
</template>

<script setup lang="ts">
import {callAPI} from "~/services/callAPI";
import {onMounted, ref} from "vue";
import type {Reports} from "~/model/Reports";
import type {Pages} from "~/model/Pages";
import NavbarVertical from "~/components/navbar_vertical.vue";
import Header_title from "~/components/header_title.vue";

const columns = [
  {key: "id", label: "ID"},
  {key: "content", label: "Rapport", sortable: true},
  {key: "createdAt", label: "Date de création", sortable: true},
  {key: "reportDate", label: "Date d'enregistrement", sortable: true},
  {key: "user", label: "Utilisateur (Email)", sortable: true},
  {key: "actions", label: "Actions"}
];

const api = new callAPI();
const report = ref<Reports[]>([]);
const page = ref(1)
let pageCount = 1;
let pageTotal = 0;

// Fonction pour récupérer les données de l'API
async function fetchReport() {
  try {
    const data = await api.fetchAPIGetPaginated("report", page.value - 1, 20) as Pages;
    if (data?.content) {
      report.value = data.content as Reports[];
      pageTotal = data.totalPages;
    } else {
      console.warn("No content found in API response");
    }
  } catch (error) {
    console.error("Error fetching report data:", error);
  }
}

async function deleteReport(id: number) {
  try {
    await api.fetchAPIDelete("report", id);
    await fetchReport();
  } catch (error) {
    console.error("Error fetching report data:", error);
  }
}

onMounted(fetchReport);
</script>

<style scoped>
/* Ajoutez vos styles ici */
</style>
