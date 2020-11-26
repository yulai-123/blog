import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/category',
    name: 'category',
    component: () => import('../views/Category.vue')
  },
  {
    path: '/reorganize',
    name: 'reorganize',
    component: () => import('../views/Reorganize.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/Register')
  },
  {
    path: '/write',
    name: 'write',
    component: () => import('../views/Write')
  },
  {
    path: '/article/view/:id',
    name: 'view',
    component: () => import('../views/ArticleView')
  },
  {
    path: '/category/view/:id',
    name: 'categoryView',
    component: () => import('../views/CategoryView')
  },
  {
    path: '/article/revise/:id',
    name: 'revise',
    component: () => import('../views/Revise')
  }

]

const router = new VueRouter({
  routes
})

export default router
