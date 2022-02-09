package com.vegapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vegapp.entity.Usuario;
import com.vegapp.repository.UsuarioRepository;
import com.vegapp.service.UsuarioService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
		
	@GetMapping("/dashboard")
	public String inicioAdmin(ModelMap modelo) {
		
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		modelo.put("usuarios", usuarios);
		
		return "inicioAdmin";
	}
	
	@GetMapping("/habilitar/{id}")
	public String habilitar(ModelMap modelo, @PathVariable String id) {
		try {
			usuarioService.habilitar(id);
			return "redirect:/admin/dashboard";
		}catch(Exception e) {
			modelo.put("error", "No fue posible habilitar");
			return "inicioAdmin";
		}
	}
	
	@GetMapping("/deshabilitar/{id}")
	public String deshabilitar(ModelMap modelo, @PathVariable String id) {
		try {
			usuarioService.deshabilitar(id);
			return "redirect:/admin/dashboard";
		}catch(Exception e) {
			modelo.put("error", "No fue posible deshabilitar");
			return "inicioAdmin";
		}
	}
	
	@GetMapping("/cambiar_rol/{id}")
	public String cambiarRol(ModelMap modelo, @PathVariable String id) {
		try {
			usuarioService.cambiarRol(id);
			modelo.put("exito", "Rol modificado con Exito!");
			return "redirect:/admin/dashboard";
		}catch(Exception e) {
			modelo.put("error", "No fue posible reasignar el rol");
			return "inicioAdmin";
		}
	}
	
	
}
