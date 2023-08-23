package com.jeremy.practica_proyecto.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

	@Entity
	@Table(name = "tasks")
	public class Tasks {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@NotBlank(message = "Por favor ingresa un mensaje")
		@Column(columnDefinition = "text")
		private String tareas;

		@Column(updatable = false)
		private Date createdAt;
		private Date updatedAt;

		// Relaciones tabla intermedia
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "user_id")
		private User autor;

		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "project_id") // Nombre de la columna en la tabla Tasks que hace referencia al proyecto
	    private Project project;

		public Tasks() {
			
		}
		
		public Tasks(User autor, Project project, String task) {
		    this.autor = autor;
		    this.project = project;
		    this.tareas = task;
		}
		
		
		
		public Long getId() {
			return id;
		}



		public void setId(Long id) {
			this.id = id;
		}



		public String getTareas() {
			return tareas;
		}



		public void setTareas(String tareas) {
			this.tareas = tareas;
		}



		public Date getCreatedAt() {
			return createdAt;
		}



		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}



		public Date getUpdatedAt() {
			return updatedAt;
		}



		public void setUpdatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
		}



		public User getAutor() {
			return autor;
		}



		public void setAutor(User autor) {
			this.autor = autor;
		}







		public Project getProject() {
			return project;
		}

		public void setProject(Project project) {
			this.project = project;
		}

		@PrePersist
		protected void onCreate() {
			this.createdAt = new Date();
		}

		@PreUpdate
		protected void onUpdate() {
			this.updatedAt = new Date();
		}
		
}
