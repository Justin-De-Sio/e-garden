<template>
  <div class="wrapper_page">
    <NavbarVertical></NavbarVertical>
    <div class="header_bis">
      <header_title title="Réglages" subtitle="Retrouvez tous les réglages d'E-garden ici"></header_title>
    </div>
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
          to="/gestion-users"
          active-class="text-primary"
          inactive-class="text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200"
      >
        Gestion des utilisateurs
      </ULink>
      <br>
    </div>
    <div>
      <infosServer></infosServer>
    </div>
  </div>
</template>

<script setup lang="ts">

import NavbarVertical from "~/components/navbar_vertical.vue";
import Header_title from "~/components/header_title.vue";
import infosServer from "~/components/infos-server.vue";

</script>

<style scoped>

</style>