package com.example.vitech_FS.entitys;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity // Anotación que indica que esta clase es una entidad JPA
@Table(name = "PR_PROYECTOS")
public class Proyectos {

    @Id
    Integer id_proyecto;

    @NotNull
    String tx_descripción;


    @Column(name = "f_inicio")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate f_inicio;

    @Column(name = "f_fin")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate f_fin;

    @Column(name = "f_baja")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate f_baja;


    String tx_lugar;
    String tx_observaciones;


    @OneToMany(mappedBy = "proyectos")  // Este era el fix clave
    @JsonManagedReference("proyecto-empleados")
    private List<Empleados_proyecto> empleadosProyectos;


}
