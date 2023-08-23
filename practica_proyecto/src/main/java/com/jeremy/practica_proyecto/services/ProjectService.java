package com.jeremy.practica_proyecto.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jeremy.practica_proyecto.models.Project;
import com.jeremy.practica_proyecto.models.Tasks;
import com.jeremy.practica_proyecto.models.User;
import com.jeremy.practica_proyecto.repositories.ProjectRepo;
import com.jeremy.practica_proyecto.repositories.TaskRepo;

@Service
public class ProjectService {
	private final ProjectRepo projectRepo;
	private final TaskRepo taskRepo;
	
	public  ProjectService(ProjectRepo pR, TaskRepo tR) {
		this.projectRepo = pR;
		this.taskRepo = tR;
	}
	
	public Project crearUnProject(Project project) {
		return projectRepo.save(project);
	}
	
	public Project actualizarUnProject(Project project) {
		return projectRepo.save(project);
	}
	
	public void borrar(Long id) {
		projectRepo.deleteById(id);
	}
	
	public void unirseDejarProject(Project project, User usuario, boolean ayudante) {
		if(ayudante) {
			project.getAyudantes().add(usuario);			
		}else {
			project.getAyudantes().remove(usuario);	
		}
		projectRepo.save(project);
	}
	
	public Project unProject(Long id) {
		return projectRepo.findById(id).orElse(null);
	}
	
	public List<Project> projectoParticipando(User usuario) {
        // Obtener proyectos en los que el usuario es el encargado (creador)
        List<Project> proyectosEncargado = projectRepo.findByEncargado(usuario);

        // Obtener proyectos en los que el usuario es un ayudante (participante)
        List<Project> proyectosAyudante = projectRepo.findByAyudantesContaining(usuario);

        // Combinar las listas de proyectos del usuario (creador y participante)
        List<Project> proyectosParticipando = new ArrayList<>(proyectosEncargado);
        proyectosParticipando.addAll(proyectosAyudante);

        return proyectosParticipando;
	}
	
	public List<Project> projectoNotParticipando(User usuario) {
		return projectRepo.findByAyudantesNotContaining(usuario);
		
		
		
	}
	
	public List<Project> ayudandoProyecto(User usuario){
		return projectRepo.findByAyudantesContaining(usuario);
	}
	
	public List<Project> noAyudandoProyecto(User usario){
		return projectRepo.findByAyudantesNotContaining(usario);
	}
	
	public Tasks tareaProyecto(Long projectId){
		return taskRepo.findById(projectId).orElse(null);
	}
	
	public void agregarTarea(User usuario, Project proyecto, String task) {
		Tasks tarea = new Tasks(usuario, proyecto, task);
		taskRepo.save(tarea);
	}
	
}
