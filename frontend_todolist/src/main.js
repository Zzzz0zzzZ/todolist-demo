import { createApp } from 'vue'
import App from './App.vue'
import BaiduCalendar from "vue-baidu-calendar"
import router from './router'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap';

createApp(App).use(router).use(createPinia()).use(ElementPlus).use(BaiduCalendar).mount('#app')
