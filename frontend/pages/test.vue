<template>
    <div class="camera_section">
      <div v-if="error" class="error">{{ error }}</div>
      <pre v-else-if="jsonData">{{ formattedJson }}</pre>
      <div v-else>Chargement...</div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed, onMounted } from "vue";
  import { useCookie } from "#app";
  
  // Récupération du token dans le cookie
  const sessionCookie = useCookie("session");
  const token = sessionCookie.value;
  console.log(token);
  
  const jsonData = ref(null);
  const error = ref(null);
  
  // Requête à l'API lors du montage du composant
  const fetchData = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/event/1", {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          withCredentials: true,    
        crossorigin: true,    
        mode: 'no-cors',
        },
      });
  
      if (!response.ok) {
        throw new Error(`Erreur HTTP : ${response.status}`);
      }
  
      jsonData.value = await response.json();
      console.log(jsonData.value);
    } catch (err) {
      error.value = err.message;
    }
  };
  
  // Appel de fetchData lors du montage du composant
  onMounted(() => {
    fetchData();
  });
  
  // Formater les données JSON pour un affichage lisible
  const formattedJson = computed(() =>
    jsonData.value ? JSON.stringify(jsonData.value, null, 2) : null
  );
  </script>
  
  <style scoped>
  pre {
    background-color: #f8f9fa;
    padding: 10px;
    border-radius: 5px;
    font-family: monospace;
    overflow-x: auto;
  }
  .error {
    color: red;
  }
  </style>
  