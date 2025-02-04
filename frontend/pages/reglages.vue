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
              v-for="([key, value], index) in Object.entries(items)" 
              :key="index"
              :class="{ selected: selectedItem === key }"
              @click="selectedItem = key"
            >
              {{ value }}
            </li>
          </ul>
        </div>
        <hr class="separator">
        
        <!-- Gestion des cartes -->
        <div class="right_section_gestion" v-if="selectedItem === 'gestion'">
          <div class="header">
            <h3>Les liens</h3>
          </div>
          <div class="grid_card">
            <button class="card" 
              v-for="(card, index) in card_details" 
              :key="index" 
              @click="$router.push(card.route)">
              <div class="top">
                <img :src="card.icon" alt="" class="icon-card">
              </div>
              <div class="bottom">
                <h2>{{ card.title }}</h2>
                <h3>{{ card.subtitle }}</h3>
              </div>
            </button>
          </div>
        </div>


        <div class="right_section_gestion" v-if="selectedItem === 'door'">
          <div class="header">
            <h3>Doors</h3>
          </div>
          <div class="nav_changement">
            <ul ref="navList">
              <div class="background" 
                :style="{ transform: `translateX(${backgroundX}px)`, width: `${backgroundWidth}px` }">
              </div>
              <li v-for="(choice, index) in doors_edit" 
                :key="index" 
                ref="navItems"
                :class="{ selected: selectedDoorEdit === index }"
                @click="updateBackground(index)">
                {{ choice }}
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, nextTick, onMounted, type Ref } from "vue";
import NavbarVertical from "~/components/navbar_vertical.vue";

definePageMeta({
  middleware: "auth",
  role: ["ADMINISTRATEUR"],
});

// Navigation principale
const items = {
  gestion: "Gestions du potager",
  door: "Portes"
};
const selectedItem = ref("door");

// Sections des portes
const doors_edit = ["Supprimer", "Créer", "Modifier"];
const selectedDoorEdit = ref(0);

const backgroundX = ref(0);
const backgroundWidth = ref(0);
const navItems = ref([]) as Ref<HTMLLIElement[]>;


const updateBackground = (index: number) => {
  selectedDoorEdit.value = index;

  nextTick(() => {
    const item = navItems.value[index]; 
    if (item) {
      backgroundX.value = item.offsetLeft ; 
      backgroundWidth.value = item.offsetWidth; 
    }
  });
};

onMounted(() => {
  updateBackground(selectedDoorEdit.value);
});


const card_details = [
  {
    title: "Rapports", 
    subtitle: "Tous les comptes rendus effectués pour un suivi détaillé des rapports.",
    icon: "/assets/image_gestionnaire.jpg",
    route: "/gestion-reports"
  },
  { 
    title: "Notifications", 
    subtitle: "Consultez toutes les notifications pour rester informé en temps réel des derniers évènements.",
    icon: "/assets/image-notifications.jpg",
    route: "/gestion-notifications"
  },
  { 
    title: "Utilisateurs", 
    subtitle: "Gérez et visualisez les informations de tous les utilisateurs.",
    icon: "/assets/image_users.jpg",
    route: "/gestion-users"
  }
];
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
  font-size: clamp(1.2rem, 1.3vw, 1.4rem);
  padding-bottom: 1rem;
  padding-left: 1rem;
}
.wrapper_settings_page {
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    width: 100%;
    max-width: 100%;
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
  font-family: "Aeonik-Regular";
  color: black;
  font-size: clamp(0.8rem, 1.3vw, 1rem);
  transition: background-color 0.3s ease;
}

.right_section_gestion {
  display: flex;
  flex-direction: column;
  flex: 6;
  height: 100%;
}

.header{
  text-align: center;
}

.header h3{
  font-family: "Aeonik-Regular";
  color: black;
  font-size: clamp(1rem, 1.3vw, 1.9rem);
  padding-top: 3rem;
  padding-left: 2rem;
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
  align-items: flex-start;
  text-align: left;
  padding: 1rem 1.3rem;
  gap: 1rem;
}

.bottom h2{
  font-family: "Aeonik-Regular";
  color: black;
  font-size: clamp(1rem, 1.3vw, 1.4rem);
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

/* DOOR SECTION */ 

.nav_changement {
  margin-top: 2rem;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  position: relative;
}

.nav_changement ul {
  padding: 0.4rem;
  border-radius: 2rem;
  width: fit-content;
  background-color: #F7F6F9;
  display: flex;
  position: relative;
  gap: 1rem;
  overflow: hidden;
}

.nav_changement .background {
  position: absolute;
  top: 4px;
  left: 0;
  height: calc(100% - 8px);
  background-color: white;
  border-radius: 2rem;
  transition: transform 0.3s ease-in-out, width 0.3s ease-in-out;
}

.nav_changement ul li {
  padding: 0.3rem 1.5rem;
  border-radius: 2rem;
  font-family: "Aeonik-Regular";
  color: #99989D;
  font-size: clamp(1rem, 1.3vw, 1rem);
  position: relative;
  cursor: pointer;
  z-index: 1;
}

.nav_changement ul li.selected {
  color: black;
}


@media screen and (max-width: 1024px)
{
  .wrapper_settings_page{
    flex-direction: column;
  }

  .left_section{
      padding-top: 1rem;
  }
  .left_section ul{
    flex-direction: row;
  }

  .left_section ul li{
    padding: 0.5rem 1rem;
  }

  .grid_card{
    padding: 2rem;
  }

  .separator{
    margin-top: 1rem;
    width: 100%;

  }

  .header h3{
    padding-top: 2rem;
    padding-left: 0;
  }

}

</style>