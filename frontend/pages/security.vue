<template>
  <div class="wrapper_page">
    <div class="left_side">
      <navbar class="navbar"/>
    </div>
    <div class="main_right_container">
      <div class="header_section">
        <div class="wrapper_notification_component">
          <notification></notification>
        </div>
        <Header_title title="Section Sécurité" subtitle="Retrouvez toute la sécurité de votre potager, ici même !" class="header_title"/>
        <!-- Component Add Report  -->
      </div>
      <div class="grid_indicators_camera_report_calendar">
        <div class="left_section">
          <div class="title_indicator">
            <h2>Indicateurs</h2>
          </div>
          <div class="indicators_section">
            <Indicators
              iconPath="/assets/passages.png"
              iconBackgroundColor="#95BD75"
              :title="`${reportData} passages`"
              subtitle="Nombre de passage"
            />
            <Indicators
              iconPath="/assets/exclamation.png"
              iconBackgroundColor="#e39695"
              :title="`${reportData_report} alertes`"
              subtitle="Alertes"
            />
          </div>
          <div class="placement_camera_report">
            <div class="camera_section">
              <h2>Caméra</h2>
                <Camera></Camera>
            </div>
            <div class="report_section">
              <h2>Compte-rendus</h2>
              <Report />
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
</template>

<script setup lang="ts">
import { Notification } from "~/services/notification";
import Indicators from "~/components/indicators.vue";
import Header_title from "~/components/header_title.vue";
import Notification_component from "~/components/notification.vue";
import navbar from "~/components/navbar.vue";
import Report from "~/components/report.vue";
import Camera from "~/components/camera.vue";
import { JWTPayload } from "~/services/jwtpayload";
import notification from "~/components/notification.vue"


const result = Notification(1, 10);

import {fetchBackend} from "~/services/call_backend"
import {fetchBackend_URL} from "~/services/call_backend"

const reportData=ref(0);
const reportData_report=ref(0);
definePageMeta({
  middleware: "auth"
});

onMounted(async () => {
    try {
        const data_stats = await fetchBackend_URL('/api/event/statistique');
        const data_stats_report = await fetchBackend_URL('/api/report/statistique');
        reportData.value = data_stats;
        reportData_report.value = data_stats_report;
        console.log("Stats:",data_stats);
    } catch (error) {
        console.error('Erreur lors de la récupération des données :', error);
    }
});

</script>

<style scoped>


body {
  background-color: #f2f4ef;
}

.wrapper_notification_component{
  display: flex;
  justify-content: right;
  align-items: center;
}

.wrapper_page {
  display: flex;
  width: 100%;
  max-width: 100%;
  height: 100vh;
  height: 100dvh;
}

.left_side {

  height: 100%;
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
  flex-direction: column;
  padding: 2vw; 
  flex: 1;
  margin-left: 2vw
}

.placement_header_title {
  margin-top: 2.5vw; 
}

h1 {
  font-family: "Gilroy-Bold";
  color: black;
  font-size: 2rem; 
}

h3 {
  font-family: "Gilroy-Medium";
  color: #969696;
  font-size: 1rem; 
}

h2{
  font-family: "Gilroy-Medium";
}

.placement_indicators_header h2{
  margin-top: 4vw; 
  font-size: 1.6rem;
  margin-bottom: 1rem;
}

.placement_indicators_components{
  display: flex;
  gap: 1.5rem;
}


 */
</style>
