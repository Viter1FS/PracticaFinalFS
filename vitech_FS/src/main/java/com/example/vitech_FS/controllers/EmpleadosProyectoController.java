package com.example.vitech_FS.controllers;

import com.example.vitech_FS.entitys.Empleados_proyecto;
import com.example.vitech_FS.services.EmpleadoService;
import com.example.vitech_FS.services.Empleado_ProyectoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // Indica que esta clase es un controlador REST
@RequestMapping("/empleado_proyecto")
@Slf4j
public class EmpleadosProyectoController {

    @Autowired  // Inyección de dependencia para usar el servicio Empleado_ProyectoService
    Empleado_ProyectoService empleProyService;

    @Autowired  // Inyección de dependencia para usar el servicio Empleado_ProyectoService
    EmpleadoService empleadoService;


    @GetMapping("/all")
    @ResponseBody  // Indica que el valor de retorno se serializará directamente en la respuesta HTTP
    public List<Empleados_proyecto> getAll() {
        return empleProyService.findAll();
    }



    @PostMapping("/{projectId}/assignProject")
    public ResponseEntity<?> assignProjectsToEmployees(
            @PathVariable Integer projectId,
            @RequestBody List<Integer> employeeIds // Recibimos una lista de IDs de empleados
    ) {
        try {
            // Llamamos al servicio para asignar el proyecto a cada empleado
            for (Integer employeeId : employeeIds) {
                empleProyService.assignProjectToEmployee(employeeId, projectId);
            }
            return ResponseEntity.ok("Empleados asignados al proyecto con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al asignar el proyecto a los empleados.");
        }
    }


    @PostMapping("/{projectId}/removeProject")
    public ResponseEntity<?> removeProjectsFromEmployees(
            @PathVariable Integer projectId,
            @RequestBody List<Integer> employeeIds // Recibimos la lista de IDs de empleados
    ) {
        try {
            // Llamamos al servicio para desasignar el proyecto de cada empleado
            for (Integer employeeId : employeeIds) {
                empleProyService.removeProjectFromEmployee(employeeId, projectId);
            }
            return ResponseEntity.ok("Empleados desasignados del proyecto con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al desasignar el proyecto de los empleados.");
        }
    }





}
