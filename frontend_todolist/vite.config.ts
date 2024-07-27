import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()]
    }),
    Components({
      resolvers: [ElementPlusResolver()]
    })
  ],
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
  },
  build: {
    rollupOptions: {
      output: {
        manualChunks: {
          vue: ['vue', 'vue-router', 'pinia'],
          utils: ['axios', 'dayjs'],
          ui: ['element-plus', '@element-plus/icons-vue']
        },
        assetFileNames: (chunk) => {
          if (/\.(png|jpg)$/.test(chunk.name)) {
            return 'imgs/[name][extname]'
          }
          return 'assets/[name]-[hash][extname]'
        }
      }
    }
  }
})
