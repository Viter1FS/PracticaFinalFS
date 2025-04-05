package com.example.vitech_FS.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Empleados_proyectoPK implements  Serializable{


        @Column(name = "ID_EMPLEADO")
        private Integer id_empleado;

        @Column(name = "ID_PROYECTO")
        private Integer id_proyecto;


}
