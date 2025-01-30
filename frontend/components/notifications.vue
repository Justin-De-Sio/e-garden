<template>
  <div>
    <div class="button_not">
      <button type="button" @click="isOpen = true">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6 text-black">
          <path stroke-linecap="round" stroke-linejoin="round" d="M14.857 17.082a23.848 23.848 0 0 0 5.454-1.31A8.967 8.967 0 0 1 18 9.75V9A6 6 0 0 0 6 9v.75a8.967 8.967 0 0 1-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 0 1-5.714 0m5.714 0a3 3 0 1 1-5.714 0" />
        </svg>
      </button>
    </div>
    <USlideover v-model="isOpen" class="Slider">
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
          <div class="header_placement">
            <h2>Notifications</h2>
            <div class="chevron">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6" @click="changePage('prev')" >
                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
              </svg>
              <h2>{{ currentPage + 1 }}</h2>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="size-6" @click="changePage('next')" v-if="checkNextPage()" >
              <path stroke-linecap="round" stroke-linejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" />
            </svg>
         </div>

          </div>

        </template>

        <ul>
          <li v-for="notification in notifications.content" :key="notification.id" class="notification-item">
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
const notifications = ref();
const api = new callAPI();
const currentPage = ref(0);

interface UserProfileContent {
  id: number;
  title: string;
  description: string;
}

interface ApiResponse {
  content: UserProfileContent[]; 
  totalPages: number,
}

const changePage = (direction: 'prev' | 'next') => {
  if (direction === 'prev' && currentPage.value > 0) {
    currentPage.value--;
  } else if (direction === 'next' && currentPage.value < notifications.value.totalPages - 1 ) {
    currentPage.value++;
  }
  fetchNotif(currentPage.value);
};

const checkNextPage = () => {

  return currentPage.value < notifications.value.totalPages - 1;
};


const fetchNotif = async (page: number) => {
  const response = await api.fetchAPIGetPaginated("event", page, 5) as ApiResponse;
  notifications.value = {
    content: response.content,
    totalPages: response.totalPages
  }
  console.log(response);
};

onMounted(() => {
  fetchNotif(currentPage.value);
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

.header_placement{
  display: flex;
  flex-direction: row;
}

.chevron{
  display: flex;  
  margin-left: auto;
  gap: 1rem;
  position: sticky;
  padding-right: 1rem;
}

.chevron svg{
  cursor: pointer;
}


.button_not button{
  background: none;
  border: none;
  width: min(7vw, 2rem);
  height: min(7vw, 2rem);
  background-color: white;
  border-radius: 0.5rem;
  margin: auto;
  margin-top: 0.2rem;
  cursor: pointer ;
}


.button_not button:hover{
  opacity: 0.7;
}

.button_not button svg{
  margin: auto;
  width: min(4vw, 1.2rem);
  height: min(4vw, 1.2rem);
}





@media (max-width: 640px) {
  .notification-item {
    flex-direction: column;
    align-items: flex-start;
  }
  .notification-content {
    margin-top: 0.5rem;
  }

  .chevron{
    top: 1.2rem;
    right: 3rem;
  }

  .chevron svg{
    cursor: pointer;
}
.Slider button{
  padding-top: 0.2rem;
}


}
</style>