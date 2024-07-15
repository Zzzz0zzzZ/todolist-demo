import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    '/api/': {
      target: 'http://127.0.0.1:8060/',
      rewrite: (path) => path.replace(/^\/api/, '/api'),
      changeOrigin: true,
      ws: true
    }
  },
  resolve: {
    alias: {
      '@': '/src'
    }
  }
})
