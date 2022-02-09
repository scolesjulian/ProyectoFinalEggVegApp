package com.vegapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegapp.entity.Articulo;
import com.vegapp.entity.Profesional;
import com.vegapp.service.ArticuloService;

@Controller
@RequestMapping("/articulo")
public class ArticuloController {

	private ArticuloService articuloServie;
	
	@GetMapping("/")
	public String articulos() {
		return "form-articulos.html";
	}
	
	@GetMapping("/guardar-articulo")
	public String cargarArticulo(ModelMap modelo) {
		try {
			modelo.put("articulo", new Articulo());
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-articulo";
	}
	
	@PostMapping("/guardar")
	public String guardar(ModelMap modelo, @ModelAttribute Articulo articulo ) {
		try {
			modelo.put("articulo", articulo);
			articuloServie.guardarArticulo(articulo);
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-articulo";
	}
	
	@GetMapping("/editar-articulo/{id}")
	public String editarArticulo(ModelMap modelo,  @ModelAttribute Articulo articulo,  @RequestParam("id") String id ) {
		try {
			modelo.put("articulo", articulo);			
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-articulo";
	}
	
	@PostMapping("/editar")
	public String editar(ModelMap modelo, @ModelAttribute Articulo articulo) {
		try {
			modelo.put("articulo", articulo);
			articuloServie.editarArticulo(articulo.getId(), articulo);
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-articulo";
	}
	
	@GetMapping("/eliminar-articulo/{id}")
	public String eliminarArticulo(ModelMap modelo, @RequestParam("ïd") String id) {
		try {
			modelo.put("articulo", articuloServie.getById(id));
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-articulo";
	}
	
	@PostMapping("/eliminar")
	public String eliminar(ModelMap modelo, @ModelAttribute Articulo articulo) {
		try {
			modelo.put("articulo", articulo);
			articuloServie.eliminarArticulo(articulo.getId());
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-articulo";
	}
	
	@GetMapping("/buscar-coincidencia")
	public String buscarArticuloPorTituloCoincidencia(ModelMap modelo, @RequestParam String palabra ) {
		try {
			modelo.put("articulos", articuloServie.buscarArticuloPorTituloCoincidencia(palabra));
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-articulo";
	}
	
	@GetMapping("/buscar-profesional")
	public String buscarArticulosPorProfesional(ModelMap modelo, @ModelAttribute Profesional profesional) {
		try {
			modelo.put("articulos", articuloServie.getByProfesional(profesional));
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "form-articulo";
	}
	
	
	
	
}
