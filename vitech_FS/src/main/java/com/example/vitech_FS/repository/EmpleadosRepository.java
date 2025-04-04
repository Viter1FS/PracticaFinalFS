package com.example.vitech_FS.repository;

import com.example.vitech_FS.entitys.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadosRepository extends JpaRepository <Empleado, Integer>{

    @Query("SELECT MAX(e.id_empleado) FROM Empleado e")
    Integer findMaxId();



}
