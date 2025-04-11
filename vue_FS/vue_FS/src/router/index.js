import { createRouter, createWebHashHistory  } from 'vue-router'
import HomeView from '../views/HomeView.vue' 
import ProjectsView from '@/views/ProjectsView.vue'
import AssignmentView from '@/views/AssignmentView.vue'



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
    {
      path: '/assignment',
      name: 'Asignación',
      component: AssignmentView,
    },
   
   
  ],
})

export default router
