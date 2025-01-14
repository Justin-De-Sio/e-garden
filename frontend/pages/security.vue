
<template>
  <navbar/>

  <div class="wrapper_notification_component">
  </div>
</template>


<script setup lang="ts">
import navbar from "~/components/navbar.vue";
import { JWTPayload } from '~/services/jwtpayload';

  definePageMeta({
    middleware: 'auth', 
  });

  
  const sessionCookie = useCookie('session');
  const token = sessionCookie.value;
  const rolePayload = JWTPayload(token);
  console.log('Role Payload:', rolePayload);


</script>


<style scoped>
.wrapper_notification_component{
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>