import {config} from 'dotenv'
config();
export default defineNuxtConfig({
  css: [
    '/public/assets/css/reset.css', // Inclure le fichier CSS global
  ],
  modules:['@nuxt/ui', 'nuxt-time'],
  runtimeConfig: {
    public: {
      apibase: process.env.API_URL || 'thomas',
    },
  },
  colorMode:{
    preference: "light"
  },
  ui: {
    safelistColors: ['brandPrimary']
  },
  app: {
    head: {
      meta: [
        { name: 'viewport', content: 'width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' }
      ]
    }
  },


  compatibilityDate: '2025-01-20',
});