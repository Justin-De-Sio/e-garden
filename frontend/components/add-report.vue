<template>
  <UPopover :popper="{ arrow: true }">
    <div class="button_add">
      <button type="button">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
             class="size-6 text-white">
          <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15"/>
        </svg>
      </button>
    </div>
    <template #panel>
      <div class="px-8 py-4">
        <Placeholder class="h-20 w-48"/>
        <div class="wrapper_report">
          <ul>
            <div class="chevron" v-if="NotValidatedReports.length > 0">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="size-6" @click="changePage('prev')">
                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5"/>
              </svg>
              <h2>{{ currentPage + 1 }}</h2>
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="size-6" @click="changePage('next')" v-if="checkNextPage()">
                <path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5"/>
              </svg>
            </div>
            <UDivider label="RAPPORT NON VALIDÉ" v-if="NotValidatedReports.length > 0"/>
            <li
                class="eachReport"
                v-for="(report, index) in NotValidatedReports"
                :key="index"
            >
              <div class="wrapperEach">
                <p>{{ truncateText(report.content, 15) }}</p>
                <p>{{ formatDate(report.createdAt) }}</p>
                <button type="button" class="editReport" @click="editReport(report.content, report.id)"
                        :class="{ 'selected': activeReportId === report.id }">
                  Modifier
                </button>
              </div>
              <UDivider/>
            </li>
          </ul>
        </div>

        <UForm :schema="schema" :state="state" class="space-y-4" @submit="onSubmit">
          <UFormGroup label="Contenu" name="content">
            <UTextarea v-model="state.content" type="text" placeholder="Veuillez choisir un compte-rendu à modifier "/>
          </UFormGroup>
          <p>Date de création {{ state.createdAt || 'aujourd\'hui' }}</p>
          <div class="placement_button">
            <UButton type="button" @click="onSubmit(true)">{{ isCreating ? "Créer" : "Enregistrer" }}</UButton>
            <UButton type="button" color="gray" @click="onSubmit(false)">Brouillon</UButton>
          </div>
        </UForm>
      </div>
    </template>
  </UPopover>
  <UNotification
      v-if="notificationVisible"
      icon="i-heroicons-check-badge"
      color="primary"
      :id="6"
      :title="notificationTitle"
      :description="notificationMessage"
      :timeout="6000"
      class="custom-notification"
  />
</template>

<script setup lang="ts">
import {z} from "zod";
import {onMounted, reactive, ref} from "vue";
import type {Reports} from "~/model/Reports";
import {callAPIServices} from "~/services/callAPIServices";
import type {Pages} from "~/model/Pages";

const notificationVisible = ref(false);
const notificationTitle = ref("");
const notificationMessage = ref("");
const currentPage = ref(0);
const activeReportId = ref<bigint | null>(null);
const isCreating = ref(true);


const truncateText = (text: string | undefined, maxLength: number = 15): string => {
  if (!text) return "Aucun Contenu";
  return text.length > maxLength ? text.slice(0, maxLength) + "..." : text;
};


const changePage = (direction: 'prev' | 'next') => {
  if (direction === 'prev' && currentPage.value > 0) {
    currentPage.value--;
  } else if (direction === 'next' && currentPage.value < totalPages.value - 1) {
    currentPage.value++;
  }
  fetchReports(currentPage.value);
};

const checkNextPage = () => {
  return currentPage.value < totalPages.value - 1;
};

const api = new callAPIServices();
const id_report = ref<bigint | undefined>();

const schema = z.object({
  content: z.string().min(8, "Un minimum de 8 caractères est requis"),
});
const state = reactive({
  content: "",
  createdAt: "",
});

function editReport(content: string, id?: bigint) {
  const selectedReport = NotValidatedReports.value.find(report => report.id === id);

  state.content = content;
  id_report.value = id;
  activeReportId.value = id ?? null;
  isCreating.value = false;
  state.createdAt = selectedReport ? formatDate(selectedReport.createdAt) : "";
}

const NotValidatedReports = ref<Reports[]>([]);

onMounted(async () => {
  await fetchReports(currentPage.value);
});

const totalPages = ref(0);

async function fetchReports(page: number) {
  try {
    const response = await api.fetchAPIGetPaginated("report/myNotValidatedReports", page, 2) as Pages;
    NotValidatedReports.value = response.content as Reports[];
    totalPages.value = response.totalPages;
  } catch (error) {
    console.error("Erreur lors de la récupération des rapports :", error);
  }
}

const RequestValidatedReport = reactive({
  content: state.content,
  validated: true,
});

async function onSubmit(isFinal: boolean) {
  if (state.content.length < 8) {
    return;
  }
  try {
    RequestValidatedReport.content = state.content;
    RequestValidatedReport.validated = isFinal;

    if (isCreating.value) {
      await api.fetchAPIPost("report", RequestValidatedReport);
    } else {
      const reportId = Number(id_report.value);
      await api.fetchAPIPutWithId("report", reportId, RequestValidatedReport);
    }


    if (isFinal) {
      notificationTitle.value = "Succès";
      notificationMessage.value = isCreating.value
          ? "Votre rapport a été créé !"
          : "Votre rapport a bien été enregistré !";
      notificationVisible.value = true;
    } else {
      notificationTitle.value = "Succès";
      notificationMessage.value = "Votre rapport a bien été enregistré en mode brouillon !";
      notificationVisible.value = true;
    }
    await fetchReports(currentPage.value);

    state.content = "";
    state.createdAt = "";
    isCreating.value = true;
    activeReportId.value = null;
    setTimeout(() => {
      notificationVisible.value = false;
    }, 6000);
  } catch (error) {
    console.error("Erreur lors de l'envoie des rapports :", error);
  }
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

.editReport.selected {
  cursor: pointer;
  background: none;
  border: none;
  padding: min(0.8vw, 5px) min(2vw, 12px);
  background-color: #f79d65;
  opacity: 0.5;
  color: white;
  border-radius: 0.4rem;
  font-size: clamp(0.7rem, 2vw, 0.9rem);
}

.wrapperEach p {
  font-size: clamp(0.7rem, 2vw, 0.9rem);
}

.placement_button {
  display: flex;
  gap: 1rem;
}

.custom-notification {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 1000;
  width: min(50vw, 30rem);
  max-width: 30rem;
}

.chevron {
  display: flex;
  margin-left: auto;
  gap: 1rem;
  position: sticky;
  padding-right: 1rem;
}

.chevron svg {
  cursor: pointer;
}

.button_add button {
  background: none;
  border: none;
  width: min(7vw, 2rem);
  height: min(7vw, 2rem);
  background-color: #95bd75;
  border-radius: 0.5rem;
  margin: auto;
  margin-top: 0.2rem;
  cursor: pointer;
}

.button_add button:hover {
  opacity: 0.7;
}

.button_add button svg {
  margin: auto;
  width: min(4vw, 1.2rem);
  height: min(4vw, 1.2rem);
}
</style>
  