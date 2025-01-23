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
            <img src="public/assets/stylo.png" alt="Modifier" class="icon-image" />
          </div>
          <div class="icon-background" :style="{ backgroundColor: '#DA5552' }" @click="supprimerUtilisateur(row.id)">
            <img src="public/assets/croix.png" alt="Supprimer" class="icon-image" />
          </div>
          <div class="icon-background" :style="{ backgroundColor: '#F4A261' }" @click="verouillerUtilisateur(row.id)">
            <img src="public/assets/croix.png" alt="Verouiller" class="icon-image" />
          </div>
          <div class="icon-background" :style="{ backgroundColor: '#95BD75' }" @click="resetMdp(row.id)">
            <img src="public/assets/croix.png" alt="Initialiser" class="icon-image" />
          </div>
        </div>
      </td>
    </tr>
    </tbody>
  </table>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { callAPI } from '~/services/callAPI';

const api = new callAPI();

// Déclarez une variable réactive pour les données
const people = ref([]);

// Requête vers le back pour les personnes
onMounted(async () => {
  requetUser();
});

// nom et clef des colonnes
const columns = [
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
    const response = await api.fetchAPIGet('/user/all');
    people.value = response; // Assignez les données récupérées à `people`
  } catch (error) {
    console.error('Erreur lors de la récupération des données :', error);
  }
}

//supprimer OK
async function supprimerUtilisateur(id: number) {
  const response = await api.fetchAPIDelete('user', id);
  requetUser();
}

//modifier pas ok
async function modifierUtilisateur(id: number) {
  const response = await api.fetchAPIDelete('user', id);
  requetUser();
}

//init pas ok
async function resetMdp(id: number) {
  const response = await api.fetchAPIPostWithId('user/resetPassword/',id, {});
  requetUser();
}

//verrouiller ok
async function verouillerUtilisateur(id: number) {
  const response = await api.fetchAPIGet('user/block/'+id);
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
  background-color: #FFFFFF; /* Garder le fond blanc pour une meilleure lisibilité */
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
  width: 19px;
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