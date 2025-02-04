<template>
  <div class="main_wrapper">
    <div class="header_log">
      <div class="logo">
        <img class="profil_logo" src="/assets/logo.png" alt="">
      </div>
      <div class="exit">
        <button class="button_exit" @click="goBack">
          <img class="exit_profil" src="/assets/cancel.png" alt="" width="20px" height="auto"/>
        </button>
      </div>
    </div>
    <div class="wrapper_profil">

      <div class="wrapper_form">
        <div class="text_header">
          <h2>Changer votre mot de passe</h2>
          <h3>Veuillez remplir les champs ci-dessous</h3>
        </div>
        <div class="header_profil">
          <h3>Votre photo de profil</h3>
          <img src="public/assets/user.png" alt="">
        </div>
        <div class="formulaire">
          <UForm :schema="formSchema" :state="formState" class="space-y-3" @submit="onSubmit">
            <UFormGroup label="username" name="username" hidden="hidden">
              <UInput v-model="username" autocomplete="username" type="text" hidden="hidden"/>
            </UFormGroup>
            <UFormGroup label="Mot de passe actuel" name="currentpassword">
              <UInput v-model="formState.currentPassword" autocomplete="current-password" type="password" class="!bg-white rounded-md" color="gray"/>
            </UFormGroup>
            <UFormGroup label="Nouveau mot de passe" name="newpassword">
              <UInput v-model="formState.newPassword" autocomplete="new-password" type="password" class="!bg-white rounded-md" color="gray"/>
            </UFormGroup>
            <UFormGroup label="Confirmation de mot de passe" name="confirmPassword">
              <UInput v-model="formState.confirmPassword" autocomplete="new-password" type="password" class="!bg-white rounded-md" color="gray"/>
            </UFormGroup>
            <div v-if="errorMessage" class="text-red-500 text-sm font-medium text-center mt-2">
              {{ errorMessage }}
            </div>
            <UButton :loading="isLoading" type="submit" class="align-center">
              Enregistrer
            </UButton>
          </UForm>

          <!--
          <form @submit.prevent="submitForm">
              <div v-for="(param, index) in password_params" :key="index">
                  <h4 class="name_input">{{ param.name }}</h4>
                  <input
                      :type="param.type"
                      :name="param.key"
                      v-model="param.value"
                  />
              </div>
              <button class="save_button" type="submit">Enregistrer</button>
          </form>
          -->
        </div>
      </div>
      <div class="illustration">
        <img src="/public/assets/mdp-illustration.png" alt="">
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
import {z} from 'zod';
import {ref} from 'vue';
import {callAPIServices} from "~/services/callAPIServices";
import type {User} from "~/model/User";

definePageMeta({
  middleware: "auth",
})

const api = new callAPIServices();
const errorMessage = ref();
const userId = ref();
const username = ref();

const isLoading = ref(false);
const notificationVisible = ref(false);
const notificationTitle = ref("");
const notificationMessage = ref("");

onMounted(async () => {
  try {
    const data = await api.fetchAPIGet('user/profil') as User;
    userId.value = data.id;
    username.value = data.email;
  } catch (error) {
    console.error('Erreur lors du chargement des données du profil :', error);
  }
});

const formState = reactive({
  currentPassword: "",
  newPassword: "",
  confirmPassword: "",
});

const formSchema = z.object({
  currentPassword: z.string().min(1, "Le mot de passe actuel est requis"),
  newPassword: z.string().min(8, "Le nouveau mot de passe doit contenir au moins 8 caractères"),
  confirmPassword: z.string().min(8).refine(
      (value) => value === formState.newPassword,
      {message: "Les mots de passe ne correspondent pas"}
  ),
});


const onSubmit = async () => {
  isLoading.value = true;
  const requestBody = {
    currentPassword: formState.currentPassword,
    newPassword: formState.newPassword,
    confirmNewPassword: formState.confirmPassword,
  };
  try {
    await api.fetchAPIPostWithId("user/changePassword", userId.value, requestBody);
    notificationVisible.value = true;
    notificationTitle.value = "Succès";
    notificationMessage.value = "Votre mot de passe a bien été modifié";

    setTimeout(() => {
      notificationVisible.value = false;
    }, 6000);
    isLoading.value = false;
  } catch (error) {
    isLoading.value = false;
    errorMessage.value = "Une erreur s'est produite";
  }


};
const router = useRouter();
const goBack = () => {
  router.back();
};
</script>

<style scoped>

body {
  background-color: #f2f4ef
}

.main_wrapper {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 1rem;
  box-sizing: border-box;
}

.wrapper_profil {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: flex-start;
  width: 100%;
  height: auto;
}

.header_log {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: auto;
}


.profil_logo {
  width: min(20vw, 100px);
  height: auto;
}

.exit {
  display: flex;
  gap: 1.3rem;

}

.button_exit {
  background: none;
  border: none;
  cursor: pointer;
}

.exit_profil {
  width: min(3vw, 20px);
  height: auto;
  margin-right: min(5vw, 30px);
  cursor: pointer;
}

.logout {
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

.text_header {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  gap: 0.5rem;
}

.text_header h2 {
  font-family: "Gilroy-Regular";
  font-size: clamp(1.3rem, 2.5vw, 2rem);
}

.text_header h3 {
  font-family: "Gilroy-Regular";
  font-size: clamp(0.8rem, 1.5vw, 1rem);
  color: #909399;
}


.wrapper_form {
  display: flex;
  flex-direction: column;
  margin-left: 10rem;
  margin-top: 3rem;
}

.header_profil {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  margin-top: 3rem;
  gap: 1rem;
}

.header_profil h3 {
  font-family: "Gilroy-Medium";
  font-size: clamp(0.9rem, 2.5vw, 1.3rem);
}

.header_profil img {
  width: 4rem;
}

.formulaire form {
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

.formulaire form input::placeholder {
  font-family: "Gilroy-Medium";
  color: black;
}

.footer_button {
  margin: auto;
  margin-top: 4rem;
}

.footer_button p {
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

.save_button {
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

.illustration {
  margin: auto;
}

.illustration img {
  width: min(25vw, 600px);
}

.custom-notification {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 1000;
  width: min(50vw, 30rem);
  max-width: 30rem;
}

.align-center {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}

@media screen and (max-width: 900px) {

  .wrapper_form {
    margin: auto;
    margin-top: 2.5em;
    justify-content: center;
    align-items: center;
  }

  .text_header {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .header_profil {
    align-items: center;
  }

  .illustration {
    display: none;
  }

}

</style>