package com.example.vitech_FS.repository;

import com.example.vitech_FS.entitys.Empleado;
import com.example.vitech_FS.entitys.Proyectos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectosRepository extends JpaRepository<Proyectos, Integer> {

    @Query("SELECT MAX(p.id_proyecto) FROM Proyectos p")
    Integer findMaxId();



}
