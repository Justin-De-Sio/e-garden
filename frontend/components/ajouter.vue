<template>
  <div @click="handleClickOutside">
    <button @click.stop="showForm = !showForm" class="add-button">Ajouter</button>
    <div v-if="showForm" class="form-container" ref="formContainer">
      <form @submit.prevent="addMember">
        <div>
          <label for="name">Nom :</label>
          <input type="text" id="name" v-model="newMember.name" required />
        </div>
        <div>
          <label for="surname">Prénom :</label>
          <input type="text" id="surname" v-model="newMember.surname" required />
        </div>
        <div>
          <label for="email">Email :</label>
          <input type="email" id="email" v-model="newMember.email" required />
        </div>
        <div>
          <label for="role">Rôle :</label>
          <input type="text" id="role" v-model="newMember.role" required />
        </div>
        <div>
          <label for="className">Classe :</label>
          <input type="text" id="className" v-model="newMember.className" required />
        </div>
        <div>
          <label for="groupNumber">Groupe :</label>
          <input type="text" id="groupNumber" v-model="newMember.groupNumber" required />
        </div>
        <button @click="addMember">Ajouter le membre</button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import {fetchBackendPost} from '~/services/call_backend.js';

const showForm = ref(false);
const newMember = ref({
  name: '',
  surname: '',
  email: '',
  className: '',
  groupNumber: '',
});
const formContainer = ref(null);

const addMember = async () => {
  try {
    const response = await fetchBackendPost("/api/user",newMember)

    const data = await response.json();
    console.log('Membre ajouté avec succès :', data);
    // Réinitialisez le formulaire après un ajout réussi
    newMember.value = {
      name: '',
      surname: '',
      email: '',
      role: '',
      className: '',
      groupNumber: null,
    };
    showForm.value = false;
  } catch (error) {
    console.error('Erreur lors de l’ajout du membre :', error);
  }
};

const handleClickOutside = (event) => {
  if (formContainer.value && !formContainer.value.contains(event.target)) {
    showForm.value = false;
  }
};

onMounted(() => {
  document.addEventListener('click', handleClickOutside);
});

onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside);
});
</script>

<style scoped>
.add-button {
  background-color: #95BD75;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  z-index:999;
}

.form-container {
  margin-top: 20px;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 15px;
  background-color: #f9f9f9;
  width: 411px;
  z-index:1;

}

form div {
  margin-bottom: 10px;
  z-index:1;
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