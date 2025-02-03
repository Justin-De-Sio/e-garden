<template>
    <div>
        <newReport></newReport>
    </div>
</template>

<script setup lang="ts">
    import newReport from '~/components/new-report.vue'
    import {getTokenObject} from "~/services/SessionServices";

    console.log(getTokenObject())
</script>

<style scoped>

</style>