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

export async function UpdateBackend(url, id, payload) { 
    const sessionCookie = useCookie('session');
    const token = sessionCookie?.value;

    if (!token) {
        console.warn('Le token est manquant dans le cookie session.');
        throw new Error('Utilisateur non authentifié.');
    }
    try {
        const response = await $fetch(`${url}/${id}`, {
        method: 'PUT',
        headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
        });
        return response;
    } catch (error) {
        console.error('Erreur lors de la requête API :', error);
        throw error; 
    }
}

export async function PostBackend(url, id, payload) { 
    const sessionCookie = useCookie('session');
    const token = sessionCookie?.value;

    if (!token) {
        console.warn('Le token est manquant dans le cookie session.');
        throw new Error('Utilisateur non authentifié.');
    }
    try {
        const response = await $fetch(`${url}/${id}`, {
        method: 'POST',
        headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
        });
        return response;
    } catch (error) {
        console.error('Erreur lors de la requête API :', error);
        throw error; 
    }
}
