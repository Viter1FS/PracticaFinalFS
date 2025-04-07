package com.example.vitech_FS.entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
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
    @JsonBackReference("empleado-proyectos")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "ID_PROYECTO", insertable = false, updatable = false)
    @JsonBackReference("proyecto-empleados")
    private Proyectos proyectos;

    @Column(name = "f_alta")
    @JsonFormat(pattern = "yyyy-MM-dd")
    String f_alta;

}
