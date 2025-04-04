package com.example.vitech_FS.entitys;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity // Anotación que indica que esta clase es una entidad JPA
@Table(name = "PR_PROYECTOS")
public class Proyectos {

    @Id
    Integer id_proyecto;
    String tx_descripción;
    String f_inicio;
    String f_fin;
    String f_baja;
    String tx_lugar;
    String tx_observaciones;





}
