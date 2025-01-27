<template>
    <div class="main_wrapper">
      <div class="header_log">
        <div class="logo">
          <img class="profil_logo" src="/assets/logo.png" alt="" />
        </div>
        <div class="exit">
          <button class="logout" @click="logout">Deconnexion</button>
          <button class="button_exit" @click="goBack">
            <img
              class="exit_profil"
              src="/assets/cancel.png"
              alt=""
              width="20px"
              height="auto"
            />
          </button>
        </div>
      </div>
      <div class="wrapper_profil">
        <div class="wrapper_form">
          <div class="text_header">
            <h2>Mon profil</h2>
            <h3>Gérez les paramètres de votre profil</h3>
          </div>
          <div class="header_profil">
            <h3>Votre photo de profil</h3>
            <img src="public/assets/user.png" alt="" />
          </div>
          <div class="formulaire">
            <UForm :schema="formSchema" :state="formState" class="space-y-3" @submit="onSubmit">
              <UFormGroup label="Nom" name="surname">
                <UInput v-model="formState.surname" class="!bg-white rounded-md" color="gray" />
              </UFormGroup>
              <UFormGroup label="Prénom" name="name">
                <UInput v-model="formState.name" class="!bg-white rounded-md" color="gray" />
              </UFormGroup>
              <UFormGroup label="Email" name="email">
                <UInput v-model="formState.email" class="!bg-white rounded-md" color="gray" readonly />
              </UFormGroup>
              <UFormGroup label="Classe" name="class">
                <UInput v-model="formState.class" type="text" class="!bg-white rounded-md" color="gray" />
              </UFormGroup>
              <UFormGroup label="Groupe" name="group">
                <UInput
                  v-model.number="formState.group"
                  type="number"
                  class="!bg-white rounded-md"
                  color="gray"
                />
              </UFormGroup>
              <div v-if="errorMessage" class="text-red-500 text-sm font-medium text-center mt-2">
                {{ errorMessage }}
              </div>
              <UButton :disabled="!hasChanges" :loading="isLoading" type="submit" class="f">
                Enregistrer
              </UButton>
            </UForm>
          </div>
          <div class="footer_button">
            <p @click="changePassword">Changer de mot de passe</p>
          </div>
        </div>
        <div class="illustration">
          <img src="/public/assets/profil-illustration.png" alt="" />
        </div>
      </div>
    </div>
      <UNotification
        v-if="notificationVisible"
        icon="i-heroicons-check-badge"
        color="primary"
        :id="6"
        :title="notificationTitle"
        :description="notificationMessage"
        :timeout="6000"
        class="custom-notification"
        
        />
  </template>
  
<script setup lang="ts">
import { callAPI } from '~/services/callAPI';
import { ref, onMounted, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { z } from 'zod';
import type { FormSubmitEvent } from "#ui/types";

const api = new callAPI();
const isLoading = ref(false);
const id = ref();
const notificationVisible = ref(false);
const notificationTitle = ref(""); 
const notificationMessage = ref(""); 


const formState = reactive({
  surname: '',
  name: '',
  email: '',
  class: '',
  group: undefined as number | undefined,
});

const originalFormState = reactive({
  surname: '',
  name: '',
  email: '',
  class: '',
  group: undefined as number | undefined,
});

const formSchema = z.object({
  surname: z.string().min(1),
  name: z.string().min(1),
  email: z.string().email(),
  class: z.string().min(1),
  group: z.number().nullable(),
});

interface UserProfileResponse {
  id: number;
  surname: string;
  name: string;
  email: string;
  className: string;
  groupNumber: number;
}

type Schema = z.output<typeof formSchema>;
const errorMessage = ref('');

async function onSubmit(event: FormSubmitEvent<Schema>) {
  isLoading.value = true;
  event.preventDefault?.();
  const requestBody = {
    id: id.value,
    surname: formState.surname,
    name: formState.name,
    email: formState.email,
    className: formState.class,
    groupNumber: formState.group,
  };

  try {
    const response = await api.fetchAPIPutWithId("user/profil", id.value, requestBody) as UserProfileResponse;
    formState.surname = response.surname || formState.surname;
    formState.name = response.name || formState.name;
    formState.email = response.email || formState.email;
    formState.class = response.className || formState.class;
    formState.group = response.groupNumber || formState.group;

    originalFormState.surname = response.surname || '';
    originalFormState.name = response.name || '';
    originalFormState.email = response.email || '';
    originalFormState.class = response.className || '';
    originalFormState.group = response.groupNumber || undefined;

    isLoading.value = false;

    notificationTitle.value = "Succès"; 
    notificationMessage.value = "Votre profil a été mis à jour avec succès !"; 
    notificationVisible.value = true;



    setTimeout(() => {
      notificationVisible.value = false;
    }, 6000);


  } catch (error) {
    isLoading.value = false;

    notificationTitle.value = "Erreur"; 
    notificationMessage.value = "Une erreur est survenue lors de la mise à jour du profil.";
    notificationVisible.value = true;

    setTimeout(() => {
      notificationVisible.value = false;
    }, 6000);
  }
}

definePageMeta({
  middleware: 'auth', 
  roles: ['UTILISATEUR'], 
});

// Fonction pour récupérer les données utilisateur
const fetchUserData = async () => {
  try {
    const response = await api.fetchAPIGet('user/profil') as UserProfileResponse; 
    id.value = response.id;
    formState.surname = response.surname || '';
    formState.name = response.name || '';
    formState.email = response.email || '';
    formState.class = response.className || '';
    formState.group = response.groupNumber || undefined;

    originalFormState.surname = response.surname || '';
    originalFormState.name = response.name || '';
    originalFormState.email = response.email || '';
    originalFormState.class = response.className || '';
    originalFormState.group = response.groupNumber || undefined;
  } catch (error) {
    console.error("Erreur lors de la récupération des données utilisateur :", error);
  }
};

const hasChanges = computed(() => {
  return (
    formState.surname !== originalFormState.surname ||
    formState.name !== originalFormState.name ||
    formState.email !== originalFormState.email || // Si vous permettez de modifier l'email à l'avenir
    formState.class !== originalFormState.class ||
    formState.group !== originalFormState.group
  );
});

onMounted(() => {
  fetchUserData();
});

// Navigation
const router = useRouter();
const goBack = () => {
  router.back();
};

const logout = () => {
  document.cookie = "session=;";
  router.push("/login");
};

const changePassword = () => {
  router.push("/changePassword");
};

definePageMeta({
    middleware: "auth",
    role: ["ADMINISTRATEUR","UTILISATEUR"],
  })
</script>


<style scoped>

    body{
        background-color: #f2f4ef
    }

    .main_wrapper {
        display: flex;
        flex-direction: column;
        justify-content: space-between; 
        align-items: center; 
        width: 100%; 
        box-sizing: border-box; 
    }

    .wrapper_profil{
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: flex-start;
        width: 100%;
        height: auto;
    }

    .header_log{
        display: flex;
        justify-content: space-between;
        align-items: center;
        width: 100%;
        height: auto;
    }


    .profil_logo{
        width: min(20vw, 100px); 
        height: auto;
    }
    
    .exit{
        display: flex;
        gap: 1.3rem;

    }

    .button_exit{
        background: none;
        border: none;
        cursor: pointer;
    }

    .exit_profil{
        width: min(3vw, 20px); 
        height: auto;
        margin-right: min(5vw, 30px); 
        cursor: pointer;
    }

    .logout{
        border: none;
        color: white;
        height: 2.5rem;
        width: min(25vw, 150px); 
        border-radius: 0.3rem;
        background-color: #ffbf69;
        font-family: "Gilroy-Medium";
        font-size: clamp(0.7rem, 2vw, 0.9rem); 
        cursor: pointer;
    }

    /* =========================
                BODY 
    ===========================*/

    .text_header{
        display: flex;
        flex-direction: column;
        justify-content: flex-start; 
        gap: 0.5rem;   
    }

    .text_header h2{
        font-family: "Gilroy-Regular";
        font-size: clamp(1.3rem, 2.5vw, 2rem); 
    }

    .text_header h3{
        font-family: "Gilroy-Regular";
        font-size: clamp(0.8rem, 1.5vw, 1rem); 
        color: #909399;
    }

    
    .wrapper_form{
        display: flex;
        flex-direction: column;
        margin-left: 10rem;
        margin-top: 3rem;
    }

    .header_profil{
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        justify-content: center;
        margin-top: 3rem;
        gap: 1rem;
    }

    .header_profil h3{
        font-family: "Gilroy-Medium";
        font-size: clamp(0.9rem, 2.5vw, 1.3rem);
    }

    .header_profil img{
        width: 4rem;
    }

    .formulaire form{
        margin-top: 2rem;
        display: flex;
        flex-direction: column;
        gap: 1rem;
        width: 100%;
    }

    .formulaire form input {
        width: 100%;
        height: 2.2rem;
        padding-left: 1rem;
        border: solid 2px #EEEEEE
    
    }

    .name_input {
        font-family: "Gilroy-Regular";
        font-size: 0.9rem;
        color: #909399;
    
    }

    .formulaire form input::placeholder {
        font-family: "Gilroy-Medium";
        color: black;
    }

    .footer_button{
        margin: auto;
        margin-top: 4rem;
    }

    .footer_button p{
        background: none;
        border: none;
        width: min(35vw, 200px); 
        height: 2.5rem;
        border-radius: 0.3rem;
        color: black;
        font-family: "Gilroy-Regular";
        font-size: clamp(0.7rem, 2vw, 0.9rem); 
        cursor: pointer;
        text-decoration: underline;
        font-style: italic;
    }

    .save_button{
        background: none;
        border: none;
        background-color: #95bd75;
        color: white;
        width: min(25vw, 200px);
        font-family: "Gilroy-Medium";
        font-size: clamp(0.7rem, 2vw, 0.8rem); 
        border-radius: 0.2rem;
        height: 2rem;
        margin: auto;
        cursor: pointer;
    }

    .save_button:disabled {
        background-color: #cccccc; 
        color: #666666;           
        cursor: not-allowed;      
        opacity: 0.6;             
    }

    .illustration{
        margin: auto;
    }

    .illustration img{
        width: min(40vw, 900px);
    }

    .align-center {
        display: flex; 
        justify-content: center; 
        align-items: center; 
        text-align: center; 
        }

    .custom-notification {
    position: fixed;
    bottom: 20px; 
    right: 20px; 
    z-index: 1000; 
    width: min(50vw, 30rem);
    max-width: 30rem; 
    }


    @media screen and (max-width: 800px) {

        .wrapper_form{
            margin: auto;
            margin-top: 2.5em;
            justify-content: center;
            align-items: center;
        }

        .text_header{
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;    
        }

        .header_profil{
            align-items: center;
        }


        .illustration{
            display: none;
        }

  }

</style>