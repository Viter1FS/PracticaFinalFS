package com.example.vitech_FS.controllers;

import com.example.vitech_FS.entitys.Proyectos;
import com.example.vitech_FS.services.ProyectoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

    @GetMapping("/export/csv")
    public ResponseEntity<?> exportCsv() {
        List<Proyectos> proyectos = proyectoService.findAll();
        String ruta = "../data_csv/proyectos.csv"; // Ruta relativa fuera del proyecto
        File archivo = new File(ruta);

        // Crea la carpeta si no existe
        archivo.getParentFile().mkdirs();

        try (PrintWriter writer = new PrintWriter(archivo)) {
            StringBuilder sb = new StringBuilder();

            // Cabecera del CSV
            sb.append("id_proyecto,tx_descripción,f_inicio,f_fin,f_baja,tx_lugar,tx_observaciones\n");

            // Datos del CSV
            for (Proyectos proyecto : proyectos) {
                sb.append(proyecto.getId_proyecto()).append(",");  // ID del proyecto
                sb.append(proyecto.getTx_descripción()).append(",");  // Descripción
                sb.append(proyecto.getF_inicio()).append(",");  // Fecha de inicio
                sb.append(proyecto.getF_fin()).append(",");  // Fecha de fin
                sb.append(proyecto.getF_baja()).append(",");  // Fecha de baja
                sb.append(proyecto.getTx_lugar()).append(",");  // Lugar
                sb.append(proyecto.getTx_observaciones()).append("\n");  // Observaciones
            }

            writer.write(sb.toString());
            return ResponseEntity.ok("CSV de proyectos exportado correctamente a: " + archivo.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al exportar CSV: " + e.getMessage());
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
