<template>
    <div class="wrapper_page">
      <NavbarVertical></NavbarVertical>
      <div class="wrapper_content">
      <div class="header_bis">
        <header_title title="Section Sécurité" subtitle="Retrouvez tous les indicateurs pour la gestion de votre potager"></header_title>
      </div>
      <div class="top_wrapper">
        <div class="top_left">
          <div class="columns_indicators">
            <indicators 
            iconBackgroundColor="#95BD75"
            iconPath="/assets/passages.png"
            subtitle="Sur les 30 derniers jours"
            :title="`${number_passage || 0} passages`"
            ></indicators>
            <indicators 
            iconBackgroundColor="#ECA9A8"
            iconPath="/assets/exclamation.png"
            subtitle="Sur les 30 derniers jours"
            :title="`${number_cr || 0} rapports non remis`"
            ></indicators>
          </div>
        </div>
        <div class="bot_left">
          <div class="top_bot_left">
            <h2>Caméra</h2>
            <camera></camera>
          </div>
          <div class="top_bot_right">
            <h2>Comptes-rendus <u-link to="/gestion-reports">(voir plus)</u-link></h2>
              <newReport></newReport>
          </div>
        </div>

      </div>
    </div>
    <footer>
    E-Garden • © 2025
  </footer>
  </div>

</template>

<script setup lang="ts">
  import indicators from "~/components/indicators.vue";
  import header_title from "~/components/header_title.vue";
  import camera from "~/components/camera-direct.vue";
  import NavbarVertical from "~/components/navbar_vertical.vue"
  import report from "~/components/report.vue";
  import {callAPI}  from "~/services/callAPI";
      import newReport from '~/components/new-report.vue'

  const api = new callAPI;
  const number_passage = ref();
  const number_cr = ref();
  definePageMeta({
    middleware: "auth",
    role: ["ADMINISTRATEUR"],
  })

  async function getData() {
  try {
    const stats_passages = await api.fetchAPIGet("/event/statistique");
    const stats_cr = await api.fetchAPIGet("/report/statistique");
    number_passage.value = stats_passages;
    number_cr.value = stats_cr;
  } catch (error) {
    console.error("Erreur :", error); 
  }
  }

  onMounted(() => {
      getData()
  })



</script>

<style scoped>

.wrapper_page{
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
  max-width: 100vw;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
.wrapper_content{
  display: flex;
  flex-direction: column;
  padding: 0 2rem;
  flex-grow: 1;
}
.header_bis{
  width: 100vw;
  height: 100%;
  padding: 2rem 0;
}


.columns_indicators{
  display: flex;
  flex-direction: row;
  gap: 1rem;
}

.top_wrapper{
  width: 100%;
  height: 100%;
}
.bot_left{
  width: 70%;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  margin: 3rem 0;
  gap: 5rem;
}

.top_bot_left{
  width: 100%;
  height: auto;
}

.top_bot_left h2{
  font-family: "Gilroy-Medium";
  font-size: clamp(1rem, 2vw, 1.5rem); 
  margin-bottom: 2rem;

}

.top_bot_right h2{
  font-family: "Gilroy-Medium";
  font-size: clamp(1rem, 2vw, 1.5rem); 
  margin-bottom: 2rem;

}

footer{ 
  font-family: "Gilroy-Medium";
  text-align: center;
  padding: 1rem 0;
  width: 100%;
}


 @media screen and (max-width: 800px){
  .wrapper_content {
  display: flex;
  flex-direction: column;
  justify-content: center;  /* Centre verticalement */
  align-items: center;  /* Centre horizontalement */
  width: 100%;
  padding: 0 1rem;
}

.bot_left{
  width: 100%;
}

.header_bis{
  text-align: center;
}

 }

</style>