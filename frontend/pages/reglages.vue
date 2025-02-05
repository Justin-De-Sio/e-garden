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


        <div class="right_section_doors" v-if="selectedItem === 'door'">
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
          <div class="container_door_content">
            <div class="left_sec">
                <h2>Les portes</h2>
                <div class="wrapper_doors">
                  <ul>
                      <li v-for="(door, index) in doors" :key="index"><img src="/public/assets/porte-ouverte.png" alt="" class="icon_door"> {{ door.name }}</li>
                  </ul>
                </div>
            </div>  
            <hr class="separator_door">
            <div class="right_sec" v-if="selectedDoorEdit == 0">
              <div class="wrapper_text">
                <h3>Vous souhaitez supprimer une porte ? </h3>
                <p>Cette action est définitive ! </p>
              </div>
              <div class="wrapper_form">
                <UForm :schema="schema" :state="state" class="space-y-6" @submit="onSubmit" >
                <UFormGroup label="Porte" name="door">
                  <UInput v-model="state.door" />
                </UFormGroup>
                <UButton type="submit" color="red" class="button_3_function">
                  Supprimer
                </UButton>
              </UForm>
              </div>
            </div>

            <!-- CREATE DOOR -->

            <div class="right_sec" v-if="selectedDoorEdit == 1">
              <div class="wrapper_text">
                <h3>Vous souhaitez créer une porte ? </h3>
                <p> Votre traçabilité sera améliorée ! </p>
              </div>
              <div class="wrapper_form">
                <UForm :schema="schema" :state="state" class="space-y-6" @submit="onSubmit_create" >
                <UFormGroup label="Porte" name="door">
                  <UInput v-model="state.door" />
                </UFormGroup>
                <UFormGroup label="Nombre" name="order">
                  <UInput v-model.number="state.order" />
                </UFormGroup>
                <UButton type="submit" class="button_3_function">
                  Créer
                </UButton>
              </UForm>
              </div>
            </div>

             <!-- MODIFY DOOR -->

             <div class="right_sec" v-if="selectedDoorEdit == 2">
              <div class="wrapper_text">
                <h3>Vous souhaitez modifier une porte ? </h3>
                <p> Donner un nom parlant à votre porte ! </p>
              </div>
              <div class="wrapper_form">
                <UForm :schema="schema_edit" :state="state_edit" class="space-y-6" @submit="onSubmit_modify" >
                <UFormGroup label="Nom de la porte à modifier" name="door_current">
                  <UInput v-model="state_edit.door_current" />
                </UFormGroup>

                <UFormGroup label= "Nouveau nom de la porte" name="door_edit">
                  <UInput v-model="state_edit.door_edit" />
                </UFormGroup>
                <UButton type="submit" color="orange" class="button_3_function">
                  Modifier
                </UButton>
              </UForm>
              </div>
            </div>

          </div>
        </div>
      </div>
    </div>
  </div>
  <UNotification
        v-if="notificationVisible"
        icon="i-heroicons-check-badge"
        :color="notificationColor"
        :id="6"
        :title="notificationTitle"
        :description="notificationMessage"
        :timeout="6000"
        class="custom-notification"
        
        />
</template>

<script setup lang="ts">
import { ref, nextTick, onMounted, type Ref } from "vue";
import NavbarVertical from "~/components/navbar_vertical.vue";
import { callAPIServices } from "~/services/callAPIServices";
import { number, z } from 'zod'
import type { FormSubmitEvent } from '#ui/types'

const api = new callAPIServices();


definePageMeta({
  middleware: "auth",
  role: ["ADMINISTRATEUR"],
});

const schema = z.object({
  door: z.string(),
  order: z.number(),
})

const schema_edit = z.object({
  door_current: z.string(),
  door_edit: z.string(),
})

type Schema = z.output<typeof schema>

type Schema_edit = z.output<typeof schema_edit>

const state = reactive({
  door: undefined,
  order: undefined,
})

const state_edit = reactive({
  door_current: undefined,
  door_edit: undefined,
})

const doorToDelete = ref();
const doorToCreate = ref();
const orderToCreate = ref(0);


const willDeleted = ref();
const doorToGet = ref();
const doorEdit= ref();

const notificationVisible = ref(false);
const notificationTitle = ref("");
const notificationMessage = ref("");
const notificationColor =  ref("");


async function onSubmit(event: FormSubmitEvent<Schema>) {
  doorToDelete.value = event.data.door;
  console.log("Saisie de l'utilisateur :", event.data.door);

  if (!doors.value || doors.value.length === 0) {
    console.error("Aucune porte disponible !");
    return;
  }

  for (let i = 0; i < doors.value.length; i++) {
    if (doors.value[i].name === `${event.data.door}`) {
      console.log("ID trouvé :", doors.value[i].id);
      willDeleted.value = doors.value[i].id; 
    }
  }
  await api.fetchAPIDelete("door",willDeleted.value)
  await getDoors();

  notificationColor.value = "red";
  notificationTitle.value = "Suppression de porte";
  notificationMessage.value = "Vous venez de supprimer une de vos portes !"; 
  notificationVisible.value = true;

  setTimeout(() => {
      notificationVisible.value = false;
    }, 6000);
}


const getRequestBodyPost = () => ({
  name: `porte ${doorToCreate.value}`,
  order: orderToCreate.value,
});

async function onSubmit_create(event: FormSubmitEvent<Schema>) {
  doorToCreate.value = event.data.door.toLowerCase();
  orderToCreate.value = event.data.order;
  console.log("creation", getRequestBodyPost())
  await api.fetchAPIPost("door",getRequestBodyPost())
    
  const response = await getDoors();
  console.log("Response", response)
  notificationColor.value = "primary";
  notificationTitle.value = "Création de porte"; 
  notificationMessage.value = "Vous venez de créer une nouvelle porte !"; 
  notificationVisible.value = true;



    setTimeout(() => {
      notificationVisible.value = false;
    }, 6000);
}

const getRequestBodyPUT = () => ({
  name: `porte ${doorEdit.value}` 
});

const id_found_edit = ref();
async function onSubmit_modify(event: FormSubmitEvent<Schema_edit>) {
  doorToGet.value = event.data.door_current.toLowerCase();
  doorEdit.value = event.data.door_edit.toLowerCase();

  for (let i = 0; i < doors.value.length; i++) {
    if (doors.value[i].name === `${doorToGet.value}`) {
      console.log("ID trouvé :", doors.value[i].id);
      id_found_edit.value = doors.value[i].id; 
    }
  }
  const response = await api.fetchAPIPutWithId("door", id_found_edit.value, getRequestBodyPUT());

  await getDoors();
  notificationColor.value = "orange";
  notificationTitle.value = "Modification de porte"; 
  notificationMessage.value = "Vous venez de modifier le nom d'une porte !"; 
  notificationVisible.value = true;

  setTimeout(() => {
      notificationVisible.value = false;
    }, 6000);
}



const items = {
  gestion: "Gestions du potager",
  door: "Portes"
};
const selectedItem = ref('gestion');


const doors_edit = ["Supprimer", "Créer", "Modifier"];
const selectedDoorEdit = ref(0);

const backgroundX = ref(0);
const backgroundWidth = ref(0);
const navItems = ref([]) as Ref<HTMLLIElement[]>;
const doors = ref()

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



async function getDoors() {
  try {
    const response_get_doors = await api.fetchAPIGet("door");
    doors.value = response_get_doors.map(door => ({
      ...door,
      name: door.name.replace(/porte/, "").trim() // Remplace "porte" par rien et enlève les espaces inutiles
    }));
    console.log("doors", doors.value)
  } catch (error) {
    console.error("Erreur lors de la récupération des portes :", error);
  }
}

onMounted(async () => {
  await getDoors();
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
  padding: 0.7rem 1rem;
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
  width: 100%;
  height: 100%;
  gap: 2rem;
}

.right_section_doors{
  display: flex;
  flex-direction: column;
  flex: 6;
  width: 100%;
  height: 100%;
  gap: 2rem;

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
  margin-bottom: 2rem;
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
  font-size: clamp(0.7rem, 1.3vw, 1rem);
  position: relative;
  cursor: pointer;
  z-index: 1;
}

.nav_changement ul li.selected {
  color: black;
}


/* SECTION DELETE */

.container_door_content {
  display: flex;
  margin: auto;
  gap: 1rem;
  width: 100%;
  max-width: 1000px;
  padding: 1rem;
  border-radius: 2rem;
  background-color: #F7F6F9;
  box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.1);
}


.left_sec {
  flex: 1;
  min-width: 250px;
  padding: 1rem;
}

 .left_sec h2{
  font-family: "Aeonik-Regular";
  color: black;
  font-size: clamp(1rem, 1.3vw, 1.2rem);
  padding: 1.5rem 2rem;
 }

 .right_sec {
  flex: 2;
  min-width: 300px;
  padding: 1rem;
}

 .wrapper_text{
  padding-bottom: 2rem;
  text-align: center;
 }

 .wrapper_text h3{
  font-family: "Aeonik-Regular";
  color: black;
  font-size: clamp(1rem, 1.3vw, 1.2rem);
 }
 .wrapper_text p{
  font-family: "Aeonik-Medium";
  color: black;
  font-size: clamp(0.7rem, 1.3vw, 1rem);
 }

 .wrapper_form{
  padding-right: 5rem;
  padding-left: 1rem;
 }


 .wrapper_doors {
  flex: 1;
  width: 100%;
  max-height: 300px; 
  overflow-y: auto;  
  border-radius: 1rem;
  margin-left: 0.5rem;
  padding: 1rem;
  display: flex;
  flex-direction: column;
}

.wrapper_doors ul {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  padding: 0rem 0.5rem;
  list-style: none;
  gap: 1rem;
}

.wrapper_doors ul li {
  display: flex;
  align-items: center; 
  padding: 0.5rem;
  gap: 1rem;
  font-family: "Aeonik-Regular";
  color: black;
  font-size: clamp(0.9rem, 1.3vw, 1rem);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.icon_door{
  width: 30px;
  height: 30px;
 
}

.separator_door{
  width: 2px; 
  height: auto; 
  flex-shrink: 0; 
  background-color: #99989D;
}


.custom-notification {
    position: fixed;
    bottom: 20px; 
    right: 20px; 
    z-index: 1000; 
    width: min(90vw, 30rem);
    max-width: 30rem; 
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
  .right_section_doors{
    padding: 1rem;
    gap: 1rem;
  }


  .header h3{
    padding-top: 2rem;
    padding-left: 0;
  }

  .container_door_content {
    flex-direction: column;
    align-items: center;
  }
  .left_sec, .right_sec {
    width: 100%;
  }

  .wrapper_doors ul {
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  padding: 0;
  gap: 0;

}

.left_sec{
  padding: 0;
}

.left_sec h2{
  text-align: center;
}

.left_sec p{
  text-align: left;
}



.wrapper_form {
    width: 100%; 
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 1rem;
  }

  .wrapper_form UForm {
    width: 100%;
  }

  .nav_changement ul li {
  padding: 0.3rem 1.2rem;
}

}

</style>