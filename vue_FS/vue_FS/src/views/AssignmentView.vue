<template>
  <v-container fluid class="pa-4 fill-width">
    <div class="container-main">
      <v-card class="pa-4" elevation="2">
        <v-card-title>Asignar Empleados a Proyectos</v-card-title>

        <v-select
          v-model="proyectoSeleccionado"
          :items="proyectosActivos"
          :item-title="(item)=>`${item.id_proyecto} - ${item.tx_descripción}`"
          label="Selecciona un proyecto"
          class="mb-4"
        />
        <h3>{{ empleadosConAsignado }}</h3>
        <v-data-table
          :items="empleadosConAsignado"
          :headers="headers"
          item-value="id"
          class="elevation-1"
        >
        <template v-slot:[`item.asignado`]="{ item }">  
            <v-checkbox
              v-model="item.asignado"
              @change="onCheckboxChange(item)"
              hide-details
            ></v-checkbox>
          </template>

        </v-data-table>
      </v-card>
    </div>
  </v-container>
</template>

<script>
import axios from "axios";
import "font-awesome/css/font-awesome.min.css";

export default {
  data() {
    return {
      empleados: [],
      proyectos_options: [],
      proyectoSeleccionado: null,
      asignaciones: {},
      headers: [
        { title: "Nombre", value: "tx_nombre" },
        { title: "Apellidos", value: "tx_apellido1" },
        { title: "Asignado", value: "asignado", sortable: false },
      ]
    };
  },
  computed: {
    proyectosActivos() {
      return this.proyectos_options.filter(p => !p.f_baja);
    },
    empleadosActivos() {
      return this.empleados.filter(e => !e.f_baja);
    },
    empleadosConAsignado() {
      if (!this.proyectoSeleccionado) return [];



      

      return this.empleadosActivos.map((emp) => {
        const estaAsignado = emp.id_proyecto.includes(this.proyectoSeleccionado);
        return {
          ...emp,
          asignado: estaAsignado
        };
      });
    }
  },
  watch: {
    proyectoSeleccionado(nuevoProyectoId) {
      if (!nuevoProyectoId) return;
      this.asignaciones = {};
      this.empleadosActivos.forEach((empleado) => {
        const estaAsignado = empleado.id_proyecto.includes(nuevoProyectoId);
        this.asignaciones[empleado.id_empleado] = estaAsignado;
      });
      

    }
  },
  created() {
    this.fetchEmpleados_proyectos();
  },
  methods: {
    toggleAsignacion(empleadoId) {
      const asignado = this.asignaciones[empleadoId];
      const proyectoId = this.proyectoSeleccionado;

      const empleado = this.empleados.find(e => e.id_empleado === empleadoId);

      if (asignado) {
        if (!empleado.id_proyecto.includes(proyectoId)) {
          empleado.id_proyecto.push(proyectoId);
        }
      } else {
        empleado.id_proyecto = empleado.id_proyecto.filter(id => id !== proyectoId);
      }
    },

    async fetchEmpleados_proyectos() {
      try {
        const response_emp = await axios.get("http://localhost:8080/empleados/all");
        const response_proyects = await axios.get("http://localhost:8080/proyectos/all");

        this.proyectos_options = response_proyects.data;

        this.empleados = response_emp.data.map(emp => ({
          ...emp,
          id_proyecto: emp.proyectosEmpleados.map(
            item => item.id_pr_empleados_proyecto.id_proyecto
          ),
          
        }));

      } catch (error) {
        console.error("Error al obtener datos de la API", error);
      }
    }
  }
};
</script>


<style scoped>
.container-main {
  position: relative;
  width: 100%;
  max-width: 1000px;
  margin: 0, auto;
  margin-right: 35vh;
  margin-left: 18vh;
  padding: 5px;
  box-sizing: border-box;
  display: block;
}
</style>
