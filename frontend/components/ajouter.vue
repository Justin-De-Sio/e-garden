<template>
  <div class="modal-overlay" @click.self="$emit('close')">
    <div class="modal-content">
      <h2>Ajouter Utilisateur</h2>
      <UForm :schema="formSchema" :state="formState" class="space-y-3" @submit="submitForm">
        <UFormGroup label="Prénom" name="name">
          <UInput v-model="formState.name" class="Uinput_custom" color="gray"/>
        </UFormGroup>

        <UFormGroup label="Nom" name="surname">
          <UInput v-model="formState.surname" type="text" class="Uinput_custom" color="gray"/>
        </UFormGroup>

        <UFormGroup label="Email" name="email">
          <UInput v-model="formState.email" type="email" class="Uinput_custom" color="gray"/>
        </UFormGroup>

        <UFormGroup label="Année scolaire" name="className">
          <UInput v-model="formState.className" type="text" class="Uinput_custom" color="gray"/>
        </UFormGroup>

        <UFormGroup label="Groupe classe" name="groupNumber">
          <UInput v-model="formState.groupNumber" type="number" class="Uinput_custom" color="gray"/>
        </UFormGroup>
        <div v-if="errorMessage" class="text-red-500 text-sm font-medium text-center mt-2">
          {{ errorMessage }}
        </div>
        <UButton type="submit" class="Ubutton_custom">
          Ajouter le membre
        </UButton>
      </UForm>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onBeforeUnmount } from 'vue';
import { z } from "zod";
import { callAPI } from '~/services/callAPI';

const formState = reactive({
  name: '',
  surname: '',
  email: '',
  className: '',
  groupNumber: null,
});

const formSchema = z.object({
  name: z.string(),
  surname: z.string(),
  email: z.string().email("Must be a valid email"),
  className: z.string(),
  groupNumber: z.number().int(),
});

type Schema = z.output<typeof formSchema>;
const errorMessage = ref('');

const resetForm = () => {
  formState.name = '';
  formState.surname = '';
  formState.email = '';
  formState.className = '';
  formState.groupNumber = null;
};

const props = defineProps<{ userId: number, requetUser: () => void }>();
const emit = defineEmits(['close']);

const api = new callAPI();


const submitForm = async () => {
  try {
    const validData = formSchema.parse(formState);
    await api.fetchAPIPost(`user`,validData);
    emit('close');
    props.requetUser();
  } catch (error) {
    console.error('Erreur lors de la mise à jour des données :', error);
  }
};



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
  width: 400px;
  height: 500px;
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

u-button {
  background-color: #f44336;
  color: white;
}
</style>