import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

export default defineConfig(({ mode }) => {
  const target = loadEnv(mode, process.cwd()).VITE_API_TARGET
  return {
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
          target,
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
  }
})
