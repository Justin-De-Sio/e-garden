<template>
    <div id="app">
      <navbar />
      <main class="main-content">
        <header class="header">
          <h1>Section capteur</h1>
          <p>Tout vos capteurs réunis, ici</p>
        </header>
        <section class="content">
          <!-- Contenu principal ici -->
        </section>
      </main>
    </div>
  </template>
  
  <script>
  import Navbar from "./components/navbar.vue"; 
  
  export default {
    name: "App",
    components: {
      Navbar,
    },
  };
  </script>