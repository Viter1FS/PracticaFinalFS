import { createRouter, createWebHashHistory  } from 'vue-router'
import HomeView from '../views/HomeView.vue' 
import ProjectsView from '@/views/ProjectsView.vue'


const router = createRouter({
  history: createWebHashHistory (import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/projects',
      name: 'Proyectos',
      component: ProjectsView,
    },
   
  ],
})

export default router
