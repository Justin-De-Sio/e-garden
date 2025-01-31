<template>
  <div class="wrapper">
    <img src="/public/assets/logo.png" alt="" width="80px" height="auto">
    <UHorizontalNavigation
        :links="links"
        class=" dark:border-gray-800"
        :ui="{
          after: 'after:h-[2px]',
          base: 'py-7 focus:outline-none',
          wrapper: 'justify-center',
          container: 'items-center justify-between w-10/12',
          label: 'text-white dark:text-black hidden md:block',
          before: 'before:inset-y-3 hover:before:bg-[#95bd75] dark:hover:before:bg-gray-800/50',
          icon:{
            inactive: 'text-white dark:text-gray-500 group-hover:text-white dark:group-hover:text-gray-200',
            active: 'text-white dark:text-gray-200',
          }
        }"
    />
    <div class="button_group">
      <AddReport class="add_button"></AddReport>
      <Notification class="notif_button" v-if="isAdmin"></Notification>
    </div>

  </div>
</template>

<script setup lang="ts">
import {callAPI} from "~/services/callAPI";
import Notification from "~/components/notifications.vue"
import type {User} from "~/model/User";
import AddReport from "~/components/add-report.vue";

// Structure des données pour les liens
interface Link {
  order: number;
  label: string;
  icon?: string;
  to?: string;
  avatar?: {
    src: string;
  };
}

const api = new callAPI();

// Tableau de liens initial
const links = ref<Link[]>([
  {
    order: 2,
    label: 'PPE1',
    icon: 'i-heroicons-bolt',
    to: '/ppe1',
  },
  {
    order: 3,
    label: 'PPE2',
    icon: 'i-heroicons-globe-europe-africa',
    to: '/ppe2',
  },
  {
    order: 6,
    label: 'Profil',
    avatar: {
      src: 'https://avatars.githubusercontent.com/u/739984?v=4',
    },
    to: '/profil',
  },
]);

const adminLinks = ref<Link[]>([
  {
    order: 1,
    label: 'Sécurité',
    icon: 'i-heroicons-video-camera',
    to: '/security',
  },
  {
    order: 4,
    label: 'Gestionnaire',
    icon: 'i-heroicons-rectangle-stack',
    to: '/gestion-users',
  },
  {
    order: 5,
    label: 'Paramètres',
    icon: 'i-heroicons-cog-6-tooth',
    to: '/reglages',
  },
]);
const isAdmin = ref(false);

const fetchProfile = async () => {
  try {
    const response = await api.fetchAPIGet('user/profil') as User;
    isAdmin.value = response.role === 'ADMINISTRATEUR';
    let updatedLinks: Link[] = [];

    if (isAdmin.value) {
      updatedLinks = links.value.concat(adminLinks.value); // Concatène correctement
      updatedLinks.sort((a, b) => a.order - b.order);
    }
    updatedLinks[updatedLinks.length - 1].label = response.name || 'Profil';
    links.value = updatedLinks;
  } catch (error) {
    console.error('Erreur lors de la récupération du profil :', error);
  }
};

onMounted(async () => {
  await fetchProfile();
});
</script>

<style scoped>
.wrapper {
  display: flex;
  padding-right: 3rem;
  background-color: #151b11;
}

.wrapper img {
  margin: 0 3rem 0 1rem;
}

.notif_button {
  margin: auto;
}

.add_button {
  margin: auto;

}

.button_group {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 2rem;
}

@media screen and (max-width: 800px) {
  .wrapper img {
    display: none;
  }

  .wrapper {
    padding-right: 2rem;
  }

  .button_group {
    display: flex;
    gap: 1rem;
    margin-left: auto;
  }

}
</style>
  