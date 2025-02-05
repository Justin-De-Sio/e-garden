<script setup>
import {callAPIServices} from "~/services/callAPIServices.ts";
import Header_title from "~/components/header_title.vue";
import NavbarVertical from "~/components/navbar_vertical.vue";

definePageMeta({
  middleware: 'auth',
});
const api = new callAPIServices();
const route = useRoute();
const porte = route.query.porte;
const message = ref('');
//const notificationVisible = ref(false);

const enregistrerPassage = async () => {
  try {
    const response = await api.fetchAPIGet(`event/door/${porte}`);
    message.value = response ? 'Passage enregistré, merci de remplir le compte rendu' : 'Vous n\'êtes pas authorisé';
  } catch (error) {
    message.value = 'Erreur lors de l’enregistrement';
  }
};

onMounted(() => {
  if (porte) {
    enregistrerPassage();
  } else {
    message.value = 'Numéro de porte invalide';
  }
});
</script>

<template>
  <div class="wrapper_page">
    <NavbarVertical></NavbarVertical>
    <div class="header_bis">
      <header_title title="Enregistrement de passage" subtitle=""></header_title>
      <p v-if="message">{{ message }}</p>
    </div>
  </div>
<!--  <UNotification-->
<!--      v-if="notificationVisible"-->
<!--      icon="i-heroicons-check-badge"-->
<!--      color="primary"-->
<!--      :id="6"-->
<!--      :title="notificationTitle"-->
<!--      :description=""-->
<!--      :timeout="6000"-->
<!--      class="custom-notification"-->
<!--  />-->
</template>

<style scoped>
.custom-notification {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 1000;
  width: min(50vw, 30rem);
  max-width: 30rem;
}
</style>