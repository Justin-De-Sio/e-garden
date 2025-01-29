<template>
    <UPopover :popper="{ arrow: true }">
      <UButton
        color="primary"
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
                  <button type="button" class="editReport" @click="editReport(report.content, report.id)">
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
                <UButton type="button" @click="onSubmit(true)">Envoyer</UButton>
                <UButton type="button" color="gray" @click="onSubmit(false)">Brouillon</UButton>
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
  const id_report = ref<bigint | undefined>();

  
  const schema = z.object({
    content: z.string().min(8, "Must be at least 8 characters"),
  });
  
  type Schema = z.output<typeof schema>;
  
  const state = reactive({
    content: "",
  });

  function editReport(content: string, id?: bigint) {
    state.content = content; 
    id_report.value = id
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

  const RequestValidatedReport = reactive({
    content: state.content,
    validated: true,

  });  

  async function onSubmit(isFinal: boolean) {
    try{
        const reportId = Number(id_report.value);
        RequestValidatedReport.content = state.content;
        RequestValidatedReport.validated = isFinal;

        const response = await api.fetchAPIPutWithId("report", reportId, RequestValidatedReport);
        console.log("test",response);

    }catch(error){
        console.error("Erreur lors de l'envoie des rapports :", error);}
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
  