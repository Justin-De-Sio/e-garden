
<template>

  <div id="app">
    <CustomComponent
        iconPath="/assets/conge.png"
        iconBackgroundColor="#95BD75"
        title="XX passages"
        subtitle="Nombre de passage"
    />
  </div>

  <div id="app">
    <CustomComponent
        iconClass="@/assets/disque.png"
        iconBackgroundColor="#457B9D54"
        title="XX% utilisé"
        subtitle="stockage disque dur"
    />
  </div>

  <div id="app">
    <CustomComponent
        iconClass="@/assets/attention.png"
        iconBackgroundColor="#DA555280"
        title="XX absences de CR"
        subtitle="Alertes"
    />
  </div>
  <!--
  <navbar></navbar>
  <div class="wrapper_notification_component">
    <notification></notification>
  </div>-->

</template>


<script>
import CustomComponent from '@/components/indicator.vue';

export default {
  name: 'App',
  components: {
    CustomComponent
  }
}
</script>


<style scoped>
.wrapper_notification_component{
  display: flex;
  justify-content: center;
  align-items: center;
}


</style>