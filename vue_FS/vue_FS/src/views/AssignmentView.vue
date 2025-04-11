<template>
  <v-container fluid class="pa-4 fill-width">
    <div class="container-main">
      <v-card class="pa-4" elevation="2">
        <v-card-title>Asignar Empleados a Proyectos</v-card-title>
        <v-select v-model="proyectoSeleccionado" :items="proyectosActivos"
          :item-title="(item) => `${item.id_proyecto} - ${item.tx_descripción}`" item-value="id_proyecto"
          label="Selecciona un proyecto" class="mb-4" />

        <!-- <h3>{{ empleadosConAsignado }}</h3> -->
        <v-data-table :items="empleadosConAsignado" :headers="headers" item-value="id" class="elevation-1">
          <template v-slot:[`item.asignado`]="{ item }">
            <v-checkbox v-model="item.asignado" @change="onCheckboxChange(item)" hide-details>
            </v-checkbox>
          </template>

        </v-data-table>
        <!-- Botón flotante para agregar -->
        <div class="mt-4 d-flex justify-end">
          <v-btn icon color="primary" class="save-changes-btn" fab @click="recogerEmpleadosSeleccionados()" style="">
            <v-icon>mdi-floppy</v-icon>
          </v-btn>
        </div>
      </v-card>

    </div>
    <!-- Snackbar de confirmación -->
    <v-snackbar v-model="snackbar" :timeout="3000" color="success">
      {{ snackbarText }}
    </v-snackbar>
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
      snackbar: false, 
      snackbarText: "",
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
        //console.log(`Empleado: ${emp.tx_nombre} | Proyectos: ${emp.id_proyecto} | Proyecto seleccionado: ${this.proyectoSeleccionado} | Asignado: ${estaAsignado}`);
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
    onCheckboxChange(item) {
      // Ahora puedes acceder directamente a item para actualizar su estado
      const asignado = item.asignado;
      const proyectoId = this.proyectoSeleccionado;

      // Encontramos el empleado correspondiente
      const empleado = this.empleados.find(e => e.id_empleado === item.id_empleado);

      // Si el empleado está asignado, agregamos el proyecto
      if (asignado) {
        if (!empleado.id_proyecto.includes(proyectoId)) {
          empleado.id_proyecto.push(proyectoId);
        }
      } else {
        // Si no está asignado, eliminamos el proyecto
        empleado.id_proyecto = empleado.id_proyecto.filter(id => id !== proyectoId);
      }
    },
    recogerEmpleadosSeleccionados(id_proyecto_selecionado) {
      const empleadosSeleccionados = this.empleadosConAsignado
        .filter(emp => emp.asignado)  // Filtra los empleados asignados
        .map(emp => emp.id_empleado);  // Devuelve solo los id_empleados

      const empleadosDeseleccionados = this.empleadosConAsignado
        .filter(emp => !emp.asignado)  // Filtra los empleados no asignados
        .map(emp => emp.id_empleado);  // Devuelve solo los id_empleados

      console.log('Empleados seleccionados:', empleadosSeleccionados);
      console.log('Empleados deseleccionados:', empleadosDeseleccionados);
      console.log("Proyecto:", this.proyectoSeleccionado);
      // Aquí puedes hacer lo que necesites con los ids, como enviarlos a la API, o almacenarlos en un estado
      this.asignarEmpleados(empleadosSeleccionados,empleadosDeseleccionados,this.proyectoSeleccionado)

      
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
    },
    async asignarEmpleados(empleadosSeleccionados, empleadosDeseleccionados, proyectoId) {


      try {
        if (empleadosSeleccionados.length > 0) {
        const response_ae = await axios.post(`http://localhost:8080/empleado_proyecto/${proyectoId}/assignProject`, 
          empleadosSeleccionados,{
            headers: {
              'Content-Type': 'application/json'
            }
          }
        );
        }
        
        if (empleadosDeseleccionados.length > 0) {
           await axios.post(`http://localhost:8080/empleado_proyecto/${proyectoId}/removeProject`, empleadosDeseleccionados, {
            headers: { 'Content-Type': 'application/json' }
          });
        }
        this.snackbarText = "Empleados asginados con éxito";
        this.snackbar = true; 
  
        
      } catch (error) {
        console.error("Error al asignar al empleado/s:", error);
        this.snackbarText = "Error al asignar o desasignar los empleados";
        this.snackbar = true; // Muestra el snackbar
      }
    },


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

.save-changes-btn {
  transition: all 0.3s ease;
  position: abslute;
  bottom: 2%;
  right: 25px;
  z-index: 10;
}

@media (max-width: 768px) {
  .container-main {
    width: 90%;
    margin-right: 350vh;
  }

  .save-changes-btn {
    width: 100%;
    justify-content: center;
  }
  }

</style>
