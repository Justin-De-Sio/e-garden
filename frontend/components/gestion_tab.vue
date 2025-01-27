<template>
  <div class="table-container">
    <UTable  :rows="people" :columns="columns" ui="{thead:'sticky top-0 z-index-1'}">
      <template #actions-header>
        <UButton @click="ajouterUtilisateur()">
          Ajouter un utilisateur
        </UButton>
      </template>
      <template #actions-data="{ row }">
        <UDropdown :items="items(row)">
          <UButton color="gray" variant="ghost" icon="i-heroicons-ellipsis-horizontal-20-solid" />
        </UDropdown>
      </template>
    </UTable>
    <ajouter v-if="showAddUser" @close="handleCloseAdd" :requet-user="requetUser" user-id=""/>
    <modif-user v-if="showModifUser" :user-id="selectedUserId"  @close="handleCloseMod" :requet-user="requetUser"/>
  </div>
</template>

<script setup lang="ts">

import { ref, computed, onMounted } from 'vue';
import { callAPI } from '~/services/callAPI';
import modifUser from '~/components/modifUser.vue';
import ajouter from '~/components/ajouter.vue'


const api = new callAPI();

const selectedUserId = ref<number | null>(null);

const showModifUser = ref(false);
const showAddUser = ref(false);

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
    console.log(showAddUser.value);
    console.log("---------------",response);
  } catch (error) {
    console.error('Erreur lors de la récupération des données :', error);
  }
}

const items = row => [
  [{
    label: 'Modifier',
    click: () => modifierUtilisateur(row.id)
  }, {
    label: 'Supprimer',
    click: () => supprimerUtilisateur(row.id)
  }, {
    label: 'Verouiller',
    click: () => verouillerUtilisateur(row.id)
  }, {
    label: 'Réinitialiser le mot de passe',
    click: () => resetMdp (row.id)
  }],
]

async function supprimerUtilisateur(id: number) {
  const response = await api.fetchAPIDelete('user', id);
  requetUser();
}
async function ajouterUtilisateur() {
  showAddUser.value = true;
}

async function modifierUtilisateur(id: string) {
  selectedUserId.value = id;
  showModifUser.value = true;
}

async function resetMdp(id: number) {
  const response = await api.fetchAPIPostWithId('user/resetPassword/', id, {});
  requetUser();
}

async function verouillerUtilisateur(id: number) {
  const response = await api.fetchAPIGet('user/block/' + id);
  requetUser();
}

function handleCloseMod() {
  console.log("handlecloseMod");
  showModifUser.value = false;
  requetUser();
}

function handleCloseAdd() {
  console.log("handlecloseAdd");
  showAddUser.value = false;
  requetUser();
}

</script>

<style scoped>
.table-container {
  overflow-y: auto;
  max-height: 400px;
}

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
  top: 0;
  background-color: #FFFFFF;
  text-align: left;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 5px;
  z-index:1;
}
</style>