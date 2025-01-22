<template>
  <div class="wrapper_page">
    <div class="left_side">
      <navbar />
    </div>
    <div class="main_right_container">
      <div class="header_section">
        <Header_title title="Section Gestion" subtitle="Gestion des Utilisateurs / Rôle" class="header_title" />
      </div>
  <div class="component_ajouter">
    <ajouter/>
  </div>
      <div class="left_section">
        <div class="title_indicator">
          <h2>Tableau de gestion</h2>
        </div>
        <div class="tab_gestion scrollable">
          <!-- Passez les données et les colonnes au composant gestion_tab -->
          <gestion_tab :columns="columns" :rows="people" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import Header_title from '~/components/header_title.vue';
import navbar from '~/components/navbar.vue';
import gestion_tab from '~/components/gestion_tab.vue';
import { fetchBackend2 } from '~/services/call_backend';

const people = ref([]); // Déclarez une variable réactive pour les données
const columns = [
  { key: 'surname', label: 'Nom' },
  { key: 'name', label: 'Prénom' },
  { key: 'email', label: 'Email' },
  { key: 'className', label: 'Classe' },
  { key: 'groupNumber', label: 'Groupe' },
  { key: 'role', label: 'Rôle' },
];

onMounted(async () => {
  try {
    const response = await fetchBackend2('/api/user/all');
    people.value = response; // Assignez les données récupérées à `people`
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
  height: 100%;
  /*background-color: aqua;*/
}

.main_right_container {
  /*background-color: blueviolet;*/
  width: 100%;
  height: 100vh;
}

.header_section {
  width: 100%;
  height: clamp(5rem, 9vw, 10rem);
  margin-left: 4vw;
  /*  background-color: blue; */
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
  width: auto;
  height: auto;
  margin-left: 4vw;
}

.component_ajouter{
  z-index:1;
}

.tab_gestion {
  display: flex;
  gap: 2rem;
  width: 100%;
  z-index:2;
  border-radius: 5px;

}

.scrollable {
  z-index: 2;
  max-height: 30rem; /* Limitez la hauteur selon vos besoins */
  overflow: hidden;
  overflow-y: auto; /* Ajoute un ascenseur vertical */
}
</style>
