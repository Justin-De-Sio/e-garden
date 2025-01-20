<template>
    <div>
      <div
        v-if="isLoading"
        class="loading-wrapper"
        :class="{ 'fade-out': isFading }"
      >
        <div class="spinner"></div>
      </div>
      <div v-else class="main-content">
        <div class="center_placement">
          <div class="logo_placement">
            <img src="~/public/assets/logo.png" alt="" />
          </div>
          <div class="center_title">
            <h1>Bienvenue sur le site E-Garden</h1>
            <h3>Une application qui centralise tous vos besoins liés au potager</h3>
          </div>
          <button @click="loginButton" class="button-login">Entrez dans le potager !</button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted } from "vue";
  import { useRouter } from "vue-router";
  
  const isLoading = ref(true);
  const isFading = ref(false);
  const router = useRouter();
  
  const loginButton = () => {
    router.push("/login");
  };
  
  onMounted(() => {
    setTimeout(() => {
      isFading.value = true; 
      setTimeout(() => {
        isLoading.value = false;
      }, 500); 
    }, 1000); 
  });
  </script>
  
<style lang="css" scoped>

    .loading-wrapper {
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: #f2f4ef;
        width: 100%;
        height: 100vh;
        position: fixed;
        top: 0;
        left: 0;
        z-index: 9999;
        transition: opacity 1s ease, visibility 1s ease;
    }
    
    .loading-wrapper.fade-out {
        opacity: 0;
        visibility: hidden;
    }
    
    .spinner {
        width: 50px;
        height: 50px;
        border: 5px solid #ccc;
        border-top: 5px solid #95bd75;
        border-radius: 50%;
        animation: spin 1s linear infinite;
    }
    
    @keyframes spin {
        0% {
        transform: rotate(0deg);
        }
        100% {
        transform: rotate(360deg);
        }
    }
    

    .main-content {
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: #f2f4ef;
        width: 100%;
        height: 100vh;
        padding: 3rem;
    }
    
    .center_placement {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center; 
        width: 100%; 

        }

    .center_title{
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
  
    .center_title h1 {
        font-family: "Gilroy-Medium";
        font-size: clamp(1.3rem, 2.5vw, 4rem); 
    } 
    
    .center_title h3 {
        font-family: "Gilroy-Regular";
        font-size: clamp(1rem, 1.5vw, 1rem); 
        text-align: center;
    }
    
    .logo_placement img {
        width: 8rem;
        height: auto;
    }
    
    .button-login {
        background: none;
        border: none;
        width: 17rem;
        height: 5vh;
        background-color: #95bd75;
        font-family: "Gilroy-Regular";
        font-size: 1rem;
        border-radius: 0.7rem;
        color: white;
        margin-top: 2rem;
        cursor: pointer;
    }
  </style>
  