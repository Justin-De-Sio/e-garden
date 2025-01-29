<template>
  <div class="main_part">    
    <div class="left_part">
      <img src="~/public/assets/login-illustration.png" alt="Image d'un potager" class="responsive-img">
    </div>
    <div class="right_part">
      <div class="pos_white_card">
        <div class="logo_title_part">
          <div class="logo_pos">
            <img class="logo" src="~/public/assets/logo.png" alt="Logo">
          </div>
          <div class="title_part">
            <h1>Bienvenue !</h1>
            <h6>Entrez vos informations, s'il vous plaît.</h6>
          </div>
        </div>
        <UForm :schema="formSchema" :state="formState" class="space-y-3" @submit="onSubmit">
          <UFormGroup  label="Email"  name="email" 
              :ui="{
                label: { 
                  base: 'text-black dark:text-white',
                }
              }"
            >
              <UInput  v-model="formState.email"  class="!bg-white rounded-md has-[:invalid]:ring-red-500" color="gray"/>
            </UFormGroup>

          <UFormGroup label="Password" name="password" :ui="{ label: { base: 'text-black dark:text-white' } }">
            <UInput v-model="formState.password" type="password" class="!bg-white rounded-md" color="gray" required/>
          </UFormGroup> 
          <div v-if="errorMessage" class="text-red-500 text-sm font-medium text-center mt-2">
            {{ errorMessage }}
          </div>
          <UButton :loading="isLoading" type="submit" class="Ubutton_custom">
            Connexion
          </UButton>
        </UForm>
      </div>
    </div>
  </div>  
</template>

<script setup lang="ts">
import { z } from 'zod'
import type {FormSubmitEvent} from "#ui/types";
import { login } from '~/services/auth';

const isLoading = ref(false);
const router = useRouter();
const formState = reactive({
  email: undefined,
  password: undefined,
});

const formSchema = z.object({
  email: z.string().email("Doit avoir la bonne syntaxe"),
  password: z.string().min(5, "Minimum 5 caractères"),
});

type Schema = z.output<typeof formSchema>;
const errorMessage = ref('');

async function onSubmit(event: FormSubmitEvent<Schema>) {
  isLoading.value = true;
  event.preventDefault?.();

  const { email, password } = formState;

  try {
    await login(email, password);
    await router.push('/security');
  } catch (error) {
    isLoading.value = false;
    if (error instanceof Error) {
      errorMessage.value = error.message;
    } else {
      errorMessage.value = "Une erreur inattendue s'est produite.";
    }

  }
}

definePageMeta({
    middleware: "already-log",
  })

</script>


<style scoped>

.main_part {
  display: flex;
  background-color: #F2F4EF;
  width: 100vw;
  height: 100vh;
}

.left_part {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
}

.responsive-img {
  width: 60%;
  height: auto;
}

.right_part {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 80%;
  height: 100%;
  background-color: #F2F4EF;
}

.pos_white_card {
  border-radius: 10px;
  background-color: white;
  width: 90%;
  height: 95%;
  display: flex;
  flex-direction: column;
  padding: 10%;
  align-items: center;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

.logo_title_part{
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.logo {
  display: flex;
  margin: auto;
  align-content: center;
  margin-bottom: 1em;
  width: 115px;
  height: auto;
}

.title_part h1 {
  font-family: "Gilroy-Bold", sans-serif;
  color: #151B11;
  font-size: clamp(1.8rem, 2.5vw, 4rem); 
  padding-left: 25px;
  text-align: center;
}

.title_part h6 {
  font-family: "Gilroy-Regular", sans-serif;
  color: #7B7B7B;
  font-size: clamp(1rem, 1.5vw, 1rem);
  margin-left: 18px;
  margin-top: 5px;
  text-align: center;
}

form {
  margin-top: 20%;
  display: flex; 
  flex-direction: column; 
  gap: 1rem; 
  width: 90%;
}

.password-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.Ubutton_custom:hover{
  transform: scale(1.03);
  background-color: #95bd75;
  color: white;
}
.Ubutton_custom{
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 100px;
  border-radius: 30px;
  background-color: black;
  color: white;
  height: 40px;
  transition: transform 0.3s ease;
  transform-origin: center ;
}
@media screen and (max-width: 800px) {
    .left_part {
      display: none;
    }

    .right_part{
      width: 100%;
    }
}
</style>

