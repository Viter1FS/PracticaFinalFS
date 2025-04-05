package com.example.vitech_FS.services.impl;

import com.example.vitech_FS.entitys.Empleado;
import com.example.vitech_FS.entitys.Empleados_proyecto;
import com.example.vitech_FS.repository.EmpleadosRepository;
import com.example.vitech_FS.repository.Empleados_ProyectoRepository;
import com.example.vitech_FS.services.EmpleadoService;
import com.example.vitech_FS.services.Empleado_ProyectoService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Transactional
public class Empleado_ProyectoServiceImpl implements Empleado_ProyectoService {

    @Autowired
    private Empleados_ProyectoRepository empleadosProyectoRepository;



    @Override
    public List<Empleados_proyecto> findAll() {
        // Llama al metodo del repositorio para obtener todos los empelados
        return empleadosProyectoRepository.findAll();
    }
}
