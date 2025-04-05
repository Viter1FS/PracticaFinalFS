package com.example.vitech_FS.services;

import com.example.vitech_FS.entitys.Empleado;
import com.example.vitech_FS.entitys.Empleados_proyecto;

import java.util.List;

public interface Empleado_ProyectoService {

    List<Empleados_proyecto> findAll();
}
