import {config} from 'dotenv'
config();
export default defineNuxtConfig({
  css: [
    '~/assets/css/reset.css', // Inclure le fichier CSS global
  ],

  runtimeConfig: {
    public: {
      apibase: process.env.API_URL || 'thomas',
    },
  },

  compatibilityDate: '2025-01-20',
});