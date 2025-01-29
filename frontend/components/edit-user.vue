<template>
  <div class="modal-overlay" @click.self="$emit('close')">
    <div class="modal-content">
      <h2>Modifier un utilisateur</h2>
      <u-form :model="user" @submit="submitForm" v-if="user">
        <UFormGroup label="Prénom" name="name">
          <u-input v-model="user.name"/>
        </UFormGroup>
        <UFormGroup label="Nom" name="surname">
          <u-input v-model="user.surname"/>
        </UFormGroup>
        <UFormGroup label="Email" name="email">
          <u-input v-model="user.email"/>
        </UFormGroup>
        <UFormGroup label="Année scolaire" name="className">
          <u-input v-model="user.className" placeholder="ING5 APP"/>
        </UFormGroup>
        <UFormGroup label="Groupe classe" name="groupNumber">
          <u-input placeholder="3" v-model="user.groupNumber"/>
        </UFormGroup>
        <UFormGroup label="Rôle" name="role">
          <USelect v-model="user.role" :options="roles"/>
        </UFormGroup>
        <UFormGroup>
          <u-button type="primary" native-type="submit">Enregistrer</u-button>
          <u-button class="cancel-button" @click="handleCancel">Annuler</u-button>
        </UFormGroup>
      </u-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref, watch} from 'vue';
import {callAPI} from '~/services/callAPI';
import type {User} from '~/model/User.js'
import type {Roles} from '~/model/Roles.ts'

const props = defineProps<{ userId: number, requetUser: () => void }>();
const emit = defineEmits(['close']);

const api = new callAPI();
const user = ref<User>();
const roles = ref<Roles>();

const fetchUser = async (id: number) => {
  try {
    console.log(id);
    user.value = await api.fetchAPIGet(`user/` + id) as User;
    roles.value = await api.fetchAPIGet("user/roles") as Roles;

  } catch (error) {
    console.error('Erreur lors de la récupération des données :', error);
  }
};

watch(() => props.userId, (newId) => {
  if (newId) {
    fetchUser(newId);
  }
});

const submitForm = async () => {
  try {
    await api.fetchAPIPut(`/user/${props.userId}`, user.value);
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

onMounted(() => {
  if (props.userId) {
    fetchUser(props.userId);
  }
});
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

u-button {
  background-color: #f44336;
  color: white;
}

h2 {
  font-family: "Gilroy-Medium", sans-serif;
  margin-bottom: 2vh;
}

.cancel-button {
  background-color: #f44336;
  color: white;
  margin-left: 0.5rem;
}
</style>