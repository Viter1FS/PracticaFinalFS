package com.example.vitech_FS.controllers;

import com.example.vitech_FS.entitys.Empleado;
import com.example.vitech_FS.entitys.Empleados_proyecto;
import com.example.vitech_FS.services.EmpleadoService;
import com.example.vitech_FS.services.Empleado_ProyectoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController  // Indica que esta clase es un controlador REST
@RequestMapping("/empleado_proyecto")
@Slf4j
public class EmpleadosProyectoController {

    @Autowired  // Inyección de dependencia para usar el servicio Empleado_ProyectoService
    Empleado_ProyectoService empleProyService;


    @GetMapping("/all")
    @ResponseBody  // Indica que el valor de retorno se serializará directamente en la respuesta HTTP
    public List<Empleados_proyecto> getAll() {
        return empleProyService.findAll();
    }



}
