<template>
    <div>
      <h1>Événement</h1>
      <div v-if="event">{{ event }}</div>
      <div v-else>Chargement...</div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  
  const event = ref(null);
  const sessionCookie = useCookie("session");
    const token = sessionCookie.value;
    const errorMessage = ref('');

    onMounted(async () => {
    try {
        const data = await $fetch('/api/report/1', {
        headers: {
            Authorization: `Bearer ${token}`,
        },
        });
        event.value = data;
    } catch (error) {
        console.error('Erreur lors de la requête API :', error);
        errorMessage.value = 'Impossible de charger l’événement. Veuillez réessayer plus tard.';
    }
    });

  </script>
  