import Vue from 'vue'
import Router from 'vue-router'
import mainPage from '@/components/main-page'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'main-page',
      component: mainPage
    }
  ]
})
