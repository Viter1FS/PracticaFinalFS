package com.example.vitech_FS.services.impl;


import com.example.vitech_FS.entitys.Empleado;
import com.example.vitech_FS.repository.EmpleadosRepository;
import com.example.vitech_FS.services.EmpleadoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired  // Inyección de dependencia del repositorio de empleados
    private EmpleadosRepository empleadosRepository;


    @Override
    public List<Empleado> findAll() {
        // Llama al metodo del repositorio para obtener todos los empelados
        return empleadosRepository.findAll();
    }

    /**
     * Busca un empleado por su id_empleado.
     *
     * @param id_empleado Número id del empleado.
     * @return El empleado encontrado o null si no existe.
     */
    @Override
    public Empleado findEmployeeById(int id_empleado) {
        Optional<Empleado> existingEmployee = empleadosRepository.findById(id_empleado); // Busca el empleado por su id_empleado (id_empleado)
        if (existingEmployee.isPresent()) {
            return existingEmployee.orElse(null);
        }
        return null;// Devuelve el empleado si existe, o null si no se encuentra
    }

    /**
     * Da de alta un nuevo empleado
     *
     * @param e , empleado que intentamos introducir
     * @return El empleado a introducir en base de datos.
     */

    @Override
    public Empleado addEmployee(Empleado e) {
        log.info("Adding employee to Database ....  ");

        if (e.getId_empleado() != null && empleadosRepository.existsById(e.getId_empleado())) {
            log.info("Error: Employee with ID {} already exists", e.getId_empleado());
            return null;
        }
        Integer nextId = empleadosRepository.findMaxId(); // Método que obtiene el ID máximo de la tabla
        if (nextId == null) {
            nextId = 1; // Si la tabla está vacía, empieza desde el 1
        } else {
            nextId++; // Incrementa el ID para el nuevo empleado
        }
        e.setId_empleado(nextId);
        Empleado savedEmployee = empleadosRepository.save(e);
        log.info("Added employee to Database with ID {}", savedEmployee.getId_empleado());
        return savedEmployee;
    }

    @Override
    public void deleteEmployeeById(Integer id) {
        log.info("Deleting employee with id {}", id);

        Optional<Empleado> empleadoOpt = empleadosRepository.findById(id);
        if (empleadoOpt.isPresent()) {
            empleadosRepository.deleteById(id);
            log.info("Employee with id {} deleted from Database", id);
        } else {
            log.warn("Employee with id {} does not exist in Database", id);
        }

    }

    @Override
    public Empleado updateById(Integer id , Empleado updatedEmpleado)  {
        log.info("Updating employee to Database with id {}   ", id);
        Optional<Empleado> empleadoOpt = empleadosRepository.findById(id);
        if(empleadoOpt.isPresent()){
            Empleado existingEmpleado = empleadoOpt.get();
            existingEmpleado.setTx_nif(updatedEmpleado.getTx_nif());
            existingEmpleado.setTx_nombre(updatedEmpleado.getTx_nombre());
            existingEmpleado.setTx_apellido1(updatedEmpleado.getTx_apellido1());
            existingEmpleado.setTx_apellido2(updatedEmpleado.getTx_apellido2());
            existingEmpleado.setF_nacimiento(updatedEmpleado.getF_nacimiento());
            existingEmpleado.setN_telefono1(updatedEmpleado.getN_telefono1());
            existingEmpleado.setN_telefono2(updatedEmpleado.getN_telefono2());
            existingEmpleado.setTx_email(updatedEmpleado.getTx_email());
            existingEmpleado.setF_alta(updatedEmpleado.getF_alta());
            existingEmpleado.setF_baja(updatedEmpleado.getF_baja());
            existingEmpleado.setCx_edocivil(updatedEmpleado.getCx_edocivil());
            existingEmpleado.setB_formacionu(updatedEmpleado.getB_formacionu());

            return empleadosRepository.save(existingEmpleado);
        }
        return null;
    }
}



