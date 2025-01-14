<template>
  <div class="button-container">
    <button class="custom-button" @click="toggleRectangle">
      <img src="@/assets/cloche.png" alt="Button Image" class="button-image" />
    </button>
    <div v-if="showRectangle" class="rectangle">
      <span class="rectangle-title">Notifications</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CustomButton',
  data() {
    return {
      showRectangle: false,
    };
  },
  methods: {
    toggleRectangle() {
      this.showRectangle = !this.showRectangle;
    },
  },
};
</script>

<style scoped>
.button-container {
  position: relative;
}

.custom-button {
  width: 60px; /* Taille du bouton */
  height: 60px; /* Taille du bouton */
  background-color: black; /* Fond noir */
  border-radius: 15px; /* Bords arrondis */
  display: flex;
  justify-content: center;
  align-items: center;
  border: 3px solid #95BD75; /* Bordure de 3px de couleur #95BD75 */
  cursor: pointer;
  margin-bottom: 5px;
  margin-top: 5px;
  margin-right: 5px;
}

.button-image {
  width: 26px; /* Ajustez la taille de l'image */
  height: 26px; /* Ajustez la taille de l'image */
}

.rectangle {
  width: 411px; /* Largeur du rectangle */
  height: 368px; /* Hauteur du rectangle */
  background-color: #0000001C; /* Couleur de fond du rectangle */
  margin-top: 10px; /* Espace entre le bouton et le rectangle */
  position: absolute;
  top: 70px; /* Position en dessous du bouton */
  right: 0;
  padding: 10px; /* Ajout de padding pour le texte */
  box-sizing: border-box;
  margin-bottom: 5px;

  margin-right: 5px;
}

.rectangle-title {
  position: absolute;
  top: 10px;
  left: 10px;
  font-size: 16px;
  font-weight: bold;

}
</style>