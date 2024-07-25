import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import pinia from './stores/store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap'
import './index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use(router).use(pinia).use(ElementPlus).mount('#app')

window.addEventListener('storage', function (e) {
  localStorage.setItem(e.key, e.oldValue)
})
