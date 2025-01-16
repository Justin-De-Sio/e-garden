<template>
  <div class="button-container">
    <button class="custom-button" @click="toggleRectangle">
      <img src="~/assets/cloche.png" alt="Button Image" class="button-image" />
    </button>
    <div v-if="showRectangle" class="rectangle">
      <div class="icon-group">
        <button class="arrow-button" @click="fetchPreviousPage">
          <img src="@/assets/up-arrow.png" alt="Up Arrow" class="arrow-image" />
        </button>
        <button class="arrow-button" @click="fetchNextPage">
          <img src="@/assets/down-arrow.png" alt="Down Arrow" class="arrow-image" />
        </button>
      </div>
      <span class="rectangle-title">Notifications</span>
      <hr class="separator-line" />
      <ul>
        <li v-for="notification in notifications" :key="notification.id">
          {{ notification.message }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CustomButton',
  data() {
    return {
      showRectangle: false,
      notifications: [],
      currentPage: 1,
    };
  },
  methods: {
    toggleRectangle() {
      this.showRectangle = !this.showRectangle;
      if (this.showRectangle) {
        this.fetchNotifications();
      }
    },
    async fetchNotifications() {
      const response = await fetch(`http://localhost:8080/event/paginated?page=${this.currentPage}&size=5`);
      const data = await response.json();
      this.notifications = data.content;
    },
    fetchNextPage() {
      this.currentPage += 1;
      this.fetchNotifications();
    },
    fetchPreviousPage() {
      if (this.currentPage > 1) {
        this.currentPage -= 1;
        this.fetchNotifications();
      }
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
  border-radius: 15px; /* Ajout du radius de 15px */
}

.rectangle-title {
  position: absolute;
  top: 15px; /* Décalage de 5px vers le bas */
  left: 20px; /* Décalage de 10px vers la droite */
  font-size: 16px;
  font-family: 'Gilroy', sans-serif; /* Application de la police Gilroy-Medium */
}

.icon-group {
  display: flex;
  position: absolute;
  top: 10px;
  right: 10px;
}

.arrow-button {
  width: 30px;
  height: 30px;
  background-color: #95BD75; /* Fond vert derrière l'icône */
  border: 1px solid #ccc;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  margin-left: 5px;
}

.arrow-image {
  width: 16px;
  height: 16px;
}

.separator-line {
  margin-top: 40px; /* Espace entre le titre et la ligne */
  border: none;
  border-top: 1px solid #ccc;
}
</style>