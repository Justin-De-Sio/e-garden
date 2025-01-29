<template>
  <div class="modal-overlay" @click.self="$emit('close')">
    <div class="modal-content">
      <h2>Ajouter un utilisateur</h2>
      <UForm :schema="formSchema" :state="formState" class="space-y-3" @submit="submitForm">
        <UFormGroup label="Prénom" name="name">
          <UInput v-model="formState.name" class="Uinput_custom" color="gray"/>
        </UFormGroup>
        <UFormGroup label="Nom" name="surname">
          <UInput v-model="formState.surname" type="text" class="Uinput_custom" color="gray"/>
        </UFormGroup>
        <UFormGroup label="Email" name="email">
          <UInput v-model="formState.email" type="email" class="Uinput_custom" placeholder="email@edu.ece.fr" color="gray"/>
        </UFormGroup>
        <UFormGroup label="Année scolaire" name="className">
          <UInput v-model="formState.className" type="text" class="Uinput_custom" placeholder="ING5 APP" color="gray"/>
        </UFormGroup>
        <UFormGroup label="Groupe classe" name="groupNumber">
          <UInput v-model="formState.groupNumber" type="number" placeholder="3" class="Uinput_custom" color="gray"/>
        </UFormGroup>
        <div v-if="errorMessage" class="text-red-500 text-sm font-medium text-center mt-2">
          {{ errorMessage }}
        </div>
        <UFormGroup label="Rôle" name="role">
          <USelect v-model="formState.role" placeholder="UTILISATEUR" :options="roles"/>
        </UFormGroup>
        <UFormGroup>
          <UButton type="primary" native-type="submit">Ajouter l'utilisateur</UButton>
          <UButton class="cancel-button" @click="handleCancel">Annuler</UButton>
        </UFormGroup>
      </UForm>
    </div>
  </div>
</template>

<script setup lang="ts">
import {reactive, ref} from 'vue';
import {z} from "zod";
import {callAPI} from '~/services/callAPI';
import type {Roles} from '~/model/Roles.ts'

const props = defineProps<{ requetUser: () => void }>();
const emit = defineEmits(['close']);
const roles = ref<Roles>();

const formState = reactive({
  name: '',
  surname: '',
  email: '',
  className: '',
  role: '',
  groupNumber: null,
});

const formSchema = z.object({
  name: z.string().min(2, "Le prénom est obligatoire").max(255, "Ne peut dépasser 255 caractères"),
  surname: z.string().min(2, "Le nom est obligatoire").max(255, "Ne peut dépasser 255 caractères"),
  email: z.string().email("L'email est invalide"),
  className: z.string().max(20, "Ne peut dépasser 20 caractères"),
  groupNumber: z.number().int().positive("Le groupe doit être un nombre positif"),
  role: z.string().min(3, "La classe est obligatoire"),
});

const errorMessage = ref('');
const api = new callAPI();

async function getRoles() {
  try {
    roles.value = await api.fetchAPIGet("user/roles") as Roles;

  } catch (error) {
    console.error('Erreur lors de la récupération des données :', error);
  }
}

const submitForm = async () => {
  try {
    const validData = formSchema.parse(formState);
    await api.fetchAPIPost(`user`, validData);

    emit('close');
    props.requetUser();
  } catch (error) {
    console.error('Erreur lors de la mise à jour des données :', error);
  }
};

const handleCancel = () => {
  props.requetUser();
  emit('close');
};

onMounted(getRoles);

</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 2rem;
  border-radius: 10px;
  width: auto;
  height: auto;
}

u-form {
  display: flex;
  flex-direction: column;
}

u-form-item {
  margin-bottom: 1rem;
}

u-input {
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 5px;
}

u-button {
  margin-right: 0.5rem;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

u-button[type="primary"] {
  background-color: #4CAF50;
  color: white;
}

.cancel-button {
  background-color: #f44336;
  color: white;
  margin-left: 0.5rem;
}

h2 {
  font-family: "Gilroy-Medium", sans-serif;
  margin-bottom: 2vh;
}
</style>
