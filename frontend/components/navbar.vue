<template>
    <nav :class="{expanded : IsExpanded}">
      <ul>
        <li class="logoItem">
          <img src="../assets/logo.png" alt="logo" width="80px" height="auto">
        </li>
        <li class="expandItem" @click="toggleMenu">
          <img src="../assets/angle.png" :class="{ rotated: IsExpanded }" alt="angle" width="25px" height="auto">
        </li>
        <!-- Project profil -->
        <li v-if="currentRole" class="role" :class="{expanded : IsExpanded}">
          <ul :class="{expanded : IsExpanded}">
            <li
              v-for="project in currentRole.menuProject"
              :key="project.name"
              class="itemProject"
              :class="{expanded : IsExpanded, active: isActive(project.name) }"
              
            >
              <div class="hoverItemProject" :class="{expanded : IsExpanded}">
                <img :src="project.icon" :alt="project.name" width="25" height="25" />
                <h4 class="nameItemProject" :class="{expanded : IsExpanded}" >{{ project.name }}</h4>
              </div>
            </li>
          </ul>
          <!-- Items profil -->
          <div class="profileContainer" :class="{expanded : IsExpanded}">
            <li
              v-for="(project, index) in currentRole.menuProfil"
              :key="project.name"
              :class="['itemProfil', { lastItemProfil: index === currentRole.menuProfil.length - 1 }]"
            >
              <div class="hoverItemProject" :class="{expanded : IsExpanded}">
                <img :src="project.icon" :alt="project.name" width="25" height="25" />
                <h4 class="nameItemProject" :class="{expanded : IsExpanded}">{{ project.name }}</h4>
              </div>
            </li>
          </div>
        </li>
        <!-- Si aucun rôle défini -->
        <li v-else>
          <p>Aucun rôle spécifié ou rôle invalide.</p>
        </li>
      </ul>
    </nav>
</template>

  
  
<script setup>
  import { ref, computed } from 'vue';
  import { useRoute } from 'vue-router';
  import { JWTPayload } from '~/services/jwtpayload';

  const roles = ref([
    {
      name: 'administrateur',
      menuProject: [
        { name: 'Security', icon: '/_nuxt/assets/camera.png' },
        { name: 'Gestionnaires', icon: '/_nuxt/assets/gestion.png' },
        { name: 'PPE1', icon: '/_nuxt/assets/capteur.png' },
        { name: 'PPE2', icon: '/_nuxt/assets/applications.png' },
      ],
      menuProfil: [
        { name: 'Paramètre', icon: '/_nuxt/assets/parametres.png' },
        { name: 'Profil', icon: '/_nuxt/assets/profil.png' },
      ],
    },
    {
      name: 'utilisateur',
      menuProject: [
        { name: 'PPE1', icon: '/_nuxt/assets/capteur.png' },
        { name: 'PPE2', icon: '/_nuxt/assets/applications.png' },
      ],
      menuProfil: [
        { name: 'Paramètres', icon: '/_nuxt/assets/parametres.png' },
        { name: 'Profil', icon: '/_nuxt/assets/profil.png' },
      ],
    },
  ]);

  const IsExpanded = ref(false);
  const route = useRoute();
  const sessionCookie = useCookie('session');
  const token = sessionCookie.value;
  const rolePayload = JWTPayload(token);

  const currentRole = computed(() => {
    return roles.value.find((role) => role.name.toLowerCase() === (rolePayload || "").toLowerCase()) || null;
  });



  // Vérifie si l'élément est actif
  const isActive = (projectName) => {
    const currentPath = route.path.toLowerCase().replace('/', '');
    
    return currentPath === projectName.toLowerCase();
  };

  const toggleMenu = () => {
    IsExpanded.value = !IsExpanded.value;
  };
</script>

  
<style scoped>


    nav {
        display: flex;
        flex-direction: column;
        width: 130px;
        height: 100vh;
        background-color: #151b11;
        color: white;
        padding: 1rem;
        position: relative;
        transition: width 0.3s ease;
    }

    nav.expanded{
      width: 270px;
    }



    .expandItem {
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: #151b11;
        position: absolute;
        top: 9.5rem;
        right: -1em; 
        cursor: pointer;
        width: 2.5em;
        height: 2.5em;
        border-radius: 50%;

    }

    .expandItem img {
        display: block;
        transform: rotate(-90deg);
        margin-left: 8px;
    }

    .expandItem img.rotated {
        transform: rotate(90deg);
    }


    ul {
        display: flex;
        flex-direction: column;
        align-items: center;
        list-style: none;
        height: 100%;
        padding: 0;
        margin: 0;
    }

    ul.expanded{
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        list-style: none;
        height: 100%;
        width: 100% ;
        padding: 0;
        margin: 0;
    }


    .role {
        display: flex;
        flex-direction: column;
        flex-grow: 1;
        width: 100%;
    }

    .role.expanded{
        display: flex;
        flex-direction: column;
        flex-grow: 1;
        width: 100%;
        padding: 0 1rem;
    }

    .itemProject {
        margin: 0.5rem 0;
        cursor: pointer;
    }

    .itemProject.expanded {
        margin: 0.5rem 0;
        cursor: pointer;
        width: 100%;

    }

    .nameItemProject {
        display: none;
    }

    .nameItemProject.expanded {
        display: flex;
    }

    .hoverItemProject {
        padding: 1.2rem 1.4rem;
        border-radius: 1rem;
    }

    .hoverItemProject h4{
        font-family: "Gilroy-Medium" , sans-serif;
    }

    .hoverItemProject.expanded{
        display: flex;
        width: 100%;
        align-items: center;
        flex-direction: row;
        gap: 2rem;

    }

    .itemProject.active .hoverItemProject {
      background-color: #95bd75; 
    }


    .logoItem {
        cursor: pointer;
        margin: 2rem 0 4rem 0;
    }

    .profileContainer {
        margin-top: auto; 
        display: flex;
        flex-direction: column;
        align-items: center; 
        width: 100%; 
    }

    .profileContainer.expanded{
        margin-top: auto; 
        display: flex;
        flex-direction: column;
        align-items: flex-start; 
        width: 100%; 
    }

    .itemProfil {
        cursor: pointer;
        margin: 0.5rem 0;
    }


    .itemProfil.lastItemProfil .hoverItemProject:hover {
        background-color: transparent;
    }

    .itemProfil.lastItemProfil h4 {
        cursor: default;
    }

    
    

</style>
  