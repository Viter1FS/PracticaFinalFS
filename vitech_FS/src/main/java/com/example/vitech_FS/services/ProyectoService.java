package com.example.vitech_FS.services;

import com.example.vitech_FS.entitys.Empleado;
import com.example.vitech_FS.entitys.Proyectos;

import java.util.List;

public interface ProyectoService {

    /**
     * Obtiene todos los proyectos disponibles en la base de datos de practica.
     * @return Lista de todos los proyectos.
     */
    List<Proyectos> findAll();


    Proyectos findProjectById(Integer id);


    Proyectos addProject(Proyectos proyecto);

    void deleteProjectById(Integer id);

    Proyectos updateById(Integer id, Proyectos updatedProject);
}
