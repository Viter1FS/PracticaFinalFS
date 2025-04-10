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

    @DeleteMapping ("/deleteProject/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable  Integer id) {
        log.info("Retriving Employee Info - delete project");
        try {
            proyectoService.deleteProjectById(id);
            return ResponseEntity.ok().build();

        }catch (Exception e){
            return  ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/updateProject/{id}")
    public ResponseEntity<Proyectos> updateEmployee(@PathVariable Integer id, @RequestBody Proyectos updatedProject) {
        log.info("Updating project with id {}", id);
        try {
            // Llamamos al servicio para actualizar el proyecto
            Proyectos updated = proyectoService.updateById(id, updatedProject);

            if (updated != null) {
                // Si el empleado se actualizó correctamente
                return ResponseEntity.ok(updated);
            } else {
                // Si el empleado no existe, retornamos un error 404
                return ResponseEntity.notFound().build();
            }

        }catch (Exception e){
            // Si el empleado no existe, retornamos un error 404
            return  ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/projects/{projectId}/employees")
//    public ResponseEntity<?> getEmployeesByProject(@PathVariable Integer projectId) {
//        try {
//            List<Empleado> employees = empleadoService.getEmployeesByProject(projectId);
//            return ResponseEntity.ok(employees);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error al obtener los empleados del proyecto.");
//        }
//    }




}
