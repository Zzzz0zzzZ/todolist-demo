import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import calenderView from '../views/CalenderView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import ChangePasswordView from '../views/ChangePasswordView'
import NotFoundView from '../views/NotFoundView';
import axios from 'axios'
import pinia from '@/stores/store'
import { countStore } from '@/stores/countStore'

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
  },
  {
    path: '/todo/change_password/',
    name: 'change_password',
    component: ChangePasswordView
  },
  {
    path: '/todo/404/',
    name: '404',
    component: NotFoundView
  },
  {
    path: '/:catchAll(.*)',
    redirect: '/todo/404/'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

const store = countStore(pinia)
router.beforeEach((to, from, next) => {
  if (to.path === '/todo/register' || to.path === '/todo/login') {
    localStorage.removeItem("token")
    store.username = null
    store.userid = null
    next()
  } else {
    let token = localStorage.getItem("token")
    if (token === null || token === '') {
      next('/todo/login')
    } else {
      axios({
        method: 'POST',
        url: '/api/checkToken',
        data: {
          "token": token
        }
      }).then(res => {
        if (res.data === true) {
          next()
        } else {
          next('/todo/login')
        }
      })
    }
  }
})
export default router
