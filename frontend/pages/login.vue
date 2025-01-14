<template>
    <div class="main_part">    
      <div class="left_part">
        <img src="../assets/potager_login.png" alt="Image d'un potager" class="responsive-img">
      </div>
      <div class="right_part">
        <div class="pos_white_card">
          <div class="logo_title_part">
            <div class="logo_pos">
              <img class="logo" src="../assets/logo.png" alt="Logo">
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
                <img src="../assets/oeil.png" alt="Afficher/Cacher le mot de passe" class="eye-icon">
              </span>
            </div>
            <div class="line"></div>
            <button type="submit">Connexion</button>
          </form>
          
        </div>
      </div>
    </div>  
  </template>
  
  <script>
import { login } from '~/services/auth';

  export default {
    data() {
      return {
        email: '',
        password: '',
      };
    },
    methods: {
      togglePassword() {
        const passwordInput = document.getElementById('password');
        const type = passwordInput.getAttribute('type');
        passwordInput.setAttribute('type', type === 'password' ? 'text' : 'password');
      },
      async handleSubmit() {
      if (!this.email || !this.password) {
        alert('Veuillez remplir tous les champs.');
        return;
      }

      try {
        // Appel de la fonction login depuis auth.js
        const token = await login(this.email, this.password);

        // Affiche un message de succès
        alert('Connexion réussie !');

        // Redirection vers la page sécurisée
        this.$router.push('/security');
      } catch (error) {

        alert('Erreur lors de la connexion. Vérifiez vos identifiants.');
      }
    },
  },
  };
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
    width: 70%;
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
    width: 40%;
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
  
  .logo {
    display: flex;
    margin: auto;
    align-content: center;
    margin-bottom: 1em;
    width: 115px;
    height: auto;
  }
  
  .title_part h1 {
    font-family: "Gilroy-Bold";
    font-size: 2.5em;
    padding-left: 25px;
  }
  
  .title_part h6 {
    font-family: "Gilroy-Regular";
    color: #7B7B7B;
    font-size: 0.9em;
    margin-left: 18px;
    margin-top: 5px;
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
  
  </style>
  