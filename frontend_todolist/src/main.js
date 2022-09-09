import { createApp } from 'vue'
import App from './App.vue'
import BaiduCalendar from "vue-baidu-calendar"
import router from './router'
<<<<<<< HEAD
import pinia from '@/stores/store'
=======
import { createPinia } from 'pinia'
>>>>>>> 92346e44a1242d4e91f4a0938f50d67ff2487026
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap';

<<<<<<< HEAD
createApp(App).use(router).use(pinia).use(ElementPlus).use(BaiduCalendar).mount('#app')
=======
createApp(App).use(router).use(createPinia()).use(ElementPlus).use(BaiduCalendar).mount('#app')
>>>>>>> 92346e44a1242d4e91f4a0938f50d67ff2487026
