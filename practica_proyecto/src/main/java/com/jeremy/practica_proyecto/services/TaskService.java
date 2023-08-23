package com.jeremy.practica_proyecto.services;

import org.springframework.stereotype.Service;

import com.jeremy.practica_proyecto.models.Project;
import com.jeremy.practica_proyecto.models.Tasks;
import com.jeremy.practica_proyecto.models.User;
import com.jeremy.practica_proyecto.repositories.TaskRepo;

@Service
public class TaskService {
	private final TaskRepo taskRepo;
	public TaskService(TaskRepo tR) {
		this.taskRepo = tR;
	}
	

//	public void agregarTarea(User usuario, Project proyecto, String task) {
//		Tasks tarea = new Tasks(usuario, proyecto, task);
//		taskRepo.save(tarea);
//	}
}
