<script setup lang="ts">

import NavbarVertical from "~/components/navbar_vertical.vue";
import Header_title from "~/components/header_title.vue";
definePageMeta({
  middleware: "auth",
})
</script>

<template>
  <div class="wrapper_page">
    <NavbarVertical></NavbarVertical>
    <div class="header_bis">
      <header_title title="PPE1" subtitle="Sous titre"></header_title>
    </div>  </div>
</template>

<style scoped>

</style>