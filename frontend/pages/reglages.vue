<template>
  <div class="wrapper_page">
    <NavbarVertical></NavbarVertical>
    <h1>Page des réglages de E-garden</h1>
    <div>
      <h2>Afficher les données</h2>
      <ULink
          to="/gestion-notifications"
          active-class="text-primary"
          inactive-class="text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200"
      >
        Gestion des notifications
      </ULink>
      <br>
      <ULink
          to="/gestion-reports"
          active-class="text-primary"
          inactive-class="text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200"
      >
        Gestion des rapports
      </ULink>
      <br>
      <ULink
          to="/gestionnaires"
          active-class="text-primary"
          inactive-class="text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200"
      >
        Gestion des utilisateurs
      </ULink>
      <br>
    </div>
    <div>
      <ServeurInfo></ServeurInfo>
    </div>
  </div>
</template>

<script setup lang="ts">

import NavbarVertical from "~/components/navbar_vertical.vue";
</script>

<style scoped>

</style>