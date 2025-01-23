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
async function requetUser(){
  try {
    console.log("requetUser");
    const response = await api.fetchAPIGet('/user/all') as Person;
    people = response;
    console.log("---------------",response);
  } catch (error) {
    console.error('Erreur lors de la récupération des données :', error);
  }
}

const items = row => [
  [{
    label: 'Modifier',
    icon: '🔼',
    click: () => console.log('Edit', row.id)
  }, {
    label: 'Supprimer',
    icon: '🔼'
  }, {
    label: 'Verouiller',
    icon: '🔼'
  }, {
    label: 'Réinitialiser le mot de passe',
    icon: '🔼'
  }],
]

const selected = ref([people[1]])


</script>