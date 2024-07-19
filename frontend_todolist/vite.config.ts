import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 9974,
    proxy: {
      '/api/': {
        target: 'http://127.0.0.1:8060/',
        rewrite: (path) => path.replace(/^\/api/, ''),
        changeOrigin: true,
        ws: true
      }
    }
  },
  resolve: {
    alias: {
      '@': '/src'
    }
  }
})
