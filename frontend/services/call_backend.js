

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

export async function fetchBackend2(url) {
    const url2 = url;
    const sessionCookie2 = useCookie('session');
    const token = sessionCookie2?.value;
    console.log("-------------------------------");
    console.log(url2);
    if (!token) {
        console.warn('Le token est manquant dans le cookie session.');
        throw new Error('Utilisateur non authentifié.');
    }

    try {
        const data2 = await $fetch(url2, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        console.log("-------------------------------");
        console.log(url2);
        return data2;
    } catch (error) {
        console.error('Erreur lors de la requête API :', error);
        throw error;
    }
}

export async function fetchBackendPost(url, body) {
    const sessionCookie2 = useCookie('session');
    const token = sessionCookie2?.value;

    if (!token) {
        console.warn('Le token est manquant dans le cookie session.');
        throw new Error('Utilisateur non authentifié.');
    }
    console.log(body.value);
    try {
        const data3 = await $fetch(url, {
            method: 'POST',
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(body),
        });
        c
        return data3;
    } catch (error) {
        console.error('Erreur lors de la requête API :', error);
        throw error;
    }
}