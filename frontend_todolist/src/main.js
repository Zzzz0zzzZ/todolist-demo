import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import pinia from './stores/store'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap'
import './index.css'

const app = createApp(App)

app.use(router).use(pinia).mount('#app')
