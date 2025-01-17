<template>
  <table>
    <thead>
    <tr>
      <th v-for="column in columns" :key="column.key" @click="sortColumn(column.key)">
        <div class="column-header">
          {{ column.label }}
              {{ sortOrder === 'asc' ? '🔼' : '🔽' }}
        </div>
      </th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="row in sortedRows" :key="row.id">
      <td v-for="column in columns" :key="column.key">
        {{ row[column.key] }}
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
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  cursor: pointer;
}

th {
  background-color: #f2f2f2;
  text-align: left;
}

.column-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

th span {
  margin-left: 0px;
}
</style>