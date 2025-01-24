<template>
  <div class="">
    <NavbarVertical></NavbarVertical>
  </div>
</template>

<script setup lang="ts">
  import NavbarVertical from "~/components/navbar_vertical.vue"
  import Notification from "~/components/notification2.vue"

  definePageMeta({
    middleware: "auth",
    role: ["ADMINISTRATEUR"],
  })
</script>

<style scoped>


</style>