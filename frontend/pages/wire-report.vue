<template>
    <div>
        <NavigationHorizontal></NavigationHorizontal>
    </div>
    <h1>Fil des Rapports (v Twitter)</h1>
</template>

<script setup lang="ts">
    import NavigationHorizontal from "~/components/navbar_vertical.vue"

    definePageMeta({
    middleware: "auth",
  })
</script>

<style scoped>

</style>