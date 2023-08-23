package com.jeremy.practica_proyecto.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = " Por favor agrege un titulo")
	@Size(min = 3, max = 30, message = "El titulo debe ser mayor a 3 caracteres y menor a 30")
	private String title;

	@NotBlank
	@Size(min = 3, message = "Por favro agrege una descripcion")
	private String description;

	@Future(message = "Por favor ingresa una fecha posterior")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Por favor ingresa una fecha")
	private Date date;

	@Column(updatable = false)
	private Date createdAt;
	private Date updatedAt;

	// rELACION 1:N hacia Usuario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User encargado;

	// Relacion n:n Eventos a Usuarios
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ayudantes",
	joinColumns = @JoinColumn(name = "project_id"),
	inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> ayudantes;

	  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Tasks> tasks = new ArrayList<>();

	public Project() {

	}
	
	




	public List<Tasks> getTasks() {
		return tasks;
	}






	public void setTasks(List<Tasks> tasks) {
		this.tasks = tasks;
	}






	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public User getEncargado() {
		return encargado;
	}

	public void setEncargado(User encargado) {
		this.encargado = encargado;
	}

	public List<User> getAyudantes() {
		return ayudantes;
	}

	public void setAyudantes(List<User> ayudantes) {
		this.ayudantes = ayudantes;
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
