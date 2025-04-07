

<template>
  <div>
    <h1>Empleados</h1>
    <ul>
      <li v-for="empleado in empleados" :key="empleado.id">
        {{ empleado.tx_nombre }} {{ empleado.tx_apellido1 }}
      </li>
    </ul>
  </div>
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