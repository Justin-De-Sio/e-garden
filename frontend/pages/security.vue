<template>
  <div class="wrapper_page">
    <div class="left_side">
      <navbar />
    </div>
    <div class="right_side">
      <div class="placement_header_title"> <!-- Mettre la width a 100%-->
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

        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import Indicators from "~/components/indicators.vue";
import Header_title from "~/components/header_title.vue";
import navbar from "~/components/navbar.vue";
import { JWTPayload } from "~/services/jwtpayload";

definePageMeta({
  middleware: "auth",
});

const sessionCookie = useCookie("session");
const token = sessionCookie.value;

const rolePayload = JWTPayload(token);

</script>

<style>
body {
  background-color: #f2f4ef;
}

.wrapper_page {
  display: flex;
  width: 100%;
  height: 100vh;
  height: 100dvh;
}
.left_side {
  height: 100vh;
}

.right_side {
  display: flex;
  flex-direction: column;
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
  display: flex;
  margin-top: 4vh;
}
.flux_video{
  margin-top: 1.5vh;
  width: 35rem;
  height: 25rem;
  background-color: #dad7cd;
  border-radius: 20px;
}

</style>
