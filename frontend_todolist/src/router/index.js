import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import calenderView from '../views/CalenderView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import pinia from '@/stores/store'
import { countStore } from '@/stores/countStore'

const store = countStore(pinia)

const routes = [
  {
    path: '/todo/todolist/:userid',
    name: 'home',
    component: HomeView
  },
  {
    path: '/todo/calender/',
    name: 'calender',
    component: calenderView
  },
  {
    path: '/todo/login/',
    name: 'login',
    component: LoginView
  },
  {
    path: '/todo/register/',
    name: 'register',
    component: RegisterView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path !== '/todo/register' && to.path !== '/todo/login' && store.login_status === false) {
    store.login_retry = true
    if (to.path === '/todo/register') {
      next('/todo/register')
    } else {
      next('/todo/login')
    }
  } else {
    next()
  }
})
export default router
