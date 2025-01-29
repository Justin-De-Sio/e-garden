<template>
  <div class="wrapper_page">
    <NavbarVertical></NavbarVertical>
    <div class="header_bis">
      <header_title title="Table Événement" subtitle="Gestion de tous les événements"></header_title>
    </div>
    <h1>Table de tous les événements</h1>
    <div v-if="!notifs.length">
      <UTable
          loading
          :loading-state="{ icon: 'i-heroicons-arrow-path-20-solid', label: 'Loading...' }"
          :progress="{ color: 'primary', animation: 'carousel' }"
          class="w-full"
          :columns="columns"
          :ui="{ th: { base: 'sticky top-0' }, wrapper: { base: '' } }"
      />
    </div>
    <UTable v-else :columns="columns" :rows="notifs">
      <template #createdAt-data="{ row }">
        <NuxtTime :datetime="row.createdAt" day="2-digit" month="2-digit" year="numeric" hour="2-digit"
                  minute="2-digit"/>
      </template>
    </UTable>
    <div class="flex justify-end px-3 py-3.5 border-t border-gray-200 dark:border-gray-700" v-if="pageTotal>1">
      <UPagination @click="fetchNotifs" v-model="page" :page-count="pageCount" :total="pageTotal"/>
    </div>
  </div>
</template>

<script setup lang="ts">
import {callAPI} from "~/services/callAPI";
import {onMounted, ref} from "vue";
import type {Pages} from "~/model/Pages";
import type {Events} from "~/model/Events";
import NavbarVertical from "~/components/navbar_vertical.vue";
import Header_title from "~/components/header_title.vue";

const columns = [
  {key: "id", label: "ID Événement"},
  {key: "title", label: "Titre", sortable: true},
  {key: "description", label: "Description de l'événement", sortable: true},
  {key: "createdAt", label: "Date de création", sortable: true},
  {key: "userId", label: "Identifiant utilisateur", sortable: true},
];

const api = new callAPI();
const notifs = ref<Events[]>([]);
const page = ref(1)
let pageCount = 1;
let pageTotal = 0;

// Fonction pour récupérer les données de l'API
async function fetchNotifs() {
  try {
    const data = await api.fetchAPIGetPaginated("event", page.value - 1, 20) as Pages;
    if (data?.content) {
      notifs.value = data.content as Events[];
      pageTotal = data.totalPages;
    } else {
      console.warn("No content found in API response");
    }
  } catch (error) {
    console.error("Error fetching report data:", error);
  }
}

onMounted(fetchNotifs);
</script>

<style scoped>

</style>