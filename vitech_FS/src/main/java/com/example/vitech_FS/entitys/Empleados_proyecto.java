package com.example.vitech_FS.entitys;

import jakarta.persistence.*;

@Entity
@Table(name = "PR_EMPLEADOS_PROYECTO")
public class Empleados_proyecto {

    @EmbeddedId
    Empleados_proyectoPK id_pr_empleados_proyecto;

    @OneToOne
    @JoinColumn(name = "ID_EMPLEADO")
    @MapsId("id_empleado")
    private Empleado empleado;

    @OneToOne
    @JoinColumn(name = "ID_PROYECTO")
    @MapsId("id_proyecto")
    private Proyectos proyectos;

    String f_alta;

}
