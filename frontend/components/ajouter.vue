<template>
  <div>
    <button class="add-button" @click.stop="showForm = true">Ajouter un utilisateur</button>
    <div v-if="showForm" class="form-overlay">
      <div class="form-container" @click.stop>
        <UForm :schema="formSchema" :state="formState" class="space-y-3" @submit="onSubmit">
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
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onBeforeUnmount } from 'vue';
import { fetchBackendPost } from '~/services/call_backend.js';
import { z } from "zod";
import type { FormSubmitEvent } from "#ui/types";


const showForm = ref(false);

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

const handleClickOutside = (event: MouseEvent) => {
  const formContainer = document.querySelector('.form-container');
  if (formContainer && !formContainer.contains(event.target as Node)) {
    showForm.value = false;
    resetForm();
  }
};

onMounted(() => {
  document.addEventListener('click', handleClickOutside);
});

onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside);
});

async function onSubmit(event: FormSubmitEvent<Schema>) {
  event.preventDefault?.();
  try {
    // Validation avec Zod
    const validData = formSchema.parse(formState);

    // Envoi de la requête POST
    const response = await fetchBackendPost('/api/user', validData);

    console.log('Response:', response);
    showForm.value = false;
    resetForm();
  } catch (error) {
    if (error instanceof z.ZodError) {
      errorMessage.value = error.errors.map(err => err.message).join(', ');
    } else {
      console.error('Submission failed:', error);
      errorMessage.value = 'Failed to submit the form.';
    }
  }

  console.log(event.data);
}
</script>

<style scoped>
.add-button {
  background-color: #95BD75;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  z-index: 999;
}

.form-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.form-container {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 15px;
  background-color: #f9f9f9;
  width: 411px;
  z-index: 1001;
}

form div {
  margin-bottom: 10px;
}

form label {
  display: block;
  margin-bottom: 5px;
  width: auto;
}

form input {
  width: 100%;
  padding: 8px;
}

form button {
  background-color: #95BD75;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>