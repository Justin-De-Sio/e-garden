<template>
    <div class="main_wrapper">
        <div class="header_log">
                <div class="logo">
                    <img class="profil_logo" src="/assets/logo.png" alt="">
                </div>
                <div class="exit">
                    <button class="button_exit" @click="goBack">
                        <img class="exit_profil" src="/assets/cancel.png" alt="" width="20px" height="auto" />
                    </button>
                </div>
            </div>
        <div class="wrapper_profil">
            
            <div class="wrapper_form">
                <div class="text_header">
                    <h2>Changer votre mot de passe</h2>
                    <h3>Veuillez remplir les champs ci-dessous</h3>
                </div>
                <div class="header_profil">
                    <h3>Votre photo de profil</h3>
                    <img src="public/assets/user.png" alt="">
                </div>
                <div class="formulaire">
                    <form @submit.prevent="submitForm">
                        <div v-for="(param, index) in password_params" :key="index">
                            <h4 class="name_input">{{ param.name }}</h4>
                            <input
                                :type="param.type"
                                :name="param.key"
                                v-model="param.value"
                            />
                        </div>
                        <button class="save_button" type="submit">Enregistrer</button>
                    </form>
                </div>
            </div>
            <div class="illustration">
                <img src="/public/assets/mdp-illustration.png" alt="">
            </div>
        </div>

    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { callAPI } from "~/services/callAPI";

const api = new callAPI();
const userId = ref()
onMounted(async () => {
    try {
        const data = await api.fetchAPIGet('user/profil');
        userId.value = data.id; 
    } catch (error) {
        console.error('Erreur lors du chargement des données du profil :', error);
    }
});

interface PasswordParam {
    key: string;
    name: string;
    type: string;
    value: string;
}

// Tableau contenant les paramètres des champs de mot de passe
const password_params = ref<PasswordParam[]>([
    { key: 'currentPassword', name: 'Mot de passe actuel', type: 'password', value: '' },
    { key: 'newPassword', name: 'Nouveau mot de passe', type: 'password', value: '' },
    { key: 'confirmNewPassword', name: 'Confirmez le mot de passe', type: 'password', value: '' },
]);



const submitForm = async()  => {
    const passwordPayload = password_params.value.reduce((acc, param) => {
        acc[param.key] = param.value;
        return acc;
    }, {} as Record<string, string>);
    try {
        const response = await api.fetchAPIPostWithId('user/changePassword', userId.value, passwordPayload);
        console.log('Profil mis à jour avec succès :', response);

    } catch (error) {
        console.error('Erreur lors de la mise à jour du profil :', error);
    }
    console.log('Payload:', passwordPayload);
    alert('Formulaire soumis avec succès !');
};
const router = useRouter();
const goBack = () => {
    router.back();
};

definePageMeta({
    middleware: "auth",
    role: ["ADMINISTRATEUR","UTILISATEUR"],
  })
</script>

<style scoped>

    body{
        background-color: #f2f4ef
    }
    .main_wrapper {
        display: flex;
        flex-direction: column;
        justify-content: space-between; 
        align-items: center; 
        width: 100%; 
        padding: 1rem; 
        box-sizing: border-box; 
    }

    .wrapper_profil{
        display: flex;
        flex-direction: row;
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
        background-color: #ffbf69;
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

    .name_input {
        font-family: "Gilroy-Regular";
        font-size: 0.9rem;
        color: #909399;
    
    }

    .formulaire form input::placeholder {
        font-family: "Gilroy-Medium";
        color: black;
    }

    .footer_button{
        margin: auto;
        margin-top: 4rem;
    }

    .footer_button p{
        background: none;
        border: none;
        width: min(35vw, 200px); 
        height: 2.5rem;
        border-radius: 0.3rem;
        color: black;
        font-family: "Gilroy-Regular";
        font-size: clamp(0.7rem, 2vw, 0.9rem); 
        cursor: pointer;
        text-decoration: underline;
        font-style: italic;
    }

    .save_button{
        background: none;
        border: none;
        background-color: #95bd75;
        color: white;
        width: min(25vw, 200px);
        font-family: "Gilroy-Medium";
        font-size: clamp(0.7rem, 2vw, 0.8rem); 
        border-radius: 0.2rem;
        height: 2rem;
        margin: auto;
        cursor: pointer;
    }

    .save_button:disabled {
        background-color: #cccccc; 
        color: #666666;           
        cursor: not-allowed;      
        opacity: 0.6;             
    }
    .illustration{
        margin: auto;
    }

    .illustration img{
        width: min(25vw, 600px);
    }

    @media screen and (max-width: 900px) {

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

        .header_profil{
            align-items: center;
        }

        .illustration{
            display: none;
        }

  }

</style>