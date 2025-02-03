import {getTokenString} from "~/services/SessionServices";

export class callAPI {
    /**
     * Méthode qui prend en paramètre une route au format string.
     * Le format peut être :
     * user/profil
     * user/profil/12
     * object/info?nom=egaren
     * video/stream?titre=film&sousTitre=auteur
     * @return valide ou non en fonction de la demande et de l'authentification
     * @param url
     */
    async fetchAPIGet(url:string) {
        return this.#fetchGet(url);
    }

    /**
     * Méthode pour faire les appels GET avec des pages, prend en paramètre la route, le numéro de page, ainsi que sa taille.
     * @param url au format string
     * @param page numéro de la page (de 0 à X)
     * @param size taille de la page (de 1 à X)
     */
    async fetchAPIGetPaginated(url:string, page:string, size:string) {
        return this.#fetchGet(`${url}/paginated?page=${page}&size=${size}`);
    }

    /**
     * Méthode pour faire les appels POST avec une route, un objet à sauvegarder.
     * @param url au format string
     * @param body objet à envoyer avec la requête
     */
    async fetchAPIPost(url:string, body:any) {
        return this.#fetchPostPut(`${url}`, body, 'POST');
    }

    /**
     * Méthode pour faire les appels POST avec une route, un identifiant et l'objet à sauvegarder.
     * @param url au format string
     * @param id identifiant de l'objet (user, rapport, event…)
     * @param body objet à envoyer avec la requête
     */
    async fetchAPIPostWithId(url:string, id:string, body:any) {
        return this.#fetchPostPut(`${url}/${id}`, body, 'POST');
    }

    /**
     * Méthode pour faire les appels PUT avec une route, un identifiant et l'objet à mettre à jour.
     * @param url au format string
     * @param id identifiant de l'objet (user, rapport, event…)
     * @param body objet à envoyer avec la requête
     */
    async fetchAPIPutWithId(url:string, id:string, body:any) {
        return this.#fetchPostPut(`${url}/${id}`, body, 'PUT');
    }

    /**
     * Méthode pour faire les appels DELETE avec une route et l'identifiant de l'objet.
     * @param url au format string
     * @param id identifiant de l'objet (user, rapport, event…)
     */
    async fetchAPIDelete(url:string, id:string) {
        try {
            const data = await $fetch(`/api/${url}/${id}`, {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${getTokenString()}`,
                },
            });
            return data;
        } catch (error) {
            console.error('Erreur lors de la requête API :', error);
            throw error;
        }
    }

    /**
     * Méthode interne à la classe pour faire les appels GET, elle permet de simplifier la classe.
     * @param url au format string
     */
    async #fetchGet(url:string) {
        try {
            const data = await $fetch("/api/" + url, {
                headers: {
                    Authorization: `Bearer ${getTokenString()}`,
                },
            });
            return data;
        } catch (error) {
            console.error('Erreur lors de la requête API :', error);
            throw error;
        }
    }

    /**
     * Méthode interne à la classe pour faire les appels POST et PUT, elle permet de simplifier la classe.
     * @param url au format string
     * @param body objet à envoyer avec la requête
     * @param methode à utiliser pour la requête, POST ou PUT
     */
    async #fetchPostPut(url:string, body:any, methode:any) {
        try {
            const data = await $fetch(`/api/${url}`, {
                method: methode,
                headers: {
                    Authorization: `Bearer ${getTokenString()}`,
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
}