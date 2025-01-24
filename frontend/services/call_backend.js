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