
import { createProxyMiddleware } from 'http-proxy-middleware';


export default defineEventHandler((event) => {
  const runtimeConfig = useRuntimeConfig();
  const url_api = runtimeConfig.public.apibase;
  console.log(`Requête interceptée : ${event.node.req.url}`);

  const proxy = createProxyMiddleware('/api', {
    target: url_api,
    changeOrigin: true, 
    pathRewrite: { '^/api': '/api' },
    onProxyReq: (proxyReq) => {
      console.log(`[Proxy] Requête envoyée vers le backend : ${proxyReq.path}`);
    },
    onProxyRes: (proxyRes) => {
      console.log(`[Proxy] Réponse du backend : ${proxyRes.statusCode}`);
    },
  });
  console.log("Targete: ",url_api);
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
