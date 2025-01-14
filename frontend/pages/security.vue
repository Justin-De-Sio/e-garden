
<template>
  <navbar></navbar>
  <div class="wrapper_notification_component">
  </div>
</template>


<script setup lang="ts">
  definePageMeta({
    middleware: 'auth', 
  });
</script>


<style scoped>
.wrapper_notification_component{
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>