package com.example.vitech_FS.entitys;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity // Anotación que indica que esta clase es una entidad JPA
@Table(name = "EM_EMPLEADOS")
public class Empleado {

    @Id
    Integer id_empleado;
    String tx_nif;
    String tx_nombre;
    String tx_apellido1;
    String tx_apellido2;


    @Column(name = "f_nacimiento")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate f_nacimiento;

    String n_telefono1;
    String n_telefono2;
    String tx_email;
    String f_alta;
    String f_baja;
    char cx_edocivil;
    char b_formacionu;

    @Version
    private Integer version = 0;

    @OneToMany(mappedBy = "empleado")
    @JsonManagedReference("empleado-proyectos")
    private List<Empleados_proyecto> proyectosEmpleados;


}
