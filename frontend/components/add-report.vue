<template>
    <UPopover :popper="{ arrow: true }">
      <UButton
        color="white"
        trailing-icon="i-heroicons-plus"
        :ui="{ rounded: 'rounded-lg' }"
      />
  
      <template #panel>
        <div class="px-8 py-4">
          <Placeholder class="h-20 w-48" />
  
          <div class="wrapper_report">
            <ul>
              <UDivider label="RAPPORT NON VALIDÉ" />
              <li
                class="eachReport"
                v-for="(report, index) in NotValidatedReports"
                :key="index"
              >
                <div class="wrapperEach">
                    <p>{{ truncateText(report.content, 15) }}</p>
                    <p>{{ formatDate(report.createdAt) }}</p>
                  <button type="button" class="editReport" @click="editReport(report.content)">
                    Modifier
                  </button>
                </div>
                <UDivider />
              </li>
            </ul>
          </div>
  
          <UForm :schema="schema" :state="state" class="space-y-4" @submit="onSubmit">
            <UFormGroup label="Contenu" name="content">
              <UTextarea v-model="state.content" type="text" placeholder="Veuillez choisir un compte-rendu à modifier " />
            </UFormGroup>
            <div class="placement_button">
                <UButton type="submit" :ui="{variant: {
                    solid: 'shadow-sm text-white dark:text-gray-900 bg-[#95bd75] hover:bg-[#85ac65] disabled:bg-[#95bd75] aria-disabled:bg-[#95bd75] dark:bg-[#85ac65] dark:hover:bg-[#75a055] dark:disabled:bg-[#85ac65] dark:aria-disabled:bg-[#85ac65] focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-[#95bd75] dark:focus-visible:outline-[#85ac65]',
                    outline: 'ring-1 ring-inset ring-current text-[#95bd75] dark:text-[#85ac65] hover:bg-[#d6e7cb] disabled:bg-transparent aria-disabled:bg-transparent dark:hover:bg-[#4d6c34] dark:disabled:bg-transparent dark:aria-disabled:bg-transparent focus-visible:ring-2 focus-visible:ring-[#95bd75] dark:focus-visible:ring-[#85ac65]',
                    soft: 'text-[#95bd75] dark:text-[#85ac65] bg-[#d6e7cb] hover:bg-[#c6d7bb] disabled:bg-[#d6e7cb] aria-disabled:bg-[#d6e7cb] dark:bg-[#4d6c34] dark:hover:bg-[#3d5c24] dark:disabled:bg-[#4d6c34] dark:aria-disabled:bg-[#4d6c34] focus-visible:ring-2 focus-visible:ring-inset focus-visible:ring-[#95bd75] dark:focus-visible:ring-[#85ac65]',
                    ghost: 'text-[#95bd75] dark:text-[#85ac65] hover:bg-[#d6e7cb] disabled:bg-transparent aria-disabled:bg-transparent dark:hover:bg-[#4d6c34] dark:disabled:bg-transparent dark:aria-disabled:bg-transparent focus-visible:ring-2 focus-visible:ring-inset focus-visible:ring-[#95bd75] dark:focus-visible:ring-[#85ac65]',
                    link: 'text-[#95bd75] hover:text-[#85ac65] disabled:text-[#95bd75] aria-disabled:text-[#95bd75] dark:text-[#85ac65] dark:hover:text-[#75a055] dark:disabled:text-[#85ac65] dark:aria-disabled:text-[#85ac65] underline-offset-4 hover:underline focus-visible:ring-2 focus-visible:ring-inset focus-visible:ring-[#95bd75] dark:focus-visible:ring-[#85ac65]',
                }}">Envoyer</UButton>
                <UButton type="button" color="gray" >Brouillon</UButton>
            </div>
          </UForm>
        </div>
      </template>
    </UPopover>
  </template>
  
  <script setup lang="ts">
  import { z } from "zod";
  import { reactive, ref, onMounted } from "vue";
  import type { Reports } from "~/model/Reports";
  import { callAPI } from "~/services/callAPI";



    const truncateText = (text: string | undefined, maxLength: number = 15): string => {
    if (!text) return "Aucun Contenu"; 
    return text.length > maxLength ? text.slice(0, maxLength) + "..." : text;
    };

  interface PaginatedResponse {
    content: Reports[];
    pageNumber: number;
    pageSize: number;
    totalElements: number;
    totalPages: number;
  }



  const api = new callAPI();
  
  const schema = z.object({
    content: z.string().min(8, "Must be at least 8 characters"),
  });
  
  type Schema = z.output<typeof schema>;
  
  const state = reactive({
    content: "",
  });

  function editReport(content: string) {
    state.content = content; 
    }
  
  const NotValidatedReports = ref<Reports[]>([]);
  
  onMounted(async () => {
    await fetchReports();
  });
  
  async function fetchReports() {
    try {
      const response = await api.fetchAPIGetPaginated("report/myNotValidatedReports",0,3) as PaginatedResponse;
    console.log(response);
      NotValidatedReports.value = response.content;
    } catch (error) {
      console.error("Erreur lors de la récupération des rapports :", error);
    }
  }
  
  async function onSubmit() {
    console.log("Données soumises :", state);
    await fetchReports();
  }
  
  function formatDate(date: string) {
    const options: Intl.DateTimeFormatOptions = {
      year: "numeric",
      month: "long",
      day: "numeric",
    };
    return new Date(date).toLocaleDateString("fr-FR", options);
  }
  </script>
  
  <style scoped>
  .wrapperEach {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 5rem;
    margin-bottom: 1rem;
  } 
  
  .eachReport {
    margin: 1rem 0;
  }
  
  .editReport {
    cursor: pointer;
    background: none;
    border: none;
    padding: min(0.8vw, 5px) min(2vw, 12px); 
    background-color: #f79d65;
    color: white;
    border-radius: 0.4rem;
    font-size: clamp(0.7rem, 2vw, 0.9rem);
  }

  .wrapperEach p {
    font-size: clamp(0.7rem, 2vw, 0.9rem);
  }

  .placement_button{
    display: flex;
    gap: 1rem;
  }
  </style>
  