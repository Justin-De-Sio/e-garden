
<template>
<!--
  <div id="app">
    <CustomComponent
        iconPath="/_nuxt/assets/conge.png"
        iconBackgroundColor="#95BD75"
        title="XX passages"
        subtitle="Nombre de passage"
    />
  </div>

  <div id="app">
    <CustomComponent
        iconPath="/_nuxt/assets/disque.png"
        iconBackgroundColor="#457B9D54"
        title="XX% utilisé"
        subtitle="stockage disque dur"
    />
  </div>

  <div id="app">
    <CustomComponent
        iconPath="/_nuxt/assets/attention.png"
        iconBackgroundColor="#DA555280"
        title="XX absences de CR"
        subtitle="Alertes"
    />
  </div>

  <navbar></navbar>

  import notification from "~/components/notification.vue";
await notification (0,5);-->
  <div class="wrapper_notification_component">
    <notification></notification>
  </div>
</template>


<script setup lang="ts">



import { Notification } from "~/services/notification";
import CustomComponent from '@/components/indicator.vue';

import Header_title from "~/components/header_title.vue";
import navbar from "~/components/navbar.vue";
import { JWTPayload } from "~/services/jwtpayload";



const result = Notification(1, 10);


definePageMeta({
  middleware: "auth",
});

const sessionCookie = useCookie("session");
const token = sessionCookie.value;

const rolePayload = JWTPayload(token);
const test = { role: rolePayload };
console.log("Role Payload:", rolePayload);
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