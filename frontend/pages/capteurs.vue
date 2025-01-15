<template>
    <div id="app">
      <div class="layout">
        <Navbar />
        <div class="content">
          <h1 class="title">Section capteur</h1>
          <h2 class="subtitle">Tous vos capteurs réunis, ici</h2>
          <div class="sensor-container">
            <div class="sensor-list">
              <div
                v-for="(sensor, index) in sensors"
                :key="sensor.title"
                class="sensor-card"
                :class="{ active: selectedSensorIndex === index }"
                @click="selectSensor(index)"
              >
                <img :src="sensor.logo" alt="Logo" class="sensor-logo" />
                <span class="sensor-title">{{ sensor.title }}</span>
              </div>
            </div>
            <div class="sensor-details">
              <h3>What is it?</h3>
              <p>{{ sensors[selectedSensorIndex].description }}</p>
              <div
                v-if="sensors[selectedSensorIndex].labels.length > 0"
                :class="{
                  'label-container': true,
                  horizontal: sensors[selectedSensorIndex].alignment === 'horizontal',
                }"
              >
                <div
                  v-for="(label, idx) in sensors[selectedSensorIndex].labels"
                  :key="idx"
                  class="label"
                >
                  <span class="label-title">{{ label.name }}</span>
                  <span class="label-description">{{ label.description }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import Navbar from "./components/Navbar.vue";
  
  export default {
    name: "App",
    components: {
      Navbar,
    },
    data() {
      return {
        sensors: [
          {
            title: "Soil moisture sensor",
            logo: "/_nuxt/assets/eau.png",
            description:
              "A device that measures the volumetric water content in soil, allowing users to monitor and optimize irrigation. It helps determine whether plants have adequate water or are at risk of drought.",
            alignment: "horizontal",
            labels: [
              { name: "Capteur 1", description: "Output: 123" },
              { name: "Capteur 2", description: "Output: 456" },
              { name: "Capteur 3", description: "Output: 789" },
            ],
          },
          {
            title: "Temperature sensor",
            logo: "/_nuxt/assets/climat.png",
            description:
              "Les capteurs de température sont des dispositifs mesurant la quantité d'énergie thermique ou la fraîcheur générée par un objet ou un système.",
            alignment: "vertical", 
            labels: [{ name: "Capteur 1", description: "Output: 24°C" }],
          },
          {
            title: "Light sensor",
            logo: "/_nuxt/assets/solar.png",
            description: "A sensor that detects the intensity of light in the environment.",
            alignment: "vertical",
            labels: [{ name: "Capteur 1", description: "Output: 300 lux" }],
          },
          {
            title: "Raingauge sensor",
            logo: "/_nuxt/assets/sauver.png",
            description: "A device used to measure rainfall over a specific period of time.",
            alignment: "vertical",
            labels: [{ name: "Capteur 1", description: "Output: 15 mm" }],
          },
        ],
        selectedSensorIndex: 0, // Par défaut, sélectionne le premier capteur
      };
    },
    methods: {
      selectSensor(index) {
        this.selectedSensorIndex = index; // maj index sélectionné
      },
    },
  };
  </script>
  
  <style>
  #app {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    height: 100vh;
    display: flex;
    flex-direction: column;
  }
  
  .layout {
    display: flex;
    height: 100%;
  }
  
  .content {
    flex: 1; 
    padding: 2rem;
    overflow-y: auto;
  }
  
  .title {
    font-size: 2.5rem;
    font-weight: bold;
    margin-bottom: 0.5rem;
  }
  
  .subtitle {
    font-size: 1.25rem;
    color: #555;
    margin-bottom: 1.5rem;
  }
  
  .sensor-container {
    display: flex;
    gap: 2rem;
  }
  
  .sensor-list {
    display: flex;
    flex-direction: column;
    gap: 15px; 
  }
  
  .sensor-card {
    display: flex;
    align-items: center;
    background-color: #F9F8FD;
    border: 2px solid #D9D9D9; 
    border-radius: 15px;
    padding: 1rem;
    width: 240px;
    cursor: pointer;
    transition: transform 0.2s;
  }
  
  .sensor-card.active {
    background-color: #eceaf5;
    border-color: #b6b3c7;
  }
  
  .sensor-card:hover {
    transform: scale(1.02); 
  }
  
  .sensor-logo {
    width: 40px;
    height: 40px;
    margin-right: 1rem;
  }
  
  .sensor-title {
    font-size: 1.2rem;
    font-weight: bold;
    color: #333;
  }
  
  .sensor-details {
    flex: 1;
    max-width: 700px; 
    background-color: #F9F8FD; 
    border: 1px solid #D9D9D9; 
    border-radius: 15px; 
    padding: 1rem;
    display: flex;
    flex-direction: column;
  }
  
  .sensor-details h3 {
    font-size: 1.5rem;
    margin-bottom: 0.5rem;
  }
  
  .sensor-details p {
    font-size: 1rem;
    color: #555;
  }
  
  .label-container {
    margin-top: 1rem;
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
  
  .label-container.horizontal {
    flex-direction: row; /* Aligne horizontalement les capteurs */
    gap: 15px; /* Espace entre les capteurs */
    flex-wrap: wrap; /* Gère les débordements si trop de capteurs */
  }
  
  .label {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    background-color: #fff;
    border: 1px solid #d9d9d9;
    padding: 0.5rem;
    border-radius: 8px;
    min-width: 80px;
  }
  
  .label-title {
    font-weight: bold;
    color: #333;
  }
  
  .label-description {
    font-size: 0.9rem;
    color: #555;
    margin-top: 0.3rem;
  }
  </style>
  