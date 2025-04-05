package com.example.vitech_FS.services.impl;

import com.example.vitech_FS.entitys.Empleado;
import com.example.vitech_FS.entitys.Proyectos;
import com.example.vitech_FS.repository.EmpleadosRepository;
import com.example.vitech_FS.repository.ProyectosRepository;
import com.example.vitech_FS.services.ProyectoService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class ProyectosServiceImpl implements ProyectoService {

    @Autowired  // Inyección de dependencia del repositorio de proyectos
    private ProyectosRepository proyectosRepository;


    @Override
    public List<Proyectos> findAll() {
        // Llama al metodo del repositorio para obtener todos los proyectos
        List<Proyectos> proyectos = proyectosRepository.findAll();
        log.info("Proyectos encontrados: " + proyectos.size());
        return proyectosRepository.findAll();
    }


    @Override
    public Proyectos findProjectById(Integer id) {
        Optional<Proyectos> existingProject = proyectosRepository.findById(id); // Busca el proyecto por su id_proyecto (id_proyecto)
        if (existingProject.isPresent()) {
            return existingProject.orElse(null);
        }
        return null;// Devuelve el proyecto si existe, o null si no se encuentra
    }

    @Override
    public Proyectos addProject(Proyectos p) {
        log.info("Adding project to Database ....  ");

        if (p.getId_proyecto() != null && proyectosRepository.existsById(p.getId_proyecto())) {
            log.info("Error: Project with ID {} already exists", p.getId_proyecto());
            return null;
        }
        Integer nextId = proyectosRepository.findMaxId(); // Método que obtiene el ID máximo de la tabla
        if (nextId == null) {
            nextId = 1; // Si la tabla está vacía, empieza desde el 1
        } else {
            nextId++; // Incrementa el ID para el nuevo empleado
        }
        p.setId_proyecto(nextId);
        Proyectos savedProject = proyectosRepository.save(p);
        log.info("Added project to Database with ID {}", savedProject.getId_proyecto());
        return savedProject;
    }

    @Override
    public void deleteProjectById(Integer id) {
        log.info("Deleting employee with id {}", id);

        Optional<Proyectos> projectOpt = proyectosRepository.findById(id);
        if (projectOpt.isPresent()) {
            proyectosRepository.deleteById(id);
            log.info("Project with id {} deleted from Database", id);
        } else {
            log.warn("Project with id {} does not exist in Database", id);
        }

    }

    @Override
    public Proyectos updateById(Integer id , Proyectos updatedProject)  {
        log.info("Updating employee to Database with id {}   ", id);
        Optional<Proyectos> projectOpt = proyectosRepository.findById(id);
        if(projectOpt.isPresent()){
            Proyectos existingProject = projectOpt.get();
            existingProject.setTx_descripción(updatedProject.getTx_descripción());
            existingProject.setF_inicio(updatedProject.getF_inicio());
            existingProject.setF_fin(updatedProject.getF_fin());
            existingProject.setF_baja(updatedProject.getF_baja());
            existingProject.setTx_lugar(updatedProject.getTx_lugar());
            existingProject.setTx_observaciones(updatedProject.getTx_observaciones());

            return proyectosRepository.save(existingProject);
        }
        return null;
    }
}
