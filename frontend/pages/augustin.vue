<template>
  <UTable v-model="selected" :rows="people" :columns="columns">
    <template #name-data="{ row }">
      <span :class="[selected.find(person => person.id === row.id) && 'text-primary-500 dark:text-primary-400']">{{ row.name }}</span>
    </template>

    <template #actions-data="{ row }">
      <UDropdown :items="items(row)">
        <UButton color="gray" variant="ghost" icon="i-heroicons-ellipsis-horizontal-20-solid" />
      </UDropdown>
    </template>
  </UTable>
</template>

<script setup lang="ts">

import { ref, computed, onMounted } from 'vue';
import { callAPI } from '~/services/callAPI';
import modifUser from '~/components/modifUser.vue';

const api = new callAPI();


onMounted(async () => {
  requetUser();
});

let columns: { key: keyof Person; label: string }[];
columns = [
  {key: 'surname', label: 'Nom'},
  {key: 'name', label: 'Prénom'},
  {key: 'email', label: 'Email'},
  {key: 'className', label: 'Classe'},
  {key: 'groupNumber', label: 'Groupe'},
  {key: 'role', label: 'Rôle'},
  {key: 'actions', label: 'Actions'}];

interface Person {
  id: number,
  name: string,
  surname: string,
  email: string,
  role: string,
  className: string,
  groupNumber: number,
  locked: boolean
}

const people = ref<Person[]>([]);
const selected = ref<Person[]>([]);

async function requetUser(){
  try {
    console.log("requetUser");
    const response = await api.fetchAPIGet('/user/all') as Person;
    people.value = response;
    console.log("---------------",response);
  } catch (error) {
    console.error('Erreur lors de la récupération des données :', error);
  }
}

const items = row => [
  [{
    label: 'Modifier',
    icon: '~/public/assets/stylo.png',
    click: () => modifierUtilisateur(row.id)
  }, {
    label: 'Supprimer',
    icon: '~/public/assets/croix.png',
    click: () => supprimerUtilisateur(row.id)
  }, {
    label: 'Verouiller',
    icon: '🔼',
    click: () => verouillerUtilisateur(row.id)
  }, {
    label: 'Réinitialiser le mot de passe',
    icon: '🔼',
    click: () => resetMdp (row.id)
  }],
]

async function supprimerUtilisateur(id: number) {
  const response = await api.fetchAPIDelete('user', id);
  requetUser();
}

const showModifUser = ref(false); // Variable pour contrôler l'affichage du composant
const selectedUserId = ref<number | null>(null); // Variable pour stocker l'ID de l'utilisateur sélectionné

async function modifierUtilisateur(id: number) {

  console.log("Modif user funct",id);
  selectedUserId.value = id; // Stocker l'ID de l'utilisateur sélectionné
  console.log("Modif user funct2");
  showModifUser.value = true; // Afficher le composant modifUser
}

async function resetMdp(id: number) {
  const response = await api.fetchAPIPostWithId('user/resetPassword/', id, {});
  requetUser();
}

async function verouillerUtilisateur(id: number) {
  const response = await api.fetchAPIGet('user/block/' + id);
  requetUser();
}

</script>


<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
  background-color: #FFFFFF;
  border-radius: 5px;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  cursor: pointer;
  border-radius: 5px;
}

th {
  position: sticky;
  top: -0.5px;
  background-color: #FFFFFF;
  text-align: left;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.column-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.action-icons {
  display: flex;
  align-items: center;
}

.icon-background {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 5px;
  width: auto;
  height: 19px;
  padding: 0.09rem;
  border-radius: 5px;
}

.icon-image {
  width: 35px;
  height: 35px;
  padding: clamp(0.3rem, 0.7vw, 3rem);
}
</style>