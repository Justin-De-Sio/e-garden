<template>
    <div class="wrapper_profil">
        <div class="header_log">
            <div class="logo">
                <img class="profil_logo" src="/public/assets/logo.png" alt="" >
            </div>
            <div class="exit">
                <button class="logout">Deconnexion</button>
                <button class="button_exit"><img class="exit_profil" src="/public/assets/cancel.png" alt="" width="20px" height="auto"></button>
            </div>
        </div>
        <div class="wrapper_form">
            <div class="text_header">
                <h2>Mon profil</h2>
                <h3>Gérez les paramètres de votre profil</h3>
            </div>
            <div class="header_profil">
                <h3>Votre photo de profil</h3>
                <img src="/public/assets/user.png" alt="">
            </div>
            <div class="formulaire">
                <form action="" method="get">
                    <div class="foreach" v-for="(param, index) in profil_params" :key="index">
                    <h4>{{ param.name }}</h4>
                    <input
                        :type="param.key === 'email' ? 'email' : 'text'"
                        :name="param.key"
                        :placeholder="param.placeholder"
                        :readonly="param.readonly || false"
                    />
                    </div>
                </form>
                </div>
            <div class="footer_button">
                <button>Supprimer votre compte</button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
    import { fetchBackend_URL } from '~/services/call_backend';

    const profil_params = ref([
        { key: 'surname', name: 'Prénom', placeholder: 'Gabin' },
        { key: 'name', name: 'Nom', placeholder: 'Cornaire' },
        { key: 'email', name: 'Adresse email', placeholder: 'Email', readonly: true },
        { key: 'class', name: 'Classe', placeholder: 'ING5' },
        { key: 'group', name: 'Groupe', placeholder: 'Grp3' },
    ]);
</script>

<style scoped>

    body{
        background-color: #f2f4ef
    }

    .wrapper_profil{
        display: flex;
        flex-direction: column;
        justify-content: flex-start;
        align-items: flex-start;
        width: 100%;
        height: auto;
    }

    .header_log{
        display: flex;
        justify-content: space-between;
        align-items: center;
        width: 100%;
        height: auto;
    }


    .profil_logo{
        width: min(20vw, 100px); 
        height: auto;
    }
    
    .exit{
        display: flex;
        gap: 1.3rem;

    }

    .button_exit{
        background: none;
        border: none;
        cursor: pointer;
    }

    .exit_profil{
        width: min(3vw, 20px); 
        height: auto;
        margin-right: min(5vw, 30px); 
        cursor: pointer;
    }

    .logout{
        border: none;
        color: white;
        height: 2.5rem;
        width: min(25vw, 150px); 
        border-radius: 0.3rem;
        background-color: #95bd75;
        font-family: "Gilroy-Medium";
        font-size: clamp(0.7rem, 2vw, 0.9rem); 
        cursor: pointer;
    }

    /* =========================
                BODY 
    ===========================*/

    .text_header{
        display: flex;
        flex-direction: column;
        justify-content: flex-start; 
        gap: 0.5rem;   
    }

    .text_header h2{
        font-family: "Gilroy-Regular";
        font-size: clamp(1.3rem, 2.5vw, 2rem); 
    }

    .text_header h3{
        font-family: "Gilroy-Regular";
        font-size: clamp(0.8rem, 1.5vw, 1rem); 
        color: #909399;
    }

    
    .wrapper_form{
        display: flex;
        flex-direction: column;
        margin-left: 10rem;
        margin-top: 3rem;
    }

    .header_profil{
        display: flex;
        flex-direction: column;
        align-items: flex-start;
        justify-content: center;
        margin-top: 3rem;
        gap: 1rem;
    }

    .header_profil h3{
        font-family: "Gilroy-Medium";
        font-size: clamp(0.9rem, 2.5vw, 1.3rem);
    }

    .header_profil img{
        width: 4rem;
    }

    .formulaire form{
        margin-top: 2rem;
        display: flex;
        flex-direction: column;
        gap: 1rem;
        width: 100%;
    }

    .formulaire form input {
        width: 100%;
        height: 2.2rem;
        padding-left: 1rem;
        border: solid 2px #EEEEEE
    
    }

    .formulaire form input::placeholder {
        font-family: "Gilroy-Medium";
        color: black;
    }

    .footer_button{
        margin: auto;
        margin-top: 5rem;
    }

    .footer_button button{
        background: none;
        background-color: #e39695;
        border: none;
        width: min(30vw, 200px); 
        height: 2.5rem;
        border-radius: 0.3rem;
        color: white;
        font-family: "Gilroy-Medium";
        font-size: clamp(0.7rem, 2vw, 0.9rem); 
        cursor: pointer;
    }

    @media screen and (max-width: 800px) {

        .wrapper_form{
            margin: auto;
            margin-top: 2.5em;
            justify-content: center;
            align-items: center;
        }

        .text_header{
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;    
        }
  }

</style>