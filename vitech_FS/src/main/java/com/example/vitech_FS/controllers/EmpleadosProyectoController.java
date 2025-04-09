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



    @PostMapping("/{id}/assignProject/{projectId}")
    public ResponseEntity<?> assignProjectToEmployee(@PathVariable Integer id, @PathVariable Integer projectId) {
        try {
            empleProyService.assignProjectToEmployee(id, projectId);
            return ResponseEntity.ok("Empleado asignado al proyecto con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al asignar el proyecto al empleado.");
        }
    }





}
