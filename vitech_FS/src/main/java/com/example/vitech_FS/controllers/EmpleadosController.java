package com.example.vitech_FS.controllers;



import com.example.vitech_FS.entitys.Empleado;
import com.example.vitech_FS.entitys.Proyectos;
import com.example.vitech_FS.services.EmpleadoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

@RestController  // Indica que esta clase es un controlador REST
@RequestMapping("/empleados")
@Slf4j
public class EmpleadosController {


    @Autowired  // Inyección de dependencia para usar el servicio EmpleadoService
    EmpleadoService empleadoService;

    /**
     * Endpoint para obtener todos los empleados.
     * @return Lista de todos los empleados almacenados.
     */
    @GetMapping("/all")
    @ResponseBody  // Indica que el valor de retorno se serializará directamente en la respuesta HTTP
    public List<Empleado> getAll() {
        return empleadoService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseBody  // Indica que el valor de retorno se serializará directamente en la respuesta HTTP
    public ResponseEntity<?> getEmployeeById(@PathVariable  Integer id) {
        log.info("Retriving employee Info - get employee");
        try {
            return ResponseEntity.ok(empleadoService.findEmployeeById(id));
        } catch (Exception e) {
            log.info("Retriving employee Info - not found employee");
            return  ResponseEntity.notFound().build();
        }

    }


    @PostMapping("/addEmployee")
    public ResponseEntity<?> addEmployee (@Valid @RequestBody Empleado employee) {
        log.info("Intentando agregar empleado con ID: {}", employee.getId_empleado());

        try {
            Empleado newEmployee = empleadoService.addEmployee(employee);

            if (newEmployee == null) {
                log.warn("El empleado con ID {} ya existe en la base de datos.", employee.getId_empleado());
                return ResponseEntity.badRequest().body("El empleado ya existe.");
            }

            return ResponseEntity.ok(newEmployee);
        } catch (Exception e) {
            log.error("Error al agregar empleado: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error al guardar el empleado.");
        }
    }

    @DeleteMapping ("/deleteEmployee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable  Integer id) {
        log.info("Retriving Employee Info - delete employee");
        try {
            empleadoService.deleteEmployeeById(id);
            return ResponseEntity.ok().build();

        }catch (Exception e){
            return  ResponseEntity.notFound().build();
        }

    }

    /**
     * Endpoint para actualizar un empleado por su id.
     * @param id ID del empleado a actualizar.
     * @param updatedEmpleado El objeto con los nuevos datos del empleado.
     * @return El empleado actualizado o una respuesta de error si no se encuentra.
     */
    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Empleado> updateEmployee(@PathVariable Integer id, @Valid @RequestBody Empleado updatedEmpleado) {
        log.info("Updating employee with id {}", id);
        try {
            // Llamamos al servicio para actualizar el empleado
            Empleado updated = empleadoService.updateById(id, updatedEmpleado);

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
    public ResponseEntity<String> exportCSV() {
        List<Empleado> empleados = empleadoService.findAll();
        String ruta = "../data_csv/empleados.csv"; // Cambia esto según tu sistema
        File file = new File(ruta);

        // Esto crea la carpeta si no existe
        file.getParentFile().mkdirs();
        try (PrintWriter writer = new PrintWriter(new File(ruta))) {
            StringBuilder sb = new StringBuilder();
            sb.append("id_empleado,tx_nif,tx_nombre,tx_apellido1,tx_apellido2,f_nacimiento,n_telefono1,n_telefono2,tx_email,f_alta,f_baja,cx_edocivil,b_formacionu\n");

            for (Empleado emp : empleados) {
                sb.append(emp.getId_empleado()).append(",");
                sb.append(emp.getTx_nif()).append(",");
                sb.append(emp.getTx_nombre()).append(",");
                sb.append(emp.getTx_apellido1()).append(",");
                sb.append(emp.getTx_apellido2()).append(",");
                sb.append(emp.getF_nacimiento()).append(",");
                sb.append(emp.getN_telefono1()).append(",");
                sb.append(emp.getN_telefono2()).append(",");
                sb.append(emp.getTx_email()).append(",");
                sb.append(emp.getF_alta()).append(",");
                sb.append(emp.getF_baja()).append(",");
                sb.append(emp.getCx_edocivil()).append(",");
                sb.append(emp.getB_formacionu()).append("\n");
            }

            writer.write(sb.toString());
            System.out.println("CSV exportado correctamente a: " + ruta);
        } catch (Exception e) {
            System.err.println("Error al guardar CSV: " + e.getMessage());
        }
        return ResponseEntity.ok("CSV generado correctamente");
    }


//    @GetMapping("/{id}/projects")
//    public ResponseEntity<?> getProjectsByEmployee(@PathVariable Integer id) {
//        try {
//            List<Proyectos> projects = empleadoService.getProjectsByEmployee(id);
//            return ResponseEntity.ok(projects);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error al obtener los proyectos del empleado.");
//        }
//    }










}
