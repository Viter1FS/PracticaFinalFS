package com.example.vitech_FS.controllers;

import com.example.vitech_FS.entitys.Empleados_proyecto;
import com.example.vitech_FS.services.EmpleadoService;
import com.example.vitech_FS.services.Empleado_ProyectoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
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
        System.out.println("Proyecto: " + projectId);
        System.out.println("Empleados a desasignar: " + employeeIds);
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

    @GetMapping("/export/csv")
    public ResponseEntity<?> exportCSV() {
        List<Empleados_proyecto> empleadosProyectos = empleProyService.findAll();  // Asumimos que ya tienes el servicio configurado.

        // Usamos StringBuilder para construir el CSV
        StringBuilder sb = new StringBuilder();
        sb.append("id_empleado,id_proyecto,f_alta,tx_descripción,f_inicio,f_fin,f_baja,tx_lugar,tx_observaciones\n");

        // Iteramos sobre los empleados y proyectos y los añadimos al StringBuilder
        for (Empleados_proyecto empProy : empleadosProyectos) {
            sb.append(empProy.getEmpleado().getId_empleado()).append(",");  // ID del empleado
            sb.append(empProy.getProyectos().getId_proyecto()).append(",");  // ID del proyecto
            sb.append((empProy.getF_alta())).append(",");
            sb.append(empProy.getProyectos().getTx_descripción()).append(",");  // Descripción del proyecto
            sb.append(empProy.getProyectos().getF_inicio()).append(",");  // Fecha de inicio del proyecto
            sb.append(empProy.getProyectos().getF_fin()).append(",");  // Fecha de fin del proyecto
            sb.append(empProy.getProyectos().getF_baja()).append(",");  // Fecha de baja del proyecto
            sb.append(empProy.getProyectos().getTx_lugar()).append(",");  // Lugar del proyecto
            sb.append(empProy.getProyectos().getTx_observaciones()).append("\n");  // Observaciones del proyecto
        }

        // Guardamos el contenido en un archivo CSV
        try {
            // La ruta donde se guardará el archivo
            String ruta = "../data_csv/empleados_proyectos.csv";
            FileWriter fileWriter = new FileWriter(ruta);
            fileWriter.write(sb.toString());
            fileWriter.close();

            return ResponseEntity.ok("Archivo CSV generado correctamente en " + ruta);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al generar el archivo CSV: " + e.getMessage());
        }
    }



}
