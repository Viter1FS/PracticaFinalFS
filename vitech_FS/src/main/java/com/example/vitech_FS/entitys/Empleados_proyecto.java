package com.example.vitech_FS.entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "PR_EMPLEADOS_PROYECTO")
public class Empleados_proyecto {

    @EmbeddedId
    Empleados_proyectoPK id_pr_empleados_proyecto;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO", insertable = false, updatable = false)
    @JsonBackReference //evita que el ciclo de referencia continue , y no tener una referencia recursiva 
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "ID_PROYECTO" ,insertable = false, updatable = false)
    @JsonBackReference
    private Proyectos proyectos;

    String f_alta;

}
