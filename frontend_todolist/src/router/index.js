import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import calenderView from '../views/CalenderView.vue'
import LoginView from '../views/LoginView.vue'
import RegisterView from '../views/RegisterView.vue'
import ChangePasswordView from '../views/ChangePasswordView'

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
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path !== '/todo/register' && to.path !== '/todo/login') {
    if (sessionStorage.getItem("islogin") === "true") {
      next()
    } else if (to.path === '/todo/register') {
      next('/todo/register')
    } else {
      next('/todo/login')
    }
  } else {
    next()
  }
})
export default router
