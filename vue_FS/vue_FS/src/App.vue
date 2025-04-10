

<template>
  <v-app>
    <v-main>

      <v-app-bar app color="primary" dark>
        <v-toolbar-title>Future Space</v-toolbar-title>

        <v-spacer />
        <div class="links-container">
          <RouterLink to="/" class="home-link">Empleados</RouterLink>
          <RouterLink to="/projects" class="home-link">Proyectos</RouterLink>
          <RouterLink to="/" class="home-link">Asignar Empleados</RouterLink>
        </div>
      </v-app-bar>


      <v-container fluid class="content-container">
        <RouterView />

      </v-container>
    </v-main>
  </v-app>
</template>


<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<style scoped>



.links-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px; /* Espacio entre los enlaces */
  position: absolute;
  width: 100%;
}

.home-link {
  color: white;
  text-decoration: none;
  
}

.content-container {
  flex: 1; /* Esto hace que ocupe todo el espacio disponible */
  display: flex;
  justify-content: center; /* Alinear el contenido */
  align-items: center; /* Centrar el contenido */
  padding: 16px;
}

</style>
