<template>  
    <div class="wrapperContainer">
        <div class="wrapperContrainerHeader">
          <button type="button" class="moreReport" @click="$router.push('/gestion-reports')">Voir plus</button>
            <div class="chevron">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6" @click="changePage('prev')" >
                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
              </svg>
              <h2>{{ currentPage + 1 }}</h2>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6" @click="changePage('next')"  >
              <path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" />
            </svg>
         </div>
        </div>
        <div class="container" v-for="(report,index) in reportsObject" :key="index">
            <img :src="isCompleted(report.validated)" class="validationIcon">
            <div class="header" >
                <img src="/public/assets/avatar_profil.png" class="avatarImg">
                <div class="informations">
                    <h2>{{ report.user.name }} {{ report.user.surname }}</h2>
                    <h4>{{ report.user.className }} - Grp 0{{ report.user.groupNumber }}</h4>
                </div>
            </div>
            <div class="footer">
                <p>{{report.content || "Aucun contenu"}}</p>
            </div>
            <hr class="separator">
        </div>
    </div>
</template>

<script setup lang="ts">
    import { callAPI } from '~/services/callAPI';
    import type { Reports } from '~/model/Reports';
    import type { Pages } from '~/model/Pages';


    const api = new callAPI();
    const reportsObject = ref<Reports[]>([]);
    const pageObject = ref();
    const currentPage = ref(0);

    const isCompleted = (isValidated: boolean) => {
        return isValidated ? "assets/cr_done.png" : "assets/cr_empty.png";
    };

    const changePage = (direction: 'prev' | 'next') => {
        if (direction === 'prev' && currentPage.value > 0) {
            currentPage.value--;
        } else if (direction === 'next' && currentPage.value < pageObject.value.totalPages - 1 ) {
            currentPage.value++;
        }
        fetchNotif(currentPage.value);
    };

    const fetchNotif = async (page: number) => {
        const response = await api.fetchAPIGetPaginated("report", page, 2) as Pages;
        pageObject.value = response;
        console.log("CurrentPage", currentPage.value)
        reportsObject.value = response.content as Reports[];

    };

    onMounted( async () => {
        fetchNotif(currentPage.value)
    });

    

    
</script>

<style scoped>
    .wrapperContainer{
        display: flex;
        flex-direction: column;
        align-items: center;
        width: 100%;
        height: 100%;
        max-height: 22rem;
        gap: 1rem;
        background-color: white;
        border-radius: 20px;
        padding: 1rem 1rem;
    }

    .wrapperContrainerHeader{
        display: flex;
        width: 100%;
        justify-content: space-between;
    }

    .moreReport{
        background: none;
        border: none;
        background-color: #95BD75;
        border-radius: 0.5rem;
        padding: 0.3rem 1rem;
        color: white;
        font-size: 0.8rem;
    }


    .chevron{
        display: flex;  
        gap: 1rem;
        position: sticky;
        padding-right: 1rem;
    }

    .chevron svg{
        cursor: pointer;
    }

    .container{
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        text-align: center;
        width: 100%;
        border-radius: 1rem;
        position: relative;
    }

    .header{
        display: flex;
        flex-direction: row;
        font-family:"Aeonik";
        padding-top: 0.8rem; 
    }

    .informations{
        padding-left: 0.5rem;
    }

    .validationIcon{
        position: absolute;
        top: 10px;
        right: 10px;
        width: 20px;
        height: 20px;
    }

    .header h2{
        font-size: 1.2rem;
        font-weight: 500;
    }


    .header h4{
        font-size: 1rem;
        font-weight: 200;
    }

    .header .avatarImg{
        flex-shrink: 0;
        width: 60px;
        height: 60px;
        object-fit: cover;
    }

    .footer{
        display: -webkit-box;
        -webkit-line-clamp: 2; 
        -webkit-box-orient: vertical;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: normal;
        padding: 0 5%;
        padding-bottom: 1rem;
        max-height: 3.3em; 
        max-width: 1000px;
    }

    .footer p{
        font-family:"Aeonik";
        font-weight: regular;
    }

    .separator {
        width: 80%;
        height: 1px;
        background-color: #ccc; 
        border: none;
        margin: 10px 0;
    }



</style>