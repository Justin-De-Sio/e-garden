<template>
  <div>

    <UButton
    icon="i-heroicons-bell-alert"
    size="lg"
    color="white"
    square
    variant="solid"
    :ui="{rounded:'rounded-[0.8rem]', color:{white: {
      solid: 'shadow-sm text-white dark:text-gray-900 bg-gray-900 hover:bg-[#95bd75] disabled:bg-gray-900 aria-disabled:bg-gray-900 dark:bg-white dark:hover:bg-gray-100 dark:disabled:bg-white dark:aria-disabled:bg-white focus-visible:ring-inset focus-visible:ring-2 focus-visible:ring-primary-500 dark:focus-visible:ring-primary-400',
      link: 'text-gray-900 dark:text-white underline-offset-4 hover:underline focus-visible:ring-inset focus-visible:ring-green-100 focus-visible:ring-green-100 dark:focus-visible:ring-primary-400'
    }}}"
    @click="isOpen = true"
  />
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
