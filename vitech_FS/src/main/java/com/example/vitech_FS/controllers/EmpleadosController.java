package com.example.vitech_FS.controllers;



import com.example.vitech_FS.entitys.Empleado;
import com.example.vitech_FS.services.EmpleadoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // Indica que esta clase es un controlador REST
@RequestMapping("/empleados")
@Slf4j
public class EmpleadosController {
    //comit , de testeo

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
    public ResponseEntity<?> addEmployee(@RequestBody Empleado employee) {
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
    public ResponseEntity<Empleado> updateEmployee(@PathVariable Integer id, @RequestBody Empleado updatedEmpleado) {
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






}
