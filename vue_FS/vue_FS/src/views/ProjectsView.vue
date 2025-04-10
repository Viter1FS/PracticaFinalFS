<template>
    <v-container fluid class="pa-4 fill-width">
      <div class="container-main">
        <v-text-field
          v-model="search"
          label="Buscar"
          prepend-inner-icon="mdi-magnify"
          autofocus
        />
  
        <!-- Tabla de datos -->
        <v-data-table
          :headers="headers"
          :items="projects"
          :search="search"
          class="elevation-1"
        >
          <template #item.actions="{ item }">
            <div class="d-flex">
              <v-btn icon @click="editProject(item)" class="mr-1">
                <i class="fa fa-pencil" aria-hidden="true"></i>
              </v-btn>
              <v-btn icon color="red" @click="dismissProject(item)">
                <i class="fa fa-trash" aria-hidden="true"></i>
              </v-btn>
            </div>
          </template>
        </v-data-table>
        <!-- Botón flotante para agregar -->
        <div class="mt-4 d-flex justify-end">
          <v-btn
            icon
            color="primary"
            class="add-user-btn"
            fab
            @click="openDialog()"
            style=""
          >
            <i class="fa fa-plus" aria-hidden="true"></i>
          </v-btn>
        </div>
      </div>
  
      <!-- Diálogo para agregar/editar -->
      <v-dialog v-model="dialog" max-width="500px">
        <v-card>
          <v-card-title>
            <span class="text-h6">{{
              editedProject.id_proyecto ? "Editar proyecto" : "Nuevo proyecto"
            }}</span>
          </v-card-title>
          <v-card-text>
            <v-form ref="formRef">
              <v-text-field
                label="Descrición"
                v-model="editedProject.tx_descripción"
                :rules="[rules.required]"
              />
              <v-text-field
                label="Fecha inicio"
                v-model="editedProject.f_inicio"
                type="date"
                :rules="[rules.required]"
              />
              
              <v-text-field
                label="Fecha fin"
                v-model="editedProject.f_fin"
                type="date"
                
              />
              <v-text-field
                label="Fecha baja"
                v-model="editedProject.f_baja"
                type="date"
              />

              <v-text-field
                label="Lugar"
                v-model="editedProject.tx_lugar"
              />
              <v-text-field
                label="Observaciones"
                v-model="editedProject.tx_observaciones"
                
              />
             
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer />
            <v-btn text @click="closeDialog">Cancelar</v-btn>
            <v-btn color="primary" @click="saveProyecto">Guardar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
  
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
        search: "",
        dialog: false,
        snackbar: false,
        snackbarText: "",
        formRef: null,
        proyectos: [],
        projects: [],
  
        headers: [
          { title: "ID", key: "id_proyecto" },
          { title: "Descrición", key: "tx_descripción" },
          { title: "Fecha inicio", key: "f_inicio" },
          { title: "Fecha fin", key: "f_fin" },
          { title: "Lugar", key: "tx_lugar" },
          { title: "Observaciones", key: "tx_observaciones" },
          { title: "Acciones", key: "actions", sortable: false },
        ],
  
        editedProject: {
          id_proyecto: null,
          tx_descripción: "",
          f_inicio: "",
          f_fin: "",
          f_baja: "",
          tx_lugar: "",
          tx_observaciones: "",
        },
        rules: {
          required: (v) => !!v || "Campo requerido",
          email: (v) => /.+@.+\..+/.test(v) || "Email inválido",
        },
      };
    },
    created() {
      this.fetchProyectos();
    },
    methods: {
      openDialog(proyect = null) {
        console.log("Abriendo diálogo...", proyect);
        if (proyect) {
          this.editedProject = {
            ...proyect,
          };
        } else {
          this.editedProject = {
          id_proyecto: null,
          tx_descripción: "",
          f_inicio: "",
          f_fin: "",
          f_baja: "",
          tx_lugar: "",
          tx_observaciones: ""
          };
        }
        this.dialog = true;
      },
  
      closeDialog() {
        this.dialog = false;
      },
  
      editProject(project) {
        this.openDialog(project);
      },
  
      deleteUser(project) {
        this.projects = this.projects.filter((p) => p.id_proyecto !== project.id_proyecto);
        this.snackbarText = "Projecto eliminado";
        this.snackbar = true;
      },
  
      //API
      async fetchProyectos() {
        try {
          const response = await axios.get("http://localhost:8080/proyectos/all");
          // this.proyectos = response.data;
            this.projects = response.data.map((pro) => ({
            id_proyecto: pro.id_proyecto,
            tx_descripción : pro.tx_descripción,
            f_inicio : pro.f_inicio,
            f_fin : pro.f_fin,
            f_baja : pro.f_baja,
            tx_lugar : pro.tx_lugar,
            tx_observaciones : pro.tx_observaciones,
            id_empleado: pro.empleadosProyectos.map(
              (item) => item.id_pr_empleados_proyecto.id_empleado
            ), 
          }));
        } catch (error) {
          console.error("Error al obtener los empleados", error);
        }
      },
      //--------------------------------------
      async saveProyecto() {
        console.log("Datos a guardar:", this.editedProject);
  
        // Asegurarnos de que la referencia del formulario esté disponible
        if (!this.$refs.formRef) {
          console.error("Formulario no encontrado");
          return;
        }
  
        // Validar el formulario
        const isValid = await this.$refs.formRef.validate();
        if (!isValid.valid) return;
  
        if (this.editedProject.id_proyecto) {
          // Actualizar un usuario existente
          try {
            const response = await axios.put(
              `http://localhost:8080/proyectos/updateProject/${this.editedProject.id_proyecto}`,
              this.editedProject
            );
  
            // Actualizar el proyecto en la lista local
            const index = this.projects.findIndex(
              (p) => p.id_proyecto === this.editedProject.id_proyecto
            );
            if (index !== -1) {
              this.projects[index] = { ...response.data };
            }
  
            this.snackbarText = "Proyecto actualizado";
          } catch (error) {
            console.error("Error al actualizar el proyecto", error);
            this.snackbarText = "Error al actualizar el proyecto";
          }
        } else {
          // Crear un nuevo proyecto
          try {
            const response = await axios.post(
              "http://localhost:8080/proyectos/addProject",
              this.editedProject
            );
            this.projects.push(response.data);
            this.snackbarText = "Proyecto agregado";
          } catch (error) {
            console.error("Error al agregar el proyecto", error);
            this.snackbarText = "Error al agregar el proyecto";
          }
        }
  
        // Mostrar el snackbar de éxito y cerrar el diálogo
        this.snackbar = true;
        this.closeDialog();
      },

      async dismissProject(project) {
        try {

          // Validar si el empleado tiene proyectos asignados
          if (project.id_empleado && project.id_empleado.length > 0) {
            
            alert(
              `No se puede dar de baja el proyecto ${project.tx_descripción} porque tiene asignado al menos un recurso`
            );
            return;
          }



          // Confirmación antes de eliminar
          const confirmDelete = confirm(
            `¿Estás seguro de que deseas dar de baja al proyecto ${project.id_proyecto} ${project.tx_descripción}?`
          );

          if (!confirmDelete) return;
          // Le asignamos la fecha actual como "fecha de baja"
          const fechaHoy = new Date().toISOString().split("T")[0];
          const proyectoBaja = {
            ...project,
            f_baja: fechaHoy,
          };

          // Llamamos al endpoint de actualización normal (como si estuviéramos editando)
          const response = await axios.put(
            `http://localhost:8080/proyectos/updateProject/${project.id_proyecto}`,
            proyectoBaja
          );

          // Si la respuesta es exitosa, eliminamos el usuario de la lista local

          if (response.status === 200) {
            this.projects = this.projects.filter(
              (p) => p.id_proyecto !== projects.id_proyecto
            );
            this.snackbarText = "Proyecto dado de baja con éxito";
            this.snackbar = true;
          }
        } catch (error) {
          console.error("Error al dar de baja el proyecto", error);
          this.snackbarText = "Error al dar de baja el proyecto";
          this.snackbar = true;
        }
      }
    },
  };
  </script>
  
  <style scoped>
  .container-main {
    position: relative;
    width: 100%;
    max-width: 1400px;
    margin: 0, auto;
    margin-right: 50vh;
    margin-left: -2vh;
    padding: 5px;
    box-sizing: border-box;
    display: block;
  }
  
  .v-data-table-header th {
    padding-left: -100px;
    /* Reducir el padding izquierdo */
    padding-right: -100px;
    /* Reducir el padding derecho */
  }
  
  .add-user-btn {
    transition: all 0.3s ease;
    position: absolute;
    bottom: 30px;
    right: 30px;
    z-index: 10;
  }
  
  @media (max-width: 768px) {
    .container-main {
      width: 90%;
      /* En pantallas pequeñas, puede ocupar el 90% */
      margin-right: 350vh;
    }
  
    .add-user-btn {
      width: 100%;
      justify-content: center;
    }
  }
  </style>
  