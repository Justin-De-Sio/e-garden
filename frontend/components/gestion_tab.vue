<template>
  <UTable  :rows="people" :columns="columns">
    <template #actions-header>
      <UButton @submit="ajouterUtilisateur">

        Ajouter un utilisateur
        <ajouter v-if="showAddUser" :user-id="selectedUserId"  @close="handleClose" :requet-user="requetUser"/>
      </UButton>
    </template>
    <template #actions-data="{ row }">
      <UDropdown :items="items(row)">
        <UButton color="gray" variant="ghost" icon="i-heroicons-ellipsis-horizontal-20-solid" />
      </UDropdown>
    </template>
  </UTable>
  <modif-user v-if="showModifUser" :user-id="selectedUserId"  @close="handleClose" :requet-user="requetUser"/>
</template>

<script setup lang="ts">

import { ref, computed, onMounted } from 'vue';
import { callAPI } from '~/services/callAPI';
import modifUser from '~/components/modifUser.vue';

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
const ajouterUtilisateur = async () => {
  console.log("ajouter user");
  showModifUser.value = true; // Afficher le composant modifUser
}

async function modifierUtilisateur(id: string) {
  console.log("Modif user funct",id);
  selectedUserId.value = id;
  console.log("Modif user funct2" + selectedUserId);
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
function handleClose() {
  showModifUser.value = false;
  showAddUser.value = false;
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
  z-index:999;

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