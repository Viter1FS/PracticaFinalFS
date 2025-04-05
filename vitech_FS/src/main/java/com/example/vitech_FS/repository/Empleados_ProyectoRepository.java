package com.example.vitech_FS.repository;

import com.example.vitech_FS.entitys.Empleado;
import com.example.vitech_FS.entitys.Empleados_proyecto;
import com.example.vitech_FS.entitys.Empleados_proyectoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Empleados_ProyectoRepository extends JpaRepository<Empleados_proyecto, Empleados_proyectoPK> {

}
