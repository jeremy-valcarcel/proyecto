package com.jeremy.practica_proyecto.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jeremy.practica_proyecto.models.Project;
import com.jeremy.practica_proyecto.models.User;

@Repository
public interface ProjectRepo extends CrudRepository<Project, Long>{

	void save(boolean ayudante);
	
	List<Project> findByAyudantesContaining(User user);	
	
	List<Project> findByAyudantesNotContaining(User user);
	
	List<Project> findByEncargado(User user);
	
	
}
