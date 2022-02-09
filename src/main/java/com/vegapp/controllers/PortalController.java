package com.vegapp.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalController {

	@GetMapping("/")
	public String inicioVegapp() {
		return "index.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
	@GetMapping("/inicio")
	public String inicio() {
		return "inicio.html";
	}
	
	@GetMapping("/login")
	public String login(@RequestParam(required=false) String error,@RequestParam(required=false) String logout,ModelMap modelo) {
		if(error!=null) {
			modelo.put("error", "Usuario o clave incorrectos");
		}
		if(logout!=null) {
			modelo.put("logout", "Ha salido correctamente de la plataforma");
		}
		return "login.html";
	}
	
}
