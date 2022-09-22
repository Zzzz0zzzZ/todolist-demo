import { createApp } from 'vue'
import App from './App.vue'
import BaiduCalendar from "vue-baidu-calendar"
import router from './router'
import pinia from '@/stores/store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap';

createApp(App).use(router).use(pinia).use(ElementPlus).use(BaiduCalendar).mount('#app')

window.addEventListener('storage', function(e) {
    localStorage.setItem(e.key, e.oldValue)
})