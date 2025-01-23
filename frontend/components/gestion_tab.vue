<template>
  <table>
    <thead>
    <tr>
      <th v-for="column in columns" :key="column.key" @click="sortColumn(column.key)">
        <div class="column-header">
          {{ column.label }}
          <span>
              {{ sortKey === column.key ? (sortOrder === 'asc' ? '🔼' : '🔽') : '🔽' }}
            </span>
        </div>
      </th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="row in sortedRows" :key="row.id">
      <td v-for="column in columns" :key="column.key">
        {{ row[column.key] }}
      </td>
      <td>
        <div class="action-icons">
          <div class="icon-background" :style="{ backgroundColor: '#87CEEB' }" @click="modifierUtilisateur(row.id)">
            <span>Modifier</span>
          </div>
          <div class="icon-background" :style="{ backgroundColor: '#DA5552' }" @click="supprimerUtilisateur(row.id)">
            <span>suppr</span>
          </div>
          <div class="icon-background" :style="{ backgroundColor: '#F4A261' }" @click="verouillerUtilisateur(row.id)">
            <span>verou</span>
          </div>
          <div class="icon-background" :style="{ backgroundColor: '#95BD75' }" @click="resetMdp(row.id)">
            <span>resMDP</span>
          </div>
        </div>
      </td>
    </tr>
    </tbody>
  </table>
  <modifUser v-if="showModifUser" :userId="selectedUserId" @close="showModifUser = false" />
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { callAPI } from '~/services/callAPI';
import modifUser from '~/components/modifUser.vue'; // Importer le composant

const api = new callAPI();

interface Person {
  id: number,
  name: string,
  surname: string,
  email: string,
  role: string,
  className: string,
  groupNumber: number
}

const people = ref<Person[]>([]);
const showModifUser = ref(false); // Variable pour contrôler l'affichage du composant
const selectedUserId = ref<number | null>(null); // Variable pour stocker l'ID de l'utilisateur sélectionné

onMounted(async () => {
  requetUser();
});

const columns: { key: keyof Person; label: string }[] = [
  { key: 'surname', label: 'Nom' },
  { key: 'name', label: 'Prénom' },
  { key: 'email', label: 'Email' },
  { key: 'className', label: 'Classe' },
  { key: 'groupNumber', label: 'Groupe' },
  { key: 'role', label: 'Rôle' },
];

const sortKey = ref<string | null>(null);
const sortOrder = ref<'asc' | 'desc'>('asc');

const sortedRows = computed(() => {
  if (!sortKey.value) return people.value;

  return [...people.value].sort((a, b) => {
    const aValue = a[sortKey.value as keyof typeof a];
    const bValue = b[sortKey.value as keyof typeof b];

    if (aValue === bValue) return 0;

    const order = sortOrder.value === 'asc' ? 1 : -1;
    return aValue > bValue ? order : -order;
  });
});

const sortColumn = (key: string) => {
  if (sortKey.value === key) {
    sortOrder.value = sortOrder.value === 'asc' ? 'desc' : 'asc';
  } else {
    sortKey.value = key;
    sortOrder.value = 'asc';
  }
};

async function requetUser(){
  try {
    const response = await api.fetchAPIGet('/user/all') as Person[];
    people.value = response;
  } catch (error) {
    console.error('Erreur lors de la récupération des données :', error);
  }
}

async function supprimerUtilisateur(id: number) {
  const response = await api.fetchAPIDelete('user', id);
  requetUser();
}

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