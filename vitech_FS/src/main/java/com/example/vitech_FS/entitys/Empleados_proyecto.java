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
    // Relación Muchos a Uno con la entidad "Empleados",
    // sin permitir inserciones ni actualizaciones en la base de datos en esta columna.
    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO", insertable = false, updatable = false)
    @JsonBackReference("empleado-proyectos") // Evita la serialización de la relación circular de "Empleado" hacia "Proyecto".
    private Empleado empleado;

    // Relación Muchos a Uno con la entidad "Proyectos",
    // sin permitir inserciones ni actualizaciones en la base de datos en esta columna.
    @ManyToOne
    @JoinColumn(name = "ID_PROYECTO", insertable = false, updatable = false)
    @JsonBackReference("proyecto-empleados") // Evita la serialización de la relación circular de "Proyecto" hacia "Empleado".
    private Proyectos proyectos;

    @Column(name = "f_alta")
    @JsonFormat(pattern = "yyyy-MM-dd")
    String f_alta;

}
