<template>
  <v-container fluid class="pa-4 fill-width">
    <div class="container-main">
      <v-text-field v-model="search" label="Buscar" prepend-inner-icon="mdi-magnify" autofocus />

      <!-- Tabla de datos -->
      <v-data-table :headers="headers" :items="users" :search="search" class="elevation-1">
        <template #item.actions="{ item }">
          <v-btn icon @click="editUser(item)">
            <i class="fa fa-pencil" aria-hidden="true"></i>
          </v-btn>
          <v-btn icon color="red" @click="deleteUser(item)">
            <i class="fa fa-trash" aria-hidden="true"></i>
          </v-btn>
        </template>
      </v-data-table>
      <!-- Botón flotante para agregar -->
      <div class="mt-4 d-flex justify-end">
        <v-btn icon color="primary" class="add-user-btn" fab @click="openDialog()" style="">
          <i class="fa fa-plus" aria-hidden="true"></i>
        </v-btn>
      </div>




    </div>


    <!-- Diálogo para agregar/editar -->
    <v-dialog v-model="dialog" max-width="500px">
      <v-card>
        <v-card-title>
          <span class="text-h6">{{ editedUser.id ? 'Editar Usuario' : 'Nuevo Usuario' }}</span>
        </v-card-title>
        <v-card-text>
          <v-form ref="formRef">
            <v-text-field label="NIF" v-model="editedUser.tx_nif" :rules="[rules.required]" />
            <v-text-field label="Primer apellido" v-model="editedUser.tx_apellido1" :rules="[rules.required]" />
            <v-text-field label="Segundo apellido" v-model="editedUser.tx_apellido2" />
            <v-text-field label="Fecha de nacimiento" v-model="editedUser.f_nacimiento" type="date" />
            <v-text-field label="Teléfono 1" v-model="editedUser.n_telefono1" />
            <v-text-field label="Teléfono 2" v-model="editedUser.n_telefono2" />
            <v-text-field label="Fecha alta" v-model="editedUser.f_alta" type="date" />
            <v-text-field label="Fecha baja" v-model="editedUser.f_baja" type="date" />
            <v-text-field label="Estado civil" v-model="editedUser.cx_edocivil" />
            <v-switch label="¿Formación universitaria?" v-model="editedUser.b_formacionu" />
          </v-form>
        </v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn text @click="closeDialog">Cancelar</v-btn>
          <v-btn color="primary" @click="saveUser">Guardar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Snackbar de confirmación -->
    <v-snackbar v-model="snackbar" :timeout="3000" color="success">
      {{ snackbarText }}
    </v-snackbar>
  </v-container>
</template>


<!-- <script>
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
</script>-->

<script>
import axios from 'axios'
import 'font-awesome/css/font-awesome.min.css';


export default {
  data() {
    return {
      search: '',
      dialog: false,
      snackbar: false,
      snackbarText: '',
      formRef: null,
      empleados: [],


      users: [],

      headers: [
        { title: 'ID', key: 'id_empleado' },
        { title: 'NIF', key: 'tx_nif' },
        { title: 'Nombre', key: 'tx_nombre' },
        { title: 'Primer apellido', key: 'tx_apellido1' },
        { title: 'Segundo apellido', key: 'tx_apellido2' },
        { title: 'Nacimiento', key: 'f_nacimiento' },
        { title: 'Primer telefono', key: 'n_telefono1' },
        { title: 'Segundo telefono', key: 'n_telefono2' },
        { title: 'Email', key: 'tx_email' },
        { title: 'Fecha alta', key: 'f_alta' },
        { title: 'Fecha baja', key: 'f_baja' },
        { title: 'Estado civil', key: 'cx_edocivil' },
        { title: 'Form uni', key: 'b_formacionu' },
        { title: 'Acciones', key: 'actions', sortable: false }
      ],


      editedUser: {
        id_empleado: null,
        tx_nombre: '',
        tx_email: ''
      },
      rules: {
        required: v => !!v || 'Campo requerido',
        email: v => /.+@.+\..+/.test(v) || 'Email inválido'
      }
    }
  },
  created() {
    this.fetchEmpleados();
  },
  methods: {
    openDialog(user = null) {
      if (user) {
        this.editedUser.id_empleado = user.id_empleado
        this.editedUser.tx_nif=user.tx_nif,  
        this.editedUser.tx_nombre = user.tx_nombre
        this.editedUser.tx_apellido1 = user.tx_apellido1
        this.editedUser.tx_apellido2 = user.tx_apellido2
        this.editedUser.f_nacimiento = user.f_nacimiento
        this.editedUser.n_telefono1 = user.n_telefono1
        this.editedUser.n_telefono2 = user.n_telefono2
        this.editedUser.tx_email = user.tx_email
        this.editedUser.f_alta = user.f_alta
        this.editedUser.f_baja = user.f_baja
        this.editedUser.cx_edocivil = user.cx_edocivil
        this.editedUser.b_formacionu = user.b_formacionu


        this.editedUser.tx_email = user.tx_email
      } else {
        this.editedUser.id_empleado = null
        this.editedUser.tx_nombre = ''
        this.editedUser.tx_email = ''
      }
      this.dialog = true
    },


    closeDialog() {
      this.dialog = false
    },


    saveUser() {
      this.formRef.validate().then(success => {
        if (!success.valid) return

        if (this.editedUser.id_empleado) {
          const index = this.users.findIndex(u => u.id === this.editedUser.id_empleado)
          if (index !== -1) {
            this.users[index] = { ...this.editedUser }
            this.snackbarText = 'Usuario actualizado'
          }
        } else {
          const newId = Math.max(...this.users.map(u => u.id_empleado)) + 1
          this.users.push({ ...this.editedUser, id_empleado: newId })
          this.snackbarText = 'Usuario agregado'
        }

        this.snackbar = true
        this.closeDialog()
      })
    },


    editUser(user) {
      this.openDialog(user)
    },
    deleteUser(user) {
      this.users = this.users.filter(u => u.ID_EMPLEADO !== user.ID_EMPLEADO)
      this.snackbarText = 'Usuario eliminado'
      this.snackbar = true
    },
    async fetchEmpleados() {
      try {
        const response = await axios.get('http://localhost:8080/empleados/all');
        this.empleados = response.data;
        this.users = this.empleados.map(emp => ({
          id_empleado: emp.id_empleado,
          tx_nif: emp.tx_nif,          // Mapeamos cada campo de la API al formato adecuado
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
          b_formacionu: emp.b_formacionu
        }));

      } catch (error) {
        console.error("Error al obtener los empleados", error);
      }
    },
  }
}
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