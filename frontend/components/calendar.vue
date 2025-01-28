<template>
<div class="wrapper">
    <UPopover :popper="{ placement: 'bottom-start' }">
        <UButton icon="i-heroicons-calendar-days-20-solid" :label="format(date, 'd MMM, yyy')" />

        <template #panel="{ close }">
        <DatePicker v-model="date" is-required @close="close" />
        </template>
    </UPopover>
</div>
</template>

<script setup lang="ts">
import { format } from 'date-fns'
import {DatePicker} from "v-calendar";

const date = ref(new Date())
</script>

<style></style>
