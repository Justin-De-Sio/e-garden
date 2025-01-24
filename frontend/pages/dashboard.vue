<template>
    <div>

    </div>
</template>

<script setup lang="ts">
    definePageMeta({
        middleware: "auth",
        role: ["ADMINISTRATEUR","UTILISATEUR"],
    })
</script>

<style scoped>

</style>