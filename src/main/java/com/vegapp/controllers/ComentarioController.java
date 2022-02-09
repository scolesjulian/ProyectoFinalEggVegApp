package com.vegapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegapp.entity.Comentario;
import com.vegapp.entity.Usuario;
import com.vegapp.service.ComentarioService;

@Controller
@RequestMapping("/comentario")
public class ComentarioController {

	@Autowired
	private ComentarioService comentarioService;
	
	@GetMapping("/guardar-comentario")
	public String cargarComentario(ModelMap modelo) {
		try {
			modelo.put("comentario", new Comentario());
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-comentario";
	}
	
	@PostMapping("/guardar")
	public String guardar(ModelMap modelo, @ModelAttribute Comentario comentario ) {
		try {
			modelo.put("comentario", comentario);
			comentarioService.guardarComentario(comentario);
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-comentario";
	}
	
	@GetMapping("/editar-comentario/{id}")
	public String editarComentario(ModelMap modelo,  @ModelAttribute Comentario comentario,  @RequestParam("id") String id ) {
		try {
			modelo.put("comentario", comentario);			
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-comentario";
	}
	
	@PostMapping("/editar")
	public String editar(ModelMap modelo, @ModelAttribute Comentario comentario) {
		try {
			modelo.put("comentario", comentario);
			comentarioService.editarComentario(comentario.getId(), comentario);
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-comentario";
	}
	
	@GetMapping("/eliminar-comentario/{id}")
	public String eliminarComentario(ModelMap modelo, @RequestParam("ïd") String id) {
		try {
			modelo.put("comentario", comentarioService.getById(id));
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-comentario";
	}
	
	@PostMapping("/eliminar")
	public String eliminar(ModelMap modelo, @ModelAttribute Comentario comentario) {
		try {
			modelo.put("comentario", comentario);
			comentarioService.eliminarComentario(comentario.getId());
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-comentario";
	}
	
	@GetMapping("/buscar-coincidencia")
	public String buscarComentarioPorTituloCoincidencia(ModelMap modelo, @RequestParam String palabra ) {
		try {
			modelo.put("comentarios", comentarioService.buscaComentarioPorTituloCoincidencia(palabra));
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-comentario";
	}
	
	@GetMapping("/buscar-usuario")
	public String buscarComentarioPorUsuario(ModelMap modelo, @ModelAttribute Usuario usuario) {
		try {
			modelo.put("comentarios", comentarioService.getByUsuario(usuario));
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-comentario";
	}
	
	
	
	
}

