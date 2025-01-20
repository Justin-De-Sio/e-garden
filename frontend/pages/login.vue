<template>
    <div class="main_part">    
      <div class="left_part">
        <img src="~/public/assets/potager_login.png" alt="Image d'un potager" class="responsive-img">
      </div>
      <div class="right_part">
        <div class="pos_white_card">
          <div class="logo_title_part">
            <div class="logo_pos">
              <img class="logo" src="~/public/assets/logo.png" alt="Logo">
            </div>
            <div class="title_part">
              <h1>Bienvenue !</h1>
              <h6>Entrez vos informations, s'il vous plaît.</h6>
            </div>
          </div>
  
          <form @submit.prevent="handleSubmit">
            <input type="email" name="email" id="email" v-model="email" placeholder="Email">
            <div class="line"></div>
            <div class="password-wrapper">
              <input type="password" id="password" v-model="password" placeholder="Password">
              <span class="toggle-password" @click="togglePassword">
                <img src="~/public/assets/oeil.png" alt="Afficher/Cacher le mot de passe" class="eye-icon">
              </span>
            </div>
            <div class="line"></div>
            <button type="submit">Connexion</button>
          </form>
          
        </div>
      </div>
    </div>  
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  import { login } from '~/services/auth';
  

  const email = ref('');
  const password = ref('');
  
  // Accès au routeur
  const router = useRouter();
  
  // Afficher / masquer le mot de passe
  const togglePassword = () => {
    const passwordInput = document.getElementById('password');
    const type = passwordInput.getAttribute('type');
    passwordInput.setAttribute('type', type === 'password' ? 'text' : 'password');
  };
  

  const handleSubmit = async () => {
    if (!email.value || !password.value) {
      alert('Veuillez remplir tous les champs.');
      return;
    }
  
    try {
      await login(email.value, password.value);
  
      router.push('/security');
    } catch (error) {
      alert('Erreur lors de la connexion. Vérifiez vos identifiants.');
    }
  };
  
  definePageMeta({
    middleware: 'already-log', 
  });
  </script>

  
  <style scoped>
  
  .main_part {
    display: flex;
    background-color: #F2F4EF;
    width: 100vw;
    height: 100vh;
  }
  
  .left_part {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
  }
  
  .responsive-img {
    width: 50%;
    height: auto;
  }
  
  .right_part {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 80%;
    height: 100%;
    background-color: #F2F4EF;
  }
  
  .pos_white_card {
    border-radius: 10px;
    background-color: white;
    width: 90%;
    height: 95%;
    display: flex;
    flex-direction: column;
    padding: 10%;
    align-items: center;
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  }

  .logo_title_part{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  
  .logo {
    display: flex;
    margin: auto;
    align-content: center;
    margin-bottom: 1em;
    width: 115px;
    height: auto;
  }
  
  .title_part h1 {
    font-family: "Gilroy-Bold", sans-serif;
    font-size: clamp(1.8rem, 2.5vw, 4rem); 
    padding-left: 25px;
    text-align: center;
  }
  
  .title_part h6 {
    font-family: "Gilroy-Regular", sans-serif;
    color: #7B7B7B;
    font-size: clamp(1rem, 1.5vw, 1rem);
    margin-left: 18px;
    margin-top: 5px;
    text-align: center;
  }

  input::placeholder {
    font-size: clamp(0.8rem, 1.5vw, 1rem);
  }
  
  form {
    margin-top: 30%;
    display: flex; 
    flex-direction: column; 
    gap: 1rem; 
    width: 90%;
  }
  
  form input {
    border: none;
    outline: none;
    padding: 10px;
    font-size: 1rem;
    border-radius: 5px;
  }
  
  form input::placeholder {
    color: #7B7B7B;
  }
  
  .line {
    width: 100%;
    height: 1px;
    background-color: #E0E0E0;
  }
  
  .password-wrapper {
    position: relative;
    display: flex;
    align-items: center;
  }
  
  .password-wrapper input {
    width: 100%;
    padding-right: 2.5rem;
  }
  
  .eye-icon {
    position: absolute;
    right: 10px;
    bottom: 5px;
    cursor: pointer;
    width: 18px;
    height: 18px;
    opacity: 0.6;
  }
  
  .eye-icon:hover {
    opacity: 1;
  }

  button{
    margin: auto;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 15%;
    background-color: #151B11 ;
    color: white;
    width: 85%;
    height: 40px;
    border: none;
    border-radius: 30px;
    cursor: pointer;
    transition: transform 0.3s ease;
    transform-origin: center ;
    
  }

  button:hover{
    transform: scale(1.03);
  }
  
  @media screen and (max-width: 800px) {
      .left_part {
        display: none;
      }

      .right_part{
        width: 100%;
      }
  }
  </style>
  