package com.example.vitech_FS.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

@Embeddable
public class Empleados_proyectoPK implements  Serializable{


        @Column(name = "ID_EMPLEADO")
        private Integer id_empleado;

        @Column(name = "ID_PROYECTO")
        private Integer id_proyecto;


}
