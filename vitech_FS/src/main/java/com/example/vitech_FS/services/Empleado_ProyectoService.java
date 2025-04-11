package com.example.vitech_FS.services;

import com.example.vitech_FS.entitys.Empleados_proyecto;

import java.util.List;

public interface Empleado_ProyectoService {

    List<Empleados_proyecto> findAll();

    void assignProjectToEmployee(Integer empleadoId, Integer proyectoId) throws Exception;

    void removeProjectFromEmployee(Integer employeeId, Integer projectId);

}
