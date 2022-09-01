import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import calenderView from '../views/CalenderView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/calender/',
    name: 'calender',
    component: calenderView
  }

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
