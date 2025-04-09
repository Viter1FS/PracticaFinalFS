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
          :items="users"
          :search="search"
          class="elevation-1"
        >
          <template #item.actions="{ item }">
            <div class="d-flex">
              <v-btn icon @click="editUser(item)" class="mr-1">
                <i class="fa fa-pencil" aria-hidden="true"></i>
              </v-btn>
              <v-btn icon color="red" @click="deleteEmpleado(item)">
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
              editedUser.id_empleado ? "Editar Usuario" : "Nuevo Usuario"
            }}</span>
          </v-card-title>
          <v-card-text>
            <v-form ref="formRef">
              <v-text-field
                label="NIF"
                v-model="editedUser.tx_nif"
                :rules="[rules.required]"
              />
              <v-text-field
                label="Nombre"
                v-model="editedUser.tx_nombre"
                :rules="[rules.required]"
              />
              <v-text-field
                label="Primer apellido"
                v-model="editedUser.tx_apellido1"
                :rules="[rules.required]"
              />
              <v-text-field
                label="Segundo apellido"
                v-model="editedUser.tx_apellido2"
              />
              <v-text-field
                label="Fecha de nacimiento"
                v-model="editedUser.f_nacimiento"
                type="date"
                :rules="[rules.required]"
              />
              <v-text-field
                label="Teléfono 1"
                v-model="editedUser.n_telefono1"
                :rules="[rules.required]"
              />
              <v-text-field label="Teléfono 2" v-model="editedUser.n_telefono2" />
              <v-text-field
                label="Email"
                v-model="editedUser.tx_email"
                type="email"
              />
              <v-text-field
                label="Fecha alta"
                v-model="editedUser.f_alta"
                type="date"
                :rules="[rules.required]"
              />
              <v-text-field
                label="Fecha baja"
                v-model="editedUser.f_baja"
                type="date"
              />
              <v-switch
                label="¿Estado casad@?"
                v-model="editedUser.cx_edocivil"
                color="primary"
              />
              <v-switch
                label="¿Formación universitaria?"
                v-model="editedUser.b_formacionu"
                color="primary"
              />
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer />
            <v-btn text @click="closeDialog">Cancelar</v-btn>
            <v-btn color="primary" @click="saveEmpleado">Guardar</v-btn>
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
        users: [],
  
        headers: [
          { title: "ID_proyecto", key: "id_empleado" },
          { title: "NIF", key: "tx_nif" },
          { title: "Nombre", key: "tx_nombre" },
          { title: "Primer apellido", key: "tx_apellido1" },
          { title: "Segundo apellido", key: "tx_apellido2" },
          { title: "Nacimiento", key: "f_nacimiento" },
          { title: "Primer telefono", key: "n_telefono1" },
          { title: "Segundo telefono", key: "n_telefono2" },
          { title: "Email", key: "tx_email" },
          { title: "Fecha alta", key: "f_alta" },
          { title: "Fecha baja", key: "f_baja" },
          { title: "Estado civil", key: "cx_edocivil" },
          { title: "Form uni", key: "b_formacionu" },
          { title: "Acciones", key: "actions", sortable: false },
        ],
  
        editedUser: {
          id_empleado: null,
          tx_nombre: "",
          tx_apellido1: "",
          tx_apellido2: "",
          f_nacimiento: "",
          n_telefono1: "",
          n_telefono2: "",
          tx_email: "",
          f_alta: "",
          f_baja: "",
          cx_edocivil: false,
          b_formacionu: false,
        },
        rules: {
          required: (v) => !!v || "Campo requerido",
          email: (v) => /.+@.+\..+/.test(v) || "Email inválido",
        },
      };
    },
    created() {
      this.fetchEmpleados();
    },
    methods: {
      openDialog(user = null) {
        console.log("Abriendo diálogo...", user);
        if (user) {
          this.editedUser = {
            ...user,
            // Suponiendo que recibes "C" y "S" y quieres que:
            // "C" → true (casado) y "S" → false (no casado)
            cx_edocivil: user.cx_edocivil === "C",
            // Para formación, si "S" significa que tiene formación, lo conviertes a boolean
            b_formacionu: user.b_formacionu === "S",
          };
        } else {
          this.editedUser = {
            id_empleado: null,
            tx_nombre: "",
            tx_apellido1: "",
            tx_apellido2: "",
            f_nacimiento: "",
            n_telefono1: "",
            n_telefono2: "",
            tx_email: "",
            f_alta: "",
            f_baja: "",
            cx_edocivil: false,
            b_formacionu: false,
          };
        }
        this.dialog = true;
      },
  
      closeDialog() {
        this.dialog = false;
      },
  
      editUser(user) {
        this.openDialog(user);
      },
  
      deleteUser(user) {
        this.users = this.users.filter((u) => u.id_empleado !== user.id_empleado);
        this.snackbarText = "Usuario eliminado";
        this.snackbar = true;
      },
  
      //API
      async fetchEmpleados() {
        try {
          const response = await axios.get("http://localhost:8080/empleados/all");
          this.empleados = response.data;
          this.users = this.empleados.map((emp) => ({
            id_empleado: emp.id_empleado,
            tx_nif: emp.tx_nif,
            tx_nombre: emp.tx_nombre,
            tx_apellido1: emp.tx_apellido1,
            tx_apellido2: emp.tx_apellido2,
            f_nacimiento: emp.f_nacimiento,
            n_telefono1: emp.n_telefono1,
            n_telefono2: emp.n_telefono2,
            tx_email: emp.tx_email,
            f_alta: emp.f_alta,
            f_baja: emp.f_baja,
  
            cx_edocivil: emp.cx_edocivil,
            b_formacionu: emp.b_formacionu,
          }));
        } catch (error) {
          console.error("Error al obtener los empleados", error);
        }
      },
      //--------------------------------------
      async saveEmpleado() {
        console.log("Datos a guardar:", this.editedUser);
  
        // Asegurarnos de que la referencia del formulario esté disponible
        if (!this.$refs.formRef) {
          console.error("Formulario no encontrado");
          return;
        }
  
        // Validar el formulario
        const isValid = await this.$refs.formRef.validate();
        if (!isValid.valid) return;
  
        // Convertir valores booleanos de nuevo a los valores esperados por el backend
        this.editedUser.cx_edocivil = this.editedUser.cx_edocivil ? "C" : "S"; // Casado 'C', no casado 'S'
        this.editedUser.b_formacionu = this.editedUser.b_formacionu ? "S" : "N";
  
        if (this.editedUser.id_empleado) {
          // Actualizar un usuario existente
          try {
            const response = await axios.put(
              `http://localhost:8080/empleados/updateEmployee/${this.editedUser.id_empleado}`,
              this.editedUser
            );
  
            // Actualizar el usuario en la lista local
            const index = this.users.findIndex(
              (u) => u.id_empleado === this.editedUser.id_empleado
            );
            if (index !== -1) {
              this.users[index] = { ...response.data };
            }
  
            this.snackbarText = "Usuario actualizado";
          } catch (error) {
            console.error("Error al actualizar el usuario", error);
            this.snackbarText = "Error al actualizar el usuario";
          }
        } else {
          // Crear un nuevo usuario
          try {
            const response = await axios.post(
              "http://localhost:8080/empleados/addEmployee",
              this.editedUser
            );
            this.users.push(response.data);
            this.snackbarText = "Usuario agregado";
          } catch (error) {
            console.error("Error al agregar el usuario", error);
            this.snackbarText = "Error al agregar el usuario";
          }
        }
  
        // Mostrar el snackbar de éxito y cerrar el diálogo
        this.snackbar = true;
        this.closeDialog();
      },
  
      async deleteEmpleado(user) {
        try {
          // Confirmación antes de eliminar
          const confirmDelete = confirm(
            `¿Estás seguro de que deseas eliminar al usuario ${user.tx_nombre} ${user.tx_apellido1}?`
          );
          if (!confirmDelete) return;
  
          // Hacemos la solicitud DELETE al backend
          const response = await axios.delete(
            `http://localhost:8080/empleados/deleteEmployee/${user.id_empleado}`
          );
  
          // Si la respuesta es exitosa, eliminamos el usuario de la lista local
          if (response.status === 200) {
            this.users = this.users.filter(
              (u) => u.id_empleado !== user.id_empleado
            );
            this.snackbarText = "Usuario eliminado con éxito";
            this.snackbar = true;
          }
        } catch (error) {
          console.error("Error al eliminar el usuario", error);
          this.snackbarText = "Error al eliminar el usuario";
          this.snackbar = true;
        }
      },
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
    margin-left: -15vh;
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
    right: 25px;
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
  