<template>
    <div class="wrapper_profil">
        <div class="header_log">
            <div class="logo">
                <img class="profil_logo" src="/assets/logo.png" alt="">
            </div>
            <div class="exit">
                <button class="logout" @click="logout">Deconnexion</button>
                <button class="button_exit" @click="goBack">
                    <img class="exit_profil" src="/assets/cancel.png" alt="" width="20px" height="auto" />
                </button>
            </div>
        </div>
        <div class="wrapper_form">
            <div class="text_header">
                <h2>Mon profil</h2>
                <h3>Gérez les paramètres de votre profil</h3>
            </div>
            <div class="header_profil">
                <h3>Votre photo de profil</h3>
                <img src="public/assets/user.png" alt="">
            </div>
            <div class="formulaire">
                <form @submit.prevent="submitForm">
                    <div class="foreach" v-for="(param, index) in profil_params" :key="index">
                        <h4 class="name_input">{{ param.name }}</h4>
                        <input
                            :type="param.key === 'email' ? 'email' : 'text'"
                            :name="param.key"
                            v-model="param.value"
                            :readonly="param.readonly || false"
                        />
                    </div>
                    <button class="save_button" type="submit" :disabled="!isModified">Enregistrer</button>
                </form>
            </div>
            <div class="footer_button">
                <p @click="changePassword">Changer de mot de passe</p>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { fetchBackend_URL, UpdateBackend } from '~/services/call_backend';
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';

// Définir un type pour l'utilisateur
interface User {
  id: string;
  name: string;
  surname: string;
  email: string;
  className: string;
  groupNumber: string;
}

interface ProfilParam {
  key: keyof User;
  name: string;
  value: string;
  originalValue: string;
  readonly?: boolean;
}


const router = useRouter();
const goBack = () => {
    router.back();
};

const logout = () =>{
    document.cookie="session=;"
    router.push("/login");
}

// Paramètres du profil et ID utilisateur
const profil_params = ref<ProfilParam[]>([]);
const userId = ref("");

// Propriété calculée pour vérifier si un champ a été modifié
const isModified = computed(() =>
    profil_params.value.some(param => param.value !== param.originalValue)
);

// Charger les données du profil à l'initialisation
onMounted(async () => {
    try {
        const data = await fetchBackend_URL('/api/user/profil');
        userId.value = data.id; 
        profil_params.value = [
            { key: 'name', name: 'Prénom', value: data.name, originalValue: data.name },
            { key: 'surname', name: 'Nom', value: data.surname, originalValue: data.surname },
            { key: 'email', name: 'Adresse email', value: data.email, originalValue: data.email, readonly: true },
            { key: 'className', name: 'Classe', value: data.className, originalValue: data.className },
            { key: 'groupNumber', name: 'Groupe', value: data.groupNumber, originalValue: data.groupNumber },
        ];
    } catch (error) {
        console.error('Erreur lors du chargement des données du profil :', error);
    }
});


const submitForm = async () => {

    // Construire l'objet User attendu par le backend
    const userPayload: User = profil_params.value.reduce((acc, param) => {
        acc[param.key] = param.value;
        return acc;
    }, {id: userId.value } as User);
    console.log(userPayload);
    try {
        const response = await UpdateBackend('/api/user/profil/', userId.value, userPayload);
        console.log('Profil mis à jour avec succès :', response);

        // Mettre à jour les valeurs originales après sauvegarde réussie
        profil_params.value.forEach(param => {
            param.originalValue = param.value;
        });
    } catch (error) {
        console.error('Erreur lors de la mise à jour du profil :', error);
    }
};

    const changePassword =  () => {
        router.push("/changePassword");
    }; 
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

        .header_profil{
            align-items: center;
        }

  }

</style>