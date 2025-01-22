<template>
    <div class="wrapper">
      <img src="/public/assets/logo.png" alt="" width="80px" height="auto">
      <UHorizontalNavigation
        :links="links"
        class="border-b border-gray-200 dark:border-gray-800"
        :ui="{
          after: 'after:h-[2px]',
          base: 'py-7',
          wrapper: 'justify-center',
          container: 'items-center justify-between w-10/12'
        }"
      />
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted } from 'vue';
  import { fetchBackend_URL, UpdateBackend } from '~/services/call_backend';
  // Structure des données pour les liens
  interface Link {
    label: string;
    icon?: string;
    to?: string;
    avatar?: {
      src: string;
    };
  }
  
  // Tableau de liens initial
  const links = ref<Link[]>([
    {
      label: 'Sécurité',
      icon: 'i-heroicons-video-camera',
      to: '/security',
    },
    {
      label: 'PPE1',
      icon: 'i-heroicons-bolt',
      to: '/ppe1',
    },
    {
      label: 'PPE2',
      icon: 'i-heroicons-globe-europe-africa',
      to: '/ppe2',
    },
    {
      label: 'Gestionnaire',
      icon: 'i-heroicons-rectangle-stack',
      to: '/gestionnaires',
    },
    {
      label: 'Paramètres',
      icon: 'i-heroicons-cog-6-tooth',
      to: '/settings',
    },
    {
      label: 'Profil', 
      avatar: {
        src: 'https://avatars.githubusercontent.com/u/739984?v=4',
      },
      to: '/profil',
    },
  ]);
  
  const fetchProfile = async () => {
    try {
      const response = await fetchBackend_URL('/api/user/profil'); 


      // Mise à jour dynamique du dernier lien
      const updatedLinks = [...links.value];
      updatedLinks[updatedLinks.length - 1].label = response.name || 'Profil';
      links.value = updatedLinks;
    } catch (error) {
      console.error('Erreur lors de la récupération du profil :', error);
    }
  };
  
  onMounted(() => {
    fetchProfile();
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
  </style>
  