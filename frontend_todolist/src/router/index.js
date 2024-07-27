import { createRouter, createWebHistory } from 'vue-router'
import axios from 'axios'

const routes = [
  {
    path: '/todo/todolist/:userid',
    name: 'home',
    component: () => import('@/views/HomeView.vue')
  },
  {
    path: '/todo/login/',
    name: 'login',
    component: () => import('@/views/LoginView.vue')
  },
  {
    path: '/todo/register/',
    name: 'register',
    component: () => import('@/views/RegisterView.vue')
  },
  {
    path: '/todo/change_password/',
    name: 'change_password',
    component: () => import('@/views/ChangePasswordView.vue')
  },
  {
    path: '/todo/404/',
    name: '404',
    component: () => import('@/views/NotFoundView.vue')
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

router.beforeEach((to, from, next) => {
  if (to.path === '/todo/register' || to.path === '/todo/login/') {
    localStorage.removeItem('token')
    localStorage.removeItem('userid')
    localStorage.removeItem('username')
    next()
  } else {
    const token = localStorage.getItem('token')
    if (token === null || token === '') {
      next('/todo/login/')
    } else {
      axios({
        method: 'POST',
        url: '/api/checkToken',
        data: {
          token: token
        }
      }).then((res) => {
        if (res.data === true) {
          next()
        } else {
          next('/todo/login/')
        }
      })
    }
  }
})
export default router
