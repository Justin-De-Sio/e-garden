<template>
  <div class="wrapperContainer">
    <div class="wrapperContrainerHeader">
      <button type="button" class="moreReport" @click="$router.push('/gestion-reports')">Voir plus</button>
      <div class="chevron">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
             class="size-6" @click="changePage('prev')">
          <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5"/>
        </svg>
        <h2>{{ currentPage + 1 }}</h2>
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
             class="size-6" @click="changePage('next')">
          <path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5"/>
        </svg>
      </div>
    </div>
    <hr class="separator">

    <div v-for="(report,index) in reportsObject" :key="index" class="main_container_content">
      <div class="container_content">
        <div class="head_content">
          <h2>{{ report.user.name }} {{ report.user.surname }}</h2>
          <h4>{{ report.user.className }} - Grp 0{{ report.user.groupNumber }}</h4>
        </div>
        <div class="body_content">
          <p>{{ report.content || "Aucun contenu" }}</p>
        </div>
      </div>
      <hr class="content-separator" />
    </div>
  </div>
</template>

<script setup lang="ts">
import {callAPI} from '~/services/callAPI';
import type {Reports} from '~/model/Reports';
import type {Pages} from '~/model/Pages';

const api = new callAPI();
const reportsObject = ref<Reports[]>([]);
const pageObject = ref();
const currentPage = ref(0);

const changePage = (direction: 'prev' | 'next') => {
  if (direction === 'prev' && currentPage.value > 0) {
    currentPage.value--;
  } else if (direction === 'next' && currentPage.value < pageObject.value.totalPages - 1) {
    currentPage.value++;
  }
  fetchNotif(currentPage.value);
};

const fetchNotif = async (page: number) => {
  const response = await api.fetchAPIGetPaginated("report", page, 4) as Pages;
  pageObject.value = response;
  reportsObject.value = response.content as Reports[];
};

onMounted(async () => {
  await fetchNotif(currentPage.value)
});
</script>

<style scoped>
.wrapperContainer {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  gap: 1rem;
  background-color: white;
  border-radius: 20px;
  padding: 1.5rem;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.wrapperContrainerHeader {
  display: flex;
  width: 100%;
  justify-content: space-between;
}

.moreReport {
  background: none;
  border: none;
  background-color: #95BD75;
  border-radius: 0.5rem;
  padding: 0.3rem 1rem;
  color: white;
  font-size: 0.8rem;
}

.chevron {
  display: flex;
  gap: 1rem;
  padding-right: 1rem;
}

.chevron svg {
  cursor: pointer;
}

.main_container_content {
  width: 100%;
}

.container_content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  gap: 2rem;
}

.head_content {
  display: flex;
  flex-direction: column;
  flex: 1;
}

.body_content {
  display: -webkit-box;
  flex-direction: column;
  flex: 2;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: normal;
  word-break: break-word;
  gap: 0.5rem;
}

.content-separator {
  width: 100%;
  border: none;
  height: 1px;
  background-color: #ddd;
  margin: 1rem 0;
}

.head_content h2 {
  font-family: "Aeonik-Regular";
  font-family: "Aeonik-Regular";
  font-size: clamp(0.85rem, 2vw, 1rem);
}

.head_content h4 {
  font-family: "Aeonik-Regular";
  font-size: clamp(0.76rem, 2vw, 1rem);
}

.body_content p{
  font-family: "Aeonik-Regular";
  font-size: clamp(0.8rem, 2vw, 1rem);
}


@media screen and (max-width: 768px) {
  .container_content {
    flex-direction: column;
    align-items: center;
    gap: 1rem;
  }

  .chevron {
    gap: 0.5rem;
    padding: 0;
  }


}
</style>
