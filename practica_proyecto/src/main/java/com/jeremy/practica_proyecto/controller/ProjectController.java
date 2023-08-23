package com.jeremy.practica_proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeremy.practica_proyecto.models.Project;
import com.jeremy.practica_proyecto.models.User;
import com.jeremy.practica_proyecto.services.ProjectService;
import com.jeremy.practica_proyecto.services.TaskService;
import com.jeremy.practica_proyecto.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ProjectController {
	private final ProjectService projectService;
	private final UserService userServ;
	private final TaskService taskService;
	public ProjectController(ProjectService pS, UserService uS, TaskService tS) {
		this.projectService = pS;
		this.userServ = uS;
		this.taskService = tS;
	}
	
	@GetMapping("project/new")
	public String nuevoProyecto(@ModelAttribute("proyecto")Project project,
			HttpSession sesion,  Model viewModel) {
		Long userId = (Long) sesion.getAttribute("userID");
		if (userId == null) {
			return "redirect:/";
		}
		User usuario = userServ.encontrarUserPorId(userId);
		viewModel.addAttribute("usuario", usuario);
		return "newproject.jsp";
	}

	@PostMapping("/project/new")
	public String crearEvento(
			@Valid @ModelAttribute("proyecto") Project project,
			BindingResult resultado,
			HttpSession sesion, Model viewModel) {
		// validar si la sesion del usuario esta activa
		Long userId = (Long) sesion.getAttribute("userID");
		if (userId == null) {
			return "redirect:/";
		}
		if (resultado.hasErrors()) {
			User usuario = userServ.encontrarUserPorId(userId);
			viewModel.addAttribute("usuario", usuario);
			return "newproject.jsp";
		}
		User usuario = userServ.encontrarUserPorId(userId);
		viewModel.addAttribute("usuario", usuario);
		projectService.crearUnProject(project);
		return "redirect:/dashboard";
	}

	// EDITAR EVENTO
	@GetMapping("/project/edit/{idProject}")
	public String formEdicionEvento(
			@PathVariable("idProject") Long idProject,
			@ModelAttribute("proyecto") Project project,
			HttpSession sesion,
			Model viewModel) {
		// validar si la sesion del usuario esta activa
		Long userId = (Long) sesion.getAttribute("userID");
		if (userId == null) {
			return "redirect:/";
		}
		Project unProject = projectService.unProject(idProject);
		if (unProject == null) {
			return "redirect:/project";
		}
		User usuario = userServ.encontrarUserPorId(userId);
		viewModel.addAttribute("usuario", usuario);
		viewModel.addAttribute("proyecto", unProject);

		return "editproject.jsp";

	}

	@PutMapping("/project/edit/{id}")
	public String editarEvento(
		@Valid @ModelAttribute("proyecto") Project project,
			BindingResult resultado,
			@PathVariable("id") Long idProject,
			HttpSession sesion, Model viewModel) {
		// validar si la sesion del usuario esta activa
		Long userId = (Long) sesion.getAttribute("userID");
		if (userId == null) {
			return "redirect:/";
		}
		User usuario = userServ.encontrarUserPorId(userId);
		if (resultado.hasErrors()) {
			viewModel.addAttribute("usuario", usuario);
			return "editproject.jsp";
		}
		projectService.actualizarUnProject(project);
		return "redirect:/dashboard";
	}

	@DeleteMapping("/project/{id}/delete")
	public String borrarEvento(@PathVariable("id") Long idProject) {
		projectService.borrar(idProject);
		return "redirect:/dashboard";
	}

//	 Unirme/Cancelar al evento
	@GetMapping("/project/{idProject}/{idUsuario}/{opcion}")
	public String asistirCancelarEvento(@PathVariable("idProject") Long idProject,
			@PathVariable("idUsuario") Long idUsuario,
			@PathVariable("opcion") String opcion,
			HttpSession sesion) {
		// validar si la sesion del usuario esta activa
		Long userId = (Long) sesion.getAttribute("userID");
		if (userId == null) {
			return "redirect:/";
		}
		Project unProject = projectService.unProject(idProject);
		boolean unirseDejar = (opcion.equals("unirse"));
		User usuario = userServ.encontrarUserPorId(userId);

		projectService.unirseDejarProject(unProject, usuario, unirseDejar);

		return "redirect:/dashboard";
	}

	@GetMapping("/project/{idProject}")
	public String mostrarProject(Model viewModel,
			@PathVariable("idProject") Long idProject,
			HttpSession sesion) {
		// validar si la sesion del usuario esta activa
		Long userId = (Long) sesion.getAttribute("userID");
		if (userId == null) {
			return "redirect:/";
		}
		User usuario = userServ.encontrarUserPorId(userId);
		viewModel.addAttribute("usuario", usuario);
		viewModel.addAttribute("proyecto", projectService.unProject(idProject));
		return "showproject.jsp";
	}
	
	@GetMapping("/projects/{idProject}/tasks")
	public String tareas(Model model,
			@PathVariable("idProject") Long idProject,
			HttpSession sesion) {
		// validar si la sesion del usuario esta activa
		Long userId = (Long) sesion.getAttribute("userID");
		if (userId == null) {
			return "redirect:/";
		}
	    Project unProject = projectService.unProject(idProject);
	    model.addAttribute("proyecto", unProject);
		model.addAttribute("tarea", projectService.tareaProyecto(idProject));
		
		return "task.jsp";
	}
	
	@PostMapping("/projects/{idProject}/tasks")
	public String agregartarea(@PathVariable("idProject") Long idProject,
			@RequestParam("task") String tarea,HttpSession sesion ,
			 RedirectAttributes errores) {

		// validar si la sesion del usuario esta activa
		Long userId = (Long) sesion.getAttribute("userID");
		if (userId == null) {
			return "redirect:/";
		}
		if(tarea.isBlank()||tarea.isEmpty()) {
			errores.addFlashAttribute("errores", "Por favor no envíes mensajes vacíos");
			return "redirect:/projects/{idProject}/tasks";
		}
		Project unProject = projectService.unProject(idProject);
		User usuario = userServ.encontrarUserPorId(userId);
		
		projectService.agregarTarea(usuario, unProject, tarea);

		return "redirect:/projects/"+idProject+"/tasks";
	}
	
}
