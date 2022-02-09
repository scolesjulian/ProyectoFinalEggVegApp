package com.vegapp.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vegapp.entity.Usuario;
import com.vegapp.entity.Zona;
import com.vegapp.errors.ErrorService;
import com.vegapp.repository.UsuarioRepository;
import com.vegapp.repository.ZonaRepository;
import com.vegapp.service.UsuarioService;
import com.vegapp.service.ZonaService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	public UsuarioService usuarioService;
	
	@Autowired
	public UsuarioRepository usuarioRepository;
	
	@Autowired
	public ZonaRepository zonaRepository;
	
	@Autowired
	public ZonaService zonaService;
	

	@GetMapping("/registro")
	public String registro(Model modelo) {
		modelo.addAttribute("zona", new Zona());
		modelo.addAttribute("provincias", this.zonaService.getProvicias());
		
		
		return "sign-in.html";
	}

	@PostMapping("/registrar")
	public String registrar(Model modelo,MultipartFile archivo,@RequestParam String nombre, @RequestParam String apellido,@RequestParam Integer edad,
			@RequestParam String email,@RequestParam String clave1,@RequestParam String clave2,@ModelAttribute Zona zona ) {
		try {
			zonaService.guardarZona(zona);
			usuarioService.crear(archivo,nombre,apellido,edad,email,clave1,clave2,zona.getId());
		} catch (ErrorService ex) {
			modelo.addAttribute("error",ex.getMessage());
			modelo.addAttribute("nombre",nombre);
			modelo.addAttribute("apellido",apellido);
			modelo.addAttribute("edad",edad);
			modelo.addAttribute("clave1",clave1);
			modelo.addAttribute("clave2",clave2);
			modelo.addAttribute("zona",zona);
			ex.printStackTrace();
			
			
			return "sign-in.html";
		}
		modelo.addAttribute("titulo","Bienvenido a Vegapp");
		modelo.addAttribute("descripcion","Tu usuario fue registrado de manera satisfactoria");
		return "redirect:/";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
	@GetMapping("/editar-perfil")
	public String editarPerfil(HttpSession session,@RequestParam(required=false) String error,
			@RequestParam String id,ModelMap model) {
		
		
		if(error!=null) {
			model.put("error", "Error al ingresar al perfil");
		}
		
		Usuario login= (Usuario) session.getAttribute("usuariosession");
		if(login==null||!login.getId().equals(id)) {
			return "redirect:/inicio";
		}
		
		try {
			Usuario usuario = usuarioRepository.findById(id).get();
			model.addAttribute("perfil",usuario);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error",e.getMessage());
		}
		return "perfil.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USUARIO')")
	@PostMapping("/actualizar-perfil")
	public String actualizarPerfil(ModelMap modelo, HttpSession session,MultipartFile archivo,@RequestParam String id,@RequestParam String nombre, 
			@RequestParam String apellido,@RequestParam Integer edad,@RequestParam String email,@RequestParam String clave1,
			@RequestParam String clave2) {
		Usuario usuario=null;
		try {
			/*Usuario login= (Usuario) session.getAttribute("usuariosession");
			if(login==null||!login.getId().equals(id)) {
				return "redirect:/inicio";
			}*/
			usuario =usuarioRepository.findById(id).get();
			
			usuarioService.editar(archivo,id,nombre,apellido,edad,email,clave1);
			session.setAttribute("usuariosession", usuario);
			return "redirect:/inicio";
		} catch (ErrorService e) {
			e.printStackTrace();
			modelo.put("error", e.getMessage());
			modelo.put("perfil", usuario);
			return "perfil.html";
		}
	}
	
	@GetMapping("/eliminar")
	public String eliminar(ModelMap modelo,@RequestParam String id) {
		try {
			usuarioService.eliminar(id);
		} catch (Exception e) {
			e.getMessage();
		}
		return "redirect:/usuario/registro";
	}
	
}
