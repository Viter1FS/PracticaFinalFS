package com.example.vitech_FS.services;


import com.example.vitech_FS.entitys.Empleado;

import java.util.List;

public interface EmpleadoService {

    /**
     * Obtiene todos los empleados disponibles en la base de datos de practica.
     * @return Lista de todos los empleados.
     */
    List<Empleado> findAll();

    /**
     * Busca un libro por su ISBN.
     * @param id_empleado Número ISBN del libro.
     * @return El libro encontrado o null si no existe.
     */
    Empleado findEmployeeById(int id_empleado);


    void deleteEmployeeById(Integer id) ;

    Empleado addEmployee(Empleado e);

    Empleado updateById (Integer id , Empleado e);
}
