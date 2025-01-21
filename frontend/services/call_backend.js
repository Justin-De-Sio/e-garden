import { useCookie } from '#app';

export async function fetchBackend(url, page, size) {
    const url_complete = `${url}paginated?page=${page}&size=${size}`;  
    const sessionCookie = useCookie('session');
    const token = sessionCookie?.value;

    if (!token) {
        console.warn('Le token est manquant dans le cookie session.');
        throw new Error('Utilisateur non authentifié.');
    }

    try {
        const data = await $fetch(url_complete, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
        });
        return data;
    } catch (error) {
        console.error('Erreur lors de la requête API :', error);
        throw error; 
    }
}

export async function fetchBackend_URL(url) { 
    const sessionCookie = useCookie('session');
    const token = sessionCookie?.value;

    if (!token) {
        console.warn('Le token est manquant dans le cookie session.');
        throw new Error('Utilisateur non authentifié.');
    }

    try {
        const data = await $fetch(url, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
        });
        return data;
    } catch (error) {
        console.error('Erreur lors de la requête API :', error);
        throw error; 
    }
}
