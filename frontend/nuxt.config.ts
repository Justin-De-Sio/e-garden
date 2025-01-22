import {config} from 'dotenv'
config();
export default defineNuxtConfig({
  css: [
    '/public/assets/css/reset.css', // Inclure le fichier CSS global
  ],
  modules:[
    '@nuxt/ui'
  ],
  runtimeConfig: {
    public: {
      apibase: process.env.API_URL || 'thomas',
    },
  },
  colorMode:{
    preference: "light",
  },
  compatibilityDate: '2025-01-20',
});