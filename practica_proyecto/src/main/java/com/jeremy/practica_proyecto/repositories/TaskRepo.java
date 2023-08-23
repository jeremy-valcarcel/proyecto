package com.jeremy.practica_proyecto.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jeremy.practica_proyecto.models.Tasks;

@Repository
public interface TaskRepo extends CrudRepository<Tasks, Long> {

//	Tasks findTareasByPoryectosId(Long projectId);
	
}
