import { createRouter, createWebHistory } from 'vue-router'
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
  history: createWebHistory(),
  routes
})

export default router
