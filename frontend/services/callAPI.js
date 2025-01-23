export class callAPI {
    async fetchAPIGet(url) {
        return this.#fetchGet(url);
    }

    async fetchAPIGetPaginated(url, page, size) {
        return this.#fetchGet(`${url}/paginated?page=${page}&size=${size}`);
    }

    async fetchAPIPost(url, body) {
        return this.#fetchPostPut(`${url}`, body, 'POST');
    }

    async fetchAPIPostWithId(url, id, body) {
        return this.#fetchPostPut(`${url}/${id}`, body, 'POST');
    }

    async fetchAPIPut(url, body) {
        return this.#fetchPostPut(`${url}`, body, 'PUT');
    }

    async fetchAPIPutWithId(url, id, body) {
        return this.#fetchPostPut(`${url}/${id}`, body, 'PUT');
    }

    async fetchAPIDelete(url, id) {
        try {
            const data = await $fetch(`/api/${url}/${id}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${this.#getSession()}`,
                },
            });
            return data;
        } catch (error) {
            console.error('Erreur lors de la requête API :', error);
            throw error;
        }
    }

    async #fetchGet(url) {
        try {
            const data = await $fetch("/api/" + url, {
                headers: {
                    Authorization: `Bearer ${this.#getSession()}`,
                },
            });
            return data;
        } catch (error) {
            console.error('Erreur lors de la requête API :', error);
            throw error;
        }
    }

    async #fetchPostPut(url, body, methode) {
        try {
            const data = await $fetch(`/api/${url}`, {
                method: methode,
                headers: {
                    Authorization: `Bearer ${this.#getSession()}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(body)
            });
            return data;
        } catch (error) {
            console.error('Erreur lors de la requête API :', error);
            throw error;
        }
    }

    #getSession() {
        const sessionCookie = useCookie('session');
        const token = sessionCookie?.value;
        if (!token) {
            console.warn('Le token est manquant dans le cookie session.');
            throw new Error('Utilisateur non authentifié.');
        }
        return token;
    }
}