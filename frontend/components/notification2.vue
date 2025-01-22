<template>
  <div>
    <UButton label="Open" @click="isOpen = true" />

    <USlideover v-model="isOpen">
      <UCard
        class="flex flex-col flex-1"
        :ui="{ body: { base: 'flex-1' }, ring: '', divide: 'divide-y divide-gray-100 dark:divide-gray-800' }"
      >
        <template #header>
          <UButton
            color="gray"
            variant="ghost"
            size="sm"
            icon="i-heroicons-x-mark-20-solid"
            class="flex sm:hidden absolute end-5 top-5 z-10"
            square
            padded
            @click="isOpen = false"
          />

          <Placeholder class="h-8" />
          Notification
        </template>

        <Placeholder class="h-full" />

        <template #footer>
          <Placeholder class="h-8" />
        </template>
      </UCard>
    </USlideover>
  </div>
</template>

<script setup lang="ts">
  import { fetchBackend } from '~/services/call_backend';
  const isOpen = ref(false)

  const fetchNotif = async () =>{
    const response = await fetchBackend("/api/event/", 1, 5);
    console.log(response)

  }

  onMounted(() => {
    fetchNotif();
  });

</script>
