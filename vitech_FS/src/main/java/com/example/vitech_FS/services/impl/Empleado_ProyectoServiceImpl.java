package com.example.vitech_FS.services.impl;

import com.example.vitech_FS.entitys.Empleado;
import com.example.vitech_FS.entitys.Empleados_proyecto;
import com.example.vitech_FS.entitys.Empleados_proyectoPK;
import com.example.vitech_FS.entitys.Proyectos;
import com.example.vitech_FS.repository.EmpleadosRepository;
import com.example.vitech_FS.repository.Empleados_ProyectoRepository;
import com.example.vitech_FS.repository.ProyectosRepository;
import com.example.vitech_FS.services.Empleado_ProyectoService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Transactional
public class Empleado_ProyectoServiceImpl implements Empleado_ProyectoService {

    @Autowired
    private Empleados_ProyectoRepository empleadosProyectoRepository;

    @Autowired
    private EmpleadosRepository empleadoRepository;

    @Autowired
    private ProyectosRepository proyectoRepository;



    @Override
    public List<Empleados_proyecto> findAll() {
        // Llama al metodo del repositorio para obtener todos los empelados
        return empleadosProyectoRepository.findAll();
    }


    @Override
    public void assignProjectToEmployee(Integer empleadoId, Integer proyectoId) throws Exception {
        // Verificar que el empleado exista
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new Exception("Empleado no encontrado"));

        // Verificar que el proyecto exista
        Proyectos proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new Exception("Proyecto no encontrado"));

        // Crear la clave compuesta y asignarla
        // Supongamos que la clase Empleados_proyectoPK tiene un constructor que recibe dos parámetros
        Empleados_proyectoPK pk = new Empleados_proyectoPK(empleado.getId_empleado(), proyecto.getId_proyecto());

        // Crear la nueva asignación y asignar la clave compuesta
        Empleados_proyecto proyectosEmpleados = new Empleados_proyecto();
        proyectosEmpleados.setId_pr_empleados_proyecto(pk);
        proyectosEmpleados.setEmpleado(empleado);
        proyectosEmpleados.setProyectos(proyecto);
        // Asigna otros atributos si es necesario, por ejemplo:
        // proyectosEmpleados.setF_alta("2025-04-09");
        proyectosEmpleados.setF_alta(LocalDate.now());
        // Guardar la asignación
        empleadosProyectoRepository.save(proyectosEmpleados);
    }

    public void removeProjectFromEmployee(Integer employeeId, Integer projectId) {
        Empleado empleado = empleadoRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        Proyectos proyecto = proyectoRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        Empleados_proyectoPK empk = new Empleados_proyectoPK(employeeId,projectId);
        Empleados_proyecto ep = empleadosProyectoRepository.findById(empk).orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        // Eliminar la asignación del empleado al proyecto
        empleado.getProyectosEmpleados().remove(ep); // Asumiendo que 'proyectos' es una lista en el modelo de empleado
        proyecto.getEmpleadosProyectos().remove(ep);
        
        // Guardar la actualización en la base de datos
        empleadoRepository.save(empleado);
        proyectoRepository.save(proyecto);
        empleadosProyectoRepository.delete(ep);
    }

}
