<template>
  <div class="wrapper_page">
    <div class="left_side">
      <navbar />
    </div>
    <div class="main_right_container">
      <div class="header_section">
        <!-- Component Header Title -->
        <Header_title title="Section Sécurité" subtitle="Retrouvez toute la sécurité de votre potager, ici même !" class="header_title"/>
        <!-- Component Notification -->
        <!-- Component Add Report  -->
      </div>
      <div class="grid_indicators_camera_report_calendar">
        <div class="left_section">
          <div class="title_indicator">
            <h2>Indicateurs</h2>
          </div>
          <div class="indicators_section">
            <Indicators
              iconPath="/_nuxt/assets/passages.png"
              iconBackgroundColor="#95BD75"
              title="XX passages"
              subtitle="Nombre de passage"
            />
            <Indicators
              iconPath="/_nuxt/assets/exclamation.png"
              iconBackgroundColor="#e39695"
              title="XX alertes"
              subtitle="Alertes"
            />
          </div>
          <div class="placement_camera_report">
            <div class="camera_section">
              <h2>Caméra</h2>
                <Camera></Camera>
            </div>
            <div class="report_section">
              <Report v-if="reportData" :report="reportData"></Report>
            </div>
          </div>
        </div>
        <div class="right_section">
          <div class="calendar_section">

          </div>
        </div>
      </div>
    </div>

  </div>
    <!-- 
    <div class="right_side">
      <div class="left_right_side">
        <div class="placement_header_title"> 
          <Header_title title="Section Sécurité" subtitle="Retrouvez toute la sécurité de votre potager, ici même !" />
        </div>
        <div class="placement_indicators_header">
          <h2>Indicateurs</h2>
          <div class="placement_indicators_components">
            <Indicators
              iconPath="/_nuxt/assets/passages.png"
              iconBackgroundColor="#95BD75"
              title="XX passages"
              subtitle="Nombre de passage"
            />
            <Indicators
              iconPath="/_nuxt/assets/exclamation.png"
              iconBackgroundColor="#e39695"
              title="XX Alertes"
              subtitle="Alertes"
            />

          </div>
        </div>
        <div class="sub_camera_report_placement">
          <div class="placement_camera">
            <h2>Caméra</h2>
            <div class="flux_video"></div>
          </div>
          <div class="placement_report">
            <Report></Report>
          </div>
        </div>
      </div>
      <div class="right_right_side">

      </div>
    </div>
  </div>
  -->
</template>

<script setup lang="ts">
import Indicators from "~/components/indicators.vue";
import Header_title from "~/components/header_title.vue";
import navbar from "~/components/navbar.vue";
import Report from "~/components/report.vue";
import Camera from "~/components/camera.vue";
import { JWTPayload } from "~/services/jwtpayload";
import {Notification} from "~/services/notification";
import {fetchBackend} from "~/services/call_backend"

const reportData=ref();
definePageMeta({
  middleware: "auth"
});

onMounted(async () => {
    try {
        const data = await fetchBackend('/api/report/', 0, 5);
        reportData.value = data;
        console.log(reportData.value); // Utilise les données retournées
    } catch (error) {
        console.error('Erreur lors de la récupération des données :', error);
    }
});

</script>

<style>


body {
  background-color: #f2f4ef;
}

.wrapper_page {
  display: flex;
  width: 100%;
  max-width: 100%;
  height: 100vh;
  height: 100dvh;
}


.left_side {
  height: 100vh;
  /*background-color: aqua;*/ 
}

.main_right_container{
  /*background-color: blueviolet;*/ 
  width: 100%;
  height: 100vh;
}

.header_section{
  width: 100%;
  height: clamp(5rem, 9vw , 10rem);
  margin-left: 4vw ;
  /*  background-color: blue; */  

}

.header_title{
  margin-top: 5vh; 
}

h1 {
  font-family: "Gilroy-Medium", sans-serif;
  color: black;
  font-size: 2rem; 
}

h2{
  font-family: "Gilroy-Medium", sans-serif;
  font-size: 1.5rem; 
  margin-bottom: 2vh;
}

h3 {
  font-family: "Gilroy-Regular", sans-serif;
  color: #969696;
  font-size: 1rem; 
}


.grid_indicators_camera_report_calendar{
  display: grid;
  grid-template-columns: 2fr 1fr;

}

.left_section{

  width: auto;
  height: 100%;
  margin-left: 4vw;

}

.right_section{

  width: auto;
  height: 100vh;

  /*  background-color: aqua;*/  

}

.placement_camera_report{
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  width: 100%;
  gap: 1rem;
}

.indicators_section{
  display: flex;
  gap: 2rem;
  width: 100%;
  height: 5rem;
}

.camera_section{
  width: auto;
  /*  background-color: pink; */  
  margin-top: 2rem;
  height: 25rem;
}

.camera_section p {
  color: black;
}

.report_section{
  width: auto;
  /*   background-color: black;*/  
  margin-top: 2rem;
  height: 25rem;

}
/*
.right_side {
  display: flex;
  flex-direction: row;
  padding: 2vw; 
  flex: 1;
  margin-left: 2vw
}

.placement_header_title {
  margin-top: 2.5vh; 
}

h1 {
  font-family: "Gilroy-Medium", sans-serif;
  color: black;
  font-size: 2rem; 
}

h3 {
  font-family: "Gilroy-Regular", sans-serif;
  color: #969696;
  font-size: 1rem; 
}

h2{
  font-family: "Gilroy-Medium", sans-serif;
}

.placement_indicators_header h2{
  margin-top: 5vh; 
  font-size: clamp(1rem, 1.5vw, 1.5rem); 
  margin-bottom: 1rem;
}

.placement_indicators_components {

  display: flex;
  flex-direction: row;
  gap: 1.5rem;
  width: 100% ;
  max-width: 90%;
}


.sub_camera_report_placement{
  display: grid;
  grid-template-columns: 1fr 1fr;
  margin-top: 4vh;
}
.flux_video{
  margin-top: 1.5vh;
  width: 35rem;
  height: 25rem;
  background-color: #dad7cd;
  border-radius: 20px;
}

.right_right_side{
  background-color: red;
  height: 100vh;
  width: 100%;
}
  */
</style>
