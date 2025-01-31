<template>
  <div class="table-container">
    <UTable :rows="people" :columns="columns"
            :ui="{ th: { base: 'sticky top-0 bg-gray-100'}, 
            tr: { base: 'bg-white' }}"
             style="background: #f2f4ef">
      <template #actions-header>
        <UButton @click="ajouterUtilisateur()">
          Ajouter un utilisateur
        </UButton>
      </template>
      <template #actions-data="{ row }">
        <UDropdown :items="items(row)" v-if="isMe(row.email)">
          <UButton color="gray" variant="ghost" icon="i-heroicons-ellipsis-horizontal-20-solid"/>
        </UDropdown>
      </template>
    </UTable>
    <ajouter v-if="showAddUser" @close="handleCloseAdd" :requet-user="requetUser"/>
    <modif-user v-if="showModifUser" :user-id="selectedUserId" @close="handleCloseMod" :requet-user="requetUser"/>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref} from 'vue';
import {callAPI} from '~/services/callAPI';
import modifUser from '~/components/edit-user.vue';
import ajouter from '~/components/add-user.vue'
import type {User} from "~/model/User";
import {getToken} from "~/services/getToken";

const api = new callAPI();

const selectedUserId = ref<number | null>(null);

const showModifUser = ref(false);
const showAddUser = ref(false);

onMounted(async () => {
  await requetUser();
});

let columns = [
  {key: 'id', label: 'ID'},
  {key: 'surname', label: 'Nom', sortable: true},
  {key: 'name', label: 'Prénom', sortable: true},
  {key: 'email', label: 'Email', sortable: true},
  {key: 'className', label: 'Classe', sortable: true},
  {key: 'groupNumber', label: 'Groupe', sortable: true},
  {key: 'role', label: 'Rôle', sortable: true},
  {key: 'actions', label: 'Actions'}];

const people = ref<User[]>([]);

async function requetUser() {
  try {
    people.value = await api.fetchAPIGet('user/all') as User[];
  } catch (error) {
    console.error('Erreur lors de la récupération des données :', error);
  }
}

const isSelectedDelete = ref(false);

const items = row => [
  [{
    label: 'Modifier',
    click: () => modifierUtilisateur(row.id )
  }, {
    label: 'Supprimer',
    click: () => supprimerUtilisateur(row.id)
  }, {
    label: row.locked ? 'Verrouiller' : 'Déverrouiller',
    click: () => verouillerUtilisateur(row.id)
  }, {
    label: 'Réinitialiser le mot de passe',
    click: () => resetMdp(row.id)
  }],
]

async function supprimerUtilisateur(id: number) {
  if (confirm('Êtes-vous sûr de vouloir annuler?')) {
    await api.fetchAPIDelete('user', id);
    await requetUser();
  }
}

async function ajouterUtilisateur() {
  showAddUser.value = true;
}

async function modifierUtilisateur(id: number) {
  selectedUserId.value = id;
  showModifUser.value = true;
}

async function resetMdp(id: number) {
  await api.fetchAPIPostWithId('user/resetPassword', id, {});
  await requetUser();
}

async function verouillerUtilisateur(id: number) {
  await api.fetchAPIGet('user/block/' + id);
  await requetUser();
}

function handleCloseMod() {
  showModifUser.value = false;
  requetUser();
}

function handleCloseAdd() {
  showAddUser.value = false;
  requetUser();
}

function isMe(email: string) {
  const username = getToken().sub;
  return email !== username;
}

</script>

<style scoped>
.table-container {
  overflow-y: auto;
  width: 90%;
  max-height: 400px;
  top:0;
  border: 1px solid black;
  border-radius: 15px;
}

table {
  width: 100%;
  border-collapse: collapse;
  background-color: #f2f4ef;
  border-radius: 5px;
}

th, td {
  border: 1px solid #f2f4ef;
  padding: 8px;
  cursor: pointer;
  border-radius: 5px;
}

th {
  position: sticky;
  top: 0;
  background-color: #f2f4ef;
  text-align: left;
  padding: 8px;
  border: 1px solid #f2f4ef;
  border-radius: 5px;
  z-index: 1;
}
</style>