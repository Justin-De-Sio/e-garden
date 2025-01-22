export class callAPI {
    async fetchAPIGet(url) {
        try {
            const data = await $fetch(url, {
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

    async fetchAPIGetPaginated(url, page, size) {
        try {
            const data = await $fetch(`${url}paginated?page=${page}&size=${size}`, {
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

    async fetchAPIPost(url, body) {
        try {
            const data = await $fetch(`${url}`, {
                method: 'POST',
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

    async fetchAPIPostWithId(url, id, body) {
        try {
            const data = await $fetch(`${url}/${id}`, {
                method: 'POST',
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

    async fetchAPIPut(url, body) {
        try {
            const data = await $fetch(`${url}`, {
                method: 'PUT',
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

    async fetchAPIPutWithId(url, id, body) {
        try {
            const data = await $fetch(`${url}/${id}`, {
                method: 'PUT',
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

    async fetchAPIDelete(url, id) {
        try {
            const data = await $fetch(`${url}/${id}`, {
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