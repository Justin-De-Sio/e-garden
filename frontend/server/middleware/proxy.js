import { createProxyMiddleware } from 'http-proxy-middleware';
import cookie from 'cookie';

export default defineEventHandler((event) => {
  const runtimeConfig = useRuntimeConfig();
  const url_api = runtimeConfig.public.apibase;

  const proxy = createProxyMiddleware('/api', {
    target: url_api,
    changeOrigin: true,
    pathRewrite: { '^/api': '/api' },
    onProxyReq: (proxyReq, req, res) => {
      // Parse cookies from the request header
      if (req.headers.cookie) {
        const cookies = cookie.parse(req.headers.cookie);
        // Use the cookie name "session" instead of "jwtToken"
        const token = cookies.session;
        if (token) {
          // Attach the token in the Authorization header with the Bearer scheme.
          proxyReq.setHeader('Authorization', `Bearer ${token}`);
        }
      }
      console.log(`[Proxy] Request sent to backend: ${proxyReq.path}`);
    },
    onProxyRes: (proxyRes, req, res) => {
      console.log(`[Proxy] Response from backend: ${proxyRes.statusCode}`);
    },
  });

  return new Promise((resolve, reject) => {
    proxy(event.node.req, event.node.res, (err) => {
      if (err) {
        console.error('Proxy middleware error:', err);
        reject(err);
      } else {
        resolve();
      }
    });
  });
});
