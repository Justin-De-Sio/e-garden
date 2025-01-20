
import { createProxyMiddleware } from 'http-proxy-middleware';

export default defineEventHandler((event) => {
  console.log(`Requête interceptée : ${event.node.req.url}`);

  const proxy = createProxyMiddleware('/api', {
    target: 'http://localhost:8080', // Adresse du backend
    changeOrigin: true, // Modifie l'origine pour éviter les problèmes CORS
    pathRewrite: { '^/api': '/api' }, // Facultatif : Réécrit les chemins si nécessaire
    onProxyReq: (proxyReq) => {
      console.log(`[Proxy] Requête envoyée vers le backend : ${proxyReq.path}`);
    },
    onProxyRes: (proxyRes) => {
      console.log(`[Proxy] Réponse du backend : ${proxyRes.statusCode}`);
    },
  });

  return new Promise((resolve, reject) => {
    proxy(event.node.req, event.node.res, (err) => {
      if (err) {
        console.error('Erreur du middleware proxy :', err);
        reject(err);
      } else {
        resolve();
      }
    });
  });
});
