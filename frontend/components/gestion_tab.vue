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
          <div class="icon-background" :style="{ backgroundColor: '#F4A261' }">
            <img src="public/assets/stylo.png" alt="Modifier" class="icon-image" />
          </div>
          <div class="icon-background" :style="{ backgroundColor: '#DA5552' }">
            <img src="public/assets/croix.png" alt="Supprimer" class="icon-image" />
          </div>
        </div>
      </td>
    </tr>
    </tbody>
  </table>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';

const props = defineProps<{
  columns: Array<{ key: string, label: string }>,
  rows: Array<Record<string, any>>
}>();

const sortKey = ref<string | null>(null);
const sortOrder = ref<'asc' | 'desc'>('asc');

const sortedRows = computed(() => {
  if (!sortKey.value) return props.rows;

  return [...props.rows].sort((a, b) => {
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


</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
  background-color: #FFFFFF;
  border-radius: 5px;
  z-index:999;
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
  z-index: 1; /* Pour s'assurer que le `thead` reste au-dessus des autres éléments */
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