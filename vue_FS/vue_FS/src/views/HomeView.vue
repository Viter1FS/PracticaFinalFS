

<template>
  <v-container >
  <div class="container">
    <h1>Empleados</h1>
    <v-text-field
      v-model="search"
      label="Buscar"
      class="mb-4 search-field"
      prepend-inner-icon="mdi-magnify"
      full-width  
    />
    </div>

    <ul>
      <li v-for="empleado in empleados" :key="empleado.id">
        {{ empleado.tx_nombre }} {{ empleado.tx_apellido1 }}
      </li>
    </ul>
  
  </v-container>
</template>


<script>
import axios from 'axios';

export default {
  data() {
    return {
      empleados: [],
    };
  },
  created() {
    this.fetchEmpleados();
  },

  methods: {
    async fetchEmpleados() {
      try {
        const response = await axios.get('http://localhost:8080/empleados/all');
        this.empleados = response.data;
      } catch (error) {
        console.error("Error al obtener los empleados", error);
      }
    },
  },
};
</script>

<style scoped>


.mb-4 {
  margin-bottom: 16px; /* Esto es solo para un pequeño espaciado */
}

@media (min-width: 1024px) {
.main-container {
  
  max-width: 100%; 
}

.search-field{
  margin-left: -200%;
  position: absolute;
  width: 100%;
  
}

}

</style>