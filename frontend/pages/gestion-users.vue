<template>
  <NavbarVertical></NavbarVertical>
  <div class="header_section">
    <Header_title title="Gestion utilisateurs" subtitle="Gestion des habilitations / rôles" class="header_title"/>
  </div>
  <div class="left_section">
    <div class="tab_gestion scrollable">
      <gestion_tab/>
    </div>
  </div>
</template>

<script setup lang="ts">
import Header_title from '~/components/header_title.vue';
import gestion_tab from '~/components/gestion-all-users.vue';
import NavbarVertical from "~/components/navbar_vertical.vue";
</script>

<style>

body {
  background-color: #f2f4ef;
}

.header_section {
  width: 100%;
  height: clamp(5rem, 9vw, 10rem);
  margin-left: 4vw;
}

.header_title {
  margin-top: 5vh;
}

h1 {
  font-family: "Gilroy-Medium", sans-serif;
  color: black;
  font-size: 2rem;
}

h2 {
  font-family: "Gilroy-Medium", sans-serif;
  margin-bottom: 2vh;
}

h3 {
  font-family: "Gilroy-Regular", sans-serif;
  color: #969696;
  font-size: 1rem;
}

.left_section {
  width: 100%;
  height: 100%;
  margin-left: 4vw;
}


.tab_gestion {
  display: flex;
  gap: 2rem;
  width: 100%;
  border-radius: 5px;

}

.scrollable {
  max-height: 30rem; /* Limitez la hauteur selon vos besoins */
  overflow: hidden;
  overflow-y: auto; /* Ajoute un ascenseur vertical */
}
</style>
