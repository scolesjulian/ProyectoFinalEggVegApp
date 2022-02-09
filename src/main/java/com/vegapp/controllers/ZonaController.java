package com.vegapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vegapp.clients.dtos.GeografiaDTO;
import com.vegapp.entity.Zona;
import com.vegapp.service.ZonaService;

@Controller
@RequestMapping("/zona")
public class ZonaController {

	@Autowired
	private ZonaService service;

	@GetMapping("/guardar-zona")
	public String guardarZonaProv(Model modelo) {
		try {
			modelo.addAttribute("provincias", this.service.getProvicias());
			modelo.addAttribute("zona", new Zona());
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		}
		return "zona.html";
	}

	@PostMapping("/guardar")
	public String guardarZona(ModelMap modelo, @ModelAttribute Zona zona) {
		try {
			modelo.put("zona", zona);
			this.service.guardarZona(zona);
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
		}
		return "zona";
	}

	@GetMapping("/editar/{id}")
	public String editarZonaProv(@PathVariable("id") String id, Model modelo) {
		try {
			Zona zonaAEditar = this.service.getById(id);
			modelo.addAttribute("zona", zonaAEditar);
			modelo.addAttribute("provinciasList", this.service.getProvicias());
			modelo.addAttribute("departamentosList", this.service.getDepartamentosByProv(zonaAEditar.getProvincia()));
			modelo.addAttribute("ciudadesList", this.service.getCiudadesByDeptos(zonaAEditar.getDepartamento()));
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		}
		return "zonaEditar";
	}

	@PostMapping("/editar")
	public String editarZona(@ModelAttribute Zona zona, Model modelo) {
		try {
			this.service.editarZona(zona.getId(), zona);
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		}
		return "zonaEditar";
	}

	@GetMapping("/eliminarZona/{id}")
	public String eliminarZona(Model modelo, @PathVariable("id") String id) {
		try {
			modelo.addAttribute("zona", service.getById(id));
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		}
		return "eliminarZona";
	}

	@PostMapping("/eliminar")
	public String eiliminarZona(@ModelAttribute Zona zona, Model modelo) {
		try {
			modelo.addAttribute("zona", zona);
			System.out.println("zona " + zona.getId());
			this.service.eliminarZona(zona.getId());
		} catch (Exception e) {
			modelo.addAttribute("error", e.getMessage());
		}
		return "listaZona";
	}

	@GetMapping("/lista")
	public String listaZonas(ModelMap model) {
		model.put("zonas", service.getAll());
		return "listaZona";
	}

	@GetMapping("/provincias")
	public List<GeografiaDTO> getProvincias() {
		return this.service.getProvicias();
	}

	@GetMapping("/departamentos/{nombre}")
	public String getDepartamentos(@PathVariable("nombre") String nombre, Model model) {
		List<GeografiaDTO> departamentosList = this.service.getDepartamentosByProv(nombre);
		model.addAttribute("departamentosList", departamentosList);
		return "zona :: departamentos";
	}

	@GetMapping("/ciudades/{nombre}")
	public String getCiudades(@PathVariable("nombre") String nombre, Model model) {
		List<GeografiaDTO> ciudadesList = this.service.getCiudadesByDeptos(nombre);
		model.addAttribute("ciudadesList", ciudadesList);
		return "zona :: ciudades";
	}
}
