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

    @Column(name = "tx_nif")
    @NotBlank(message = "El documento de indentidad es obligatorio")
    String tx_nif;
    String tx_nombre;
    String tx_apellido1;
    String tx_apellido2;


    @Column(name = "f_nacimiento")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate f_nacimiento;

    @Pattern(regexp = "^(\\d{9})$", message = "El número de teléfono debe contener exactamente 9 dígitos.")
    String n_telefono1;
    @Pattern(regexp = "^(\\d{9})$", message = "El número de teléfono debe contener exactamente 9 dígitos.")
    String n_telefono2;

    @NotNull(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico no tiene un formato válido.")
    String tx_email;

    @Column(name = "f_alta")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @NotNull(message = "La fecha de alta es obligatoria")
    private LocalDate f_alta;

    @Column(name = "f_baja")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "La fecha de baja debe ser hoy o en el futuro.")
    private LocalDate f_baja;


    char cx_edocivil;
    char b_formacionu;

    @Version
    private Integer version = 0;

    @OneToMany(mappedBy = "empleado")
    @JsonManagedReference("empleado-proyectos")
    private List<Empleados_proyecto> proyectosEmpleados;


}
