<template>
  <div class="wrapper_page">
    <div class="navbar">
      <NavbarVertical></NavbarVertical>
    </div>
    <div class="wrapper_padding">
      <h2 class="title_setting">Paramètres du potager</h2>
      <div class="wrapper_settings_page">
          <div class="left_section">
            <ul>
              <li 
              v-for="(item, index) in items" 
              :key="index"
              :class="{ selected: selectedItem === index }"
              @click="selectedItem = index"
              >
                {{ item }}
              </li>
            </ul>
          </div>
          <hr class="separator">
          <div class="right_section">
            <div class="header">
              <h3>Les liens</h3>
            </div>
            <div class="grid_card" >
              <button class="card" v-for="card, index in card_details" :key="index">
                <div class="top">
                  <img :src=card.icon alt="" class="icon-card">
                </div>
                <div class="bottom">
                  <h2>{{card.title}}</h2>
                  <h3>{{card.subtitle}}</h3>
                </div>
              </button>
            </div>
          </div>
        </div>
      </div>
  </div>
    <!--
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
  -->
</template>

<script setup lang="ts">

import NavbarVertical from "~/components/navbar_vertical.vue";
import Header_title from "~/components/header_title.vue";
import infosServer from "~/components/infos-server.vue";

definePageMeta({
  middleware: "auth",
  role: ["ADMINISTRATEUR"],
})

const items = ["Gestions du potager", "Portes"];

const card_details = [
  {
    title:"Rapports", 
    subtitle: "Test1",
    icon: "/assets/image_gestionnaire.jpg"
  },
  { 
    title:"Notifications", 
    subtitle: "Test2",
    icon: "/assets/image-notifications.jpg"
  },
  { 
    title:"Utilisateurs", 
    subtitle: "Test3",
    icon: "/assets/image_users.jpg"
  }];

const selectedItem = ref(0);
</script>

<style scoped>

.wrapper_page {
    min-height: 100vh; 
    display: flex;
    flex-direction: column;
}

.wrapper_padding {
    flex-grow: 1; 
    display: flex;
    flex-direction: column;
    padding: 2rem;
}
.title_setting{
  font-family: "Aeonik-Regular";
  color: black;
  font-size: clamp(0.8rem, 1.3vw, 1.4rem);
  padding-bottom: 1rem;
  padding-left: 1rem;
}
.wrapper_settings_page {
    display: flex;
    width: 100%;
    flex-grow: 1; 
    background-color: white;
    border-radius: 1rem;
    align-items: stretch;
}

.separator {
    width: 2px; 
    height: auto; 
    flex-shrink: 0; 
    background-color: #F3F5F7;
}

.left_section {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    flex: 1;
    height: 100%; 
    padding-top: 3rem;

}

.left_section ul {
  display: flex;
  flex-direction: column;

  gap: 1rem
}

.left_section ul li{
  display: inline-block;
  width: fit-content;
  white-space: nonwrap;
  padding: 0.9rem 1rem;
  border-radius: 1.5rem;
  cursor: pointer;
  color: #787D85;
  transition: background-color 0.3s ease;
}

.right_section {
  display: flex;
  flex-direction: column;
  flex: 6;
  height: 100%;
}

.left_section ul li.selected{
  background-color: #95bd75a5;
  color: #283618;
}

.grid_card{
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  width: 100%;
  height: 100%;
  gap: 3rem;
  padding: 4rem 6rem;

}

  .card {
  width: 100%;
  height: 20rem;
  background-color: #f5f5f5;
  border-radius: 1rem;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}


.card .top {
  flex: 1; 
  width: 100%;
  min-height: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #e0e0e0;
}

.card .bottom {
  flex: 1; 
  display: flex;
  flex-direction: column;
  text-align: center;
  align-items: flex-start;
  padding: 1rem;
}

.bottom h2{
  font-family: "Aeonik-Regular";
  color: black;
  font-size: clamp(0.8rem, 1.3vw, 1.4rem);
}

.bottom h3{
  font-family: "Aeonik-Regular";
  color: black;
  font-size: clamp(0.8rem, 1.3vw, 1rem);
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1);
}

.icon-card {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}



</style>