<template>
  <div class="">
    <NavbarVertical></NavbarVertical>
  </div>
</template>

<script setup lang="ts">
  import NavbarVertical from "~/components/navbar_vertical.vue"
  import Notification from "~/components/notification2.vue"
</script>

<style scoped>

::v-deep(body) {
  background-color: green;
}
</style>