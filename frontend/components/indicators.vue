<template>
    <div class="custom-frame">
      <div class="icon-background" :style="{ backgroundColor: iconBackgroundColor }">
        <img :src="iconPath" alt="icon" class="icon-image" />
      </div>
      <div class="text-container">
        <h1>{{ title }}</h1>
        <h2>{{ subtitle }}</h2>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'CustomFrame',
    props: {
      iconPath: {
        type: String,
        required: true
      },
      iconBackgroundColor: {
        type: String,
        required: true
      },
      title: {
        type: String,
        required: true
      },
      subtitle: {
        type: String,
        required: true
      }
    }
  }
  </script>
  
  <style scoped>
  @font-face {
    font-family: 'gilroy-medium';
    src: url('@/assets/fonts/gilroy-medium.ttf') format('truetype');
    font-weight: normal;
    font-style: normal;
  }
  
  .custom-frame {
    width: 256px;
    height: 81px;
    background-color: #FFFFFF; /* Couleur de fond */
    border: 2px solid grey;
    display: flex;
    flex-direction: row; /* Aligner les éléments horizontalement */
    align-items: center;
    padding: 0 10px; /* Espacement interne */
    font-family: 'NomDeVotrePolice', sans-serif; /* Utilisation de la police */
    border-radius: 15px; /* Bords arrondis */
  }
  .icon-background {
    border-radius: 12px; /* Bords arrondis */
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 10px; /* Espacement entre l'icône et le texte */
    width: 38px;
    height: 38px;
  }
  .icon-image {
    width: 21px; /* Taille de l'icône */
    height: 21px; /* Taille de l'icône */
  }
  .custom-frame i {
    font-size: 21px; /* Taille de l'icône */
  }
  .text-container {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
  }
  .custom-frame h1 {
    font-size: 13px;
    margin: 0;
    color: #000000;
  }
  .custom-frame h2 {
    font-size: 11px;
    color: #7B7B7B;
    margin: 0;
  }
  
  </style>
  