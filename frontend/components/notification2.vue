<template>
  <div>
    <UButton
      icon="i-heroicons-bell-alert"
      size="lg"
      color="white"
      square
      variant="solid"
      :ui="{rounded:'rounded-[0.8rem]', color:{white: {solid: 'shadow-sm text-white dark:text-gray-900 bg-gray-900 hover:bg-[#95bd75]'}}}"
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

        <ul>
          <li v-for="notification in notifications" :key="notification.id" class="notification-item">
            <div class="notification-wrapper">
              <div class="notification-icon-wrapper">
                <div class="notification-icon">
                  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="icon">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M20.25 8.511c.884.284 1.5 1.128 1.5 2.097v4.286c0 1.136-.847 2.1-1.98 2.193-.34.027-.68.052-1.02.072v3.091l-3-3c-1.354 0-2.694-.055-4.02-.163a2.115 2.115 0 0 1-.825-.242m9.345-8.334a2.126 2.126 0 0 0-.476-.095 48.64 48.64 0 0 0-8.048 0c-1.131.094-1.976 1.057-1.976 2.192v4.286c0 .837.46 1.58 1.155 1.951m9.345-8.334V6.637c0-1.621-1.152-3.026-2.76-3.235A48.455 48.455 0 0 0 11.25 3c-2.115 0-4.198.137-6.24.402-1.608.209-2.76 1.614-2.76 3.235v6.226c0 1.621 1.152 3.026 2.76 3.235.577.075 1.157.14 1.74.194V21l4.155-4.155" />
                  </svg>
                </div>
              </div>
              <div class="notification-content">
                <h3 class="notification-title">{{ notification.title }}</h3>
                <p class="notification-description">{{ notification.description }}</p>
              </div>
            </div>
          </li>
        </ul>
        <template #footer>
          <Placeholder class="h-8" />
        </template>
      </UCard>
    </USlideover>
  </div>
</template>

<script setup lang="ts">
import { callAPI } from '~/services/callAPI';
const isOpen = ref(false);
const notifications = ref<Notification[]>([]);
const api = new callAPI();

interface Notification {
  id: number;
  title: string;
  description: string;
}

const fetchNotif = async () => {
  const response = await api.fetchAPIGetPaginated("/api/event", 0, 5);
  notifications.value = response.content;
};

onMounted(() => {
  fetchNotif();
});
</script>

<style scoped>
.notification-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1rem 1rem 1rem 0.5rem;
  border-bottom: 1px solid #e5e7eb;
}

.notification-wrapper {
  display: flex;
  align-items: center;
  gap: 1rem;
  width: 100%;
}

.notification-icon-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 3rem;
  height: 3rem;
  border-radius: 50%;
  background-color: #f3f4f6;
  flex-shrink: 0;
}

.notification-icon {
  flex-shrink: 0;
}

.icon {
  width: 1.5rem;
  height: 1.5rem;
  color: #6b7280;
}

.notification-content {
  display: flex;
  flex-direction: column;
  flex: 1;
}

.notification-title {
  font-size: 1rem;
  font-weight: 600;
  color: #111827;
}

.notification-description {
  font-size: 0.875rem;
  color: #6b7280;
}

/* Responsive Styles */
@media (max-width: 640px) {
  .notification-item {
    flex-direction: column;
    align-items: flex-start;
  }
  .notification-content {
    margin-top: 0.5rem;
  }
}
</style>