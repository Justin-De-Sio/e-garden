<template>
  <div class="wrapper_page">
    <div class="left_side">
      <navbar />
    </div>
    <div class="right_side">
      <div class="placement_header_title">
        <Header_title title="Section Sécurité" subtitle="Retrouvez toute la sécurité de votre potager, ici même !" />
      </div>
      <Indicators
        iconPath="/_nuxt/assets/passages.png"
        iconBackgroundColor="#95BD75"
        title="XX passages"
        subtitle="Nombre de passage"
    />
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
}

.left_side {
  height: 100vh;
}

.right_side {
  display: flex;
  flex-direction: column;
  padding: 2vw; 
  flex: 1;
}

.placement_header_title {
  margin: 2vw 2vw; 
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

</style>
