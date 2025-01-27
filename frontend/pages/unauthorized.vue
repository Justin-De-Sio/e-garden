<template>
  <div class="header">
    <NavbarVertical></NavbarVertical>
  </div>
  <div class="body_title">
    <h2>Vous n'êtes pas autorisé à accéder à cette page</h2>
  </div>
</template>

<script setup lang="ts">
import NavbarVertical from "~/components/navbar_vertical.vue"
</script>

<style scoped>
.body_title {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;
}

.body_title h2 {
  text-align: center;
  margin-bottom: 10rem;
  font-family: "Gilroy-Regular";
  color: black;
  font-size: clamp(1.8rem, 3vw, 6rem);
}
</style>