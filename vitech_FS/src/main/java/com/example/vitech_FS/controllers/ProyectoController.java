package com.example.vitech_FS.controllers;

import com.example.vitech_FS.entitys.Empleado;
import com.example.vitech_FS.entitys.Proyectos;
import com.example.vitech_FS.services.EmpleadoService;
import com.example.vitech_FS.services.ProyectoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController  // Indica que esta clase es un controlador REST
@RequestMapping("/proyectos")
@Slf4j
public class ProyectoController {


    @Autowired  // Inyección de dependencia para usar el servicio EmpleadoService
    ProyectoService proyectoService;

    /**
     * Endpoint para obtener todos los proyectos.
     * @return Lista de todos los proyectos almacenados.
     */
    @GetMapping("/all")
    @ResponseBody  // Indica que el valor de retorno se serializará directamente en la respuesta HTTP
    public List<Proyectos> getAll() {
        return proyectoService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody  // Indica que el valor de retorno se serializará directamente en la respuesta HTTP
    public ResponseEntity<?> getProjectById(@PathVariable Integer id) {
        log.info("Retriving project Info - get project");
        try {
            return ResponseEntity.ok(proyectoService.findProjectById(id));
        } catch (Exception e) {
            log.info("Retriving project Info - not found project");
            return  ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/addProject")
    public ResponseEntity<?> addProject(@RequestBody Proyectos proyecto) {
        log.info("Intentando agregar proyecto con ID: {}", proyecto.getId_proyecto());

        try {
            Proyectos newProyect = proyectoService.addProject(proyecto);

            if (newProyect == null) {
                log.warn("El proyecto con ID {} ya existe en la base de datos.", proyecto.getId_proyecto());
                return ResponseEntity.badRequest().body("El proyecto ya existe.");
            }

            return ResponseEntity.ok(newProyect);
        } catch (Exception e) {
            log.error("Error al agregar proyecto: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error al guardar el proyecto.");
        }
    }




}
