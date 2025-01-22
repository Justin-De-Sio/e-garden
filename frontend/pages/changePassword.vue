<template>
    <div class="wrapper_password">
        <div class="header_profil">
            <h3>Gabin Cornaire</h3>
            <img src="/assets/user.png" alt="User Image">
        </div>
        <div class="placement_forms">
            <p>Veuillez remplir les champs ci-dessous</p>
        </div>
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
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import { fetchBackend_URL, PostBackend } from '~/services/call_backend';

const userId = ref()
onMounted(async () => {
    try {
        const data = await fetchBackend_URL('/api/user/profil');
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
        const response = await PostBackend('/api/user/changePassword', userId.value, passwordPayload);
        console.log('Profil mis à jour avec succès :', response);

    } catch (error) {
        console.error('Erreur lors de la mise à jour du profil :', error);
    }
    console.log('Payload:', passwordPayload);
    alert('Formulaire soumis avec succès !');
};
</script>

<style scoped>

</style>