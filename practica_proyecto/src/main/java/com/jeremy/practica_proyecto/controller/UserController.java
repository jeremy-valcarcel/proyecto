package com.jeremy.practica_proyecto.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.jeremy.practica_proyecto.models.LogReg;
import com.jeremy.practica_proyecto.models.Project;
import com.jeremy.practica_proyecto.models.User;
import com.jeremy.practica_proyecto.services.ProjectService;
import com.jeremy.practica_proyecto.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	
	private final UserService userServ;
	private final ProjectService projectService;
	public UserController(UserService uSer, ProjectService pS) {
		this.userServ = uSer;
		this.projectService = pS;
	}
	
	@GetMapping("/")
	public String raiz(Model viewModel) {
		viewModel.addAttribute("user", new User());
		viewModel.addAttribute("login", new LogReg());
		return "loginreg.jsp";
	}
	
	
	@PostMapping("/registration")
	public String registro(@Valid @ModelAttribute("user") User usuario,
			BindingResult resultado, Model viewModel ) {
		if(resultado.hasErrors()) {
			viewModel.addAttribute("login", new LogReg());
			return "loginreg.jsp";
		}
		User usuarioRegistrado = userServ.registroUsuario(usuario, resultado);
		viewModel.addAttribute("login", new LogReg());
		if(usuarioRegistrado != null) {
			viewModel.addAttribute("registro", "Gracias por registrarte, ahora login por favor");
		}
		return "loginreg.jsp";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("login") LogReg loginuser,
			BindingResult resultado, Model viewModel, HttpSession sesion) {
		if (resultado.hasErrors()) {
			viewModel.addAttribute("user", new User());
			return "loginreg.jsp";
		}
		
		if(userServ.authenthicateUser(
				loginuser.getEmail(), 
				loginuser.getPassword(), 
				resultado )) {
			User usuarioLog = userServ.encontrarPorEmail(loginuser.getEmail());
			sesion.setAttribute("userID",usuarioLog.getId());
			return "redirect:/dashboard";
		}else {
			viewModel.addAttribute("errorLog", "Por favor intenta de nuevo");
			viewModel.addAttribute("user", new User());
			return "loginreg.jsp";
		}
		
	}
	
	@GetMapping("/dashboard")
	public String bienvenida(
	        @ModelAttribute("project") Project proyecto,
	        BindingResult resultado,
	        HttpSession sesion, Model viewModel) {
		Long userId =  (Long) sesion.getAttribute("userID");
		if(userId == null ) {
			return "redirect:/";
		}
	    User usuario = userServ.encontrarUserPorId(userId);

	    // Obtener proyectos en los que el usuario está participando (unido)
	    List<Project> proyectosParticipando = projectService.projectoParticipando(usuario);

	    // Obtener proyectos en los que el usuario no está participando
	    List<Project> proyectosNoParticipando = projectService.projectoNotParticipando(usuario);

	    viewModel.addAttribute("usuario", usuario);
	    viewModel.addAttribute("proyectosParticipando", proyectosParticipando);
	    viewModel.addAttribute("proyectosNoParticipando", proyectosNoParticipando);
	    return "dashboard.jsp";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession sesion) {
		sesion.setAttribute("userID", null);
		return "redirect:/";
	}
	


}
