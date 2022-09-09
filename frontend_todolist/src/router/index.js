import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import calenderView from '../views/CalenderView.vue'
<<<<<<< HEAD
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import pinia from '@/stores/store'
import { countStore } from '@/stores/countStore'

const store = countStore(pinia)

const routes = [
  {
    path: '/todo/todolist/:userid',
=======

const routes = [
  {
    path: '/todo/',
>>>>>>> 92346e44a1242d4e91f4a0938f50d67ff2487026
    name: 'home',
    component: HomeView
  },
  {
    path: '/todo/calender/',
    name: 'calender',
    component: calenderView
<<<<<<< HEAD
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
=======
>>>>>>> 92346e44a1242d4e91f4a0938f50d67ff2487026
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

<<<<<<< HEAD
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
=======
>>>>>>> 92346e44a1242d4e91f4a0938f50d67ff2487026
export default router
