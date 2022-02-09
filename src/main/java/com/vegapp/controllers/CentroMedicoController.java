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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vegapp.entity.CentroMedico;
import com.vegapp.entity.Tienda;
import com.vegapp.entity.Zona;
import com.vegapp.errors.ErrorService;
import com.vegapp.repository.CentroMedicoRepository;
import com.vegapp.repository.ZonaRepository;
import com.vegapp.service.CentroMedicoService;
import com.vegapp.service.ZonaService;

@Controller
@RequestMapping("/centroMedico")
public class CentroMedicoController {
	
	@Autowired
	public CentroMedicoService centroMedicoService;
	
	@Autowired
	public CentroMedicoRepository centroMedicoRepository;
	
	@Autowired
	public ZonaRepository zonaRepository;
	
	@Autowired
	public ZonaService zonaService;


	@GetMapping("/centroMedicoRegistro")  
	public String centroMedicoRegistro(Model modelo) {	
		modelo.addAttribute("zona", new Zona());
		modelo.addAttribute("provincias", this.zonaService.getProvicias());
		return "centroMedicoRegistro.html";
	}
	
	
								//// //// //// //// //// //// //// ////		 REGISTRAR centro Medico 		//// //// //// //// //// //// //// ////
	
	@PostMapping("/centroMedicoRegistrar")
	public String centroMedicoRegistroPrueba(Model modelo, MultipartFile archivo, @RequestParam String nombre, @RequestParam String especialidad, @RequestParam String 
			descripcion, @RequestParam String horario, @RequestParam String telefono, String direccion, @ModelAttribute Zona zona){		
		
		try {
			zonaService.guardarZona(zona);
			centroMedicoService.generarCentroMedico(archivo, nombre, especialidad, descripcion, horario, telefono, direccion, zona.getId());
			
		} catch (ErrorService e) {	
			modelo.addAttribute("error", e.getMessage());
			modelo.addAttribute("nombre", nombre);
			modelo.addAttribute("especialidad", especialidad);
			modelo.addAttribute("descripcion", descripcion);
			modelo.addAttribute("horario", horario);
			modelo.addAttribute("telefono", telefono);
			modelo.addAttribute("direccion", direccion);
			modelo.addAttribute("zona",zona);

			return "centroMedicoRegistro.html";
		}
		
		modelo.addAttribute("titulo", "Registro de centro médico exitoso");
		modelo.addAttribute("descripcion", "");
		return "exito.html";
	}
	
	
							//// //// //// //// //// //// //// ////		 ELIMINAR centro Medico 		//// //// //// //// //// //// //// ////
	
	@GetMapping("/centroMedicoEliminacion")
	public String centroMedicoEliminacion() {
		return "centroMedicoEliminacion.html";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarcentroMedico(ModelMap modelo,@PathVariable("id") String id) {
		try {
			centroMedicoService.eliminarCentroMedico(id);
		}catch(Exception e){
			modelo.put("error",e.getMessage());
		}
		return "redirect:/centroMedico/centrosMedicosMostrar";
	}
	
	@PostMapping("/centroMedicoEliminar")
	public String centroMedicoEliminacion(ModelMap modelo, @RequestParam String id) {
		
		try {
			
			centroMedicoService.eliminarCentroMedico(id);

		}catch (ErrorService e) {
			modelo.put("error", e.getMessage());
			
			return "centroMedicoEliminacion.html";
		}
		modelo.put("titulo", "Eliminacion de centro médico exitosa");
		modelo.put("descripcion", "Eliminacion exitosa");
		return "exito.html";
	}
	
							//// //// //// //// //// //// //// ////		 EDITAR centro Medico 		//// //// //// //// //// //// //// ////
	
		@GetMapping("/centroMedicoEdicion")
		public String centroMedicoEdicion() {		
			return "centroMedicoEdicion.html";
		}
		
		@GetMapping("/edicion/{id}")
		public String edicion(Model modelo,@PathVariable("id") String id) {		
			return "centroMedicoEdicion.html";
		}
		
		
		
		@PostMapping("/centroMedicoEditar")
		public String centroMedicoEdicion(Model modelo, MultipartFile archivo, @RequestParam String id, @RequestParam String nombre, @RequestParam String especialidad, @RequestParam String descripcion, @RequestParam String horario, @RequestParam String telefono,@RequestParam String direccion){		
			//List<Zona> zonas= zonaRepository.findAll();
			//modelo.addAttribute("zonas", zonas);
			try {
				//zonaService.guardarZona(zona);
				centroMedicoService.editarCentroMedico(archivo, id, nombre, especialidad, descripcion, horario, telefono, direccion);
				
			} catch (ErrorService e) {	
				modelo.addAttribute("error", e.getMessage());
				modelo.addAttribute("nombre", nombre);
				modelo.addAttribute("especialidad", especialidad);
				modelo.addAttribute("descripcion", descripcion);
				modelo.addAttribute("horario", horario);
				modelo.addAttribute("telefono", telefono);
				modelo.addAttribute("direccion", direccion);
				//modelo.addAttribute("zona",zona);

				return "centroMedicoEdicion.html";
			}
			
			modelo.addAttribute("titulo", "Edición de centro médico exitosa");
			modelo.addAttribute("descripcion", "");
			return "exito.html";
		}
	
							//// //// //// //// //// //// //// ////		 BUSCAR y MOSTRAR centro Medico 		//// //// //// //// //// //// //// ////
	
	@GetMapping("/centroMedicoBusqueda")
	public String centroMedicoBusqueda() {
		return "centroMedicoBusqueda.html";
	}
	
	
	//Mostrar 1 centro Medico
	@PostMapping("/centroMedicoMostrar")
	public String centroMedicoMostrar(Model modelo,@RequestParam(required=false) String error, @RequestParam String nombre) {
		if(error!=null) {
			modelo.addAttribute("error", "No se encuentra un centro médico bajo ese nombre.");
		}
		List<CentroMedico> centrosMedicos = centroMedicoRepository.findByNombre(nombre);
		CentroMedico centroMedico=null;
		if(centrosMedicos.size()==0) {
			modelo.addAttribute("error", "No se encuentra un centro médico bajo ese nombre.");
			return "redirect:/centroMedico/centrosMedicosMostrar";
		}else {
			centroMedico = centrosMedicos.get(0);
			
		}
		
		modelo.addAttribute("centroMedico", centroMedico);
		return "centroMedicoMostrar.html";
	}
	
	//Mostrar todos los centros Medicos
	@GetMapping("/centrosMedicosMostrar")
	public String centroMedicoMostrar(ModelMap modelo) {
		
		List<CentroMedico> centrosMedicos = centroMedicoRepository.findAll();
		
		modelo.addAttribute("centrosMedicos",centrosMedicos);
		return "centrosMedicosMostrar.html";
	}
	
	//Mostrar todos los centros Medicos
	@PostMapping("/centroMedicoMostrarPorId")
	public String centroMedicoMostrarPorId(ModelMap modelo,@RequestParam String id) {
		
		CentroMedico centroMedico = centroMedicoRepository.findById(id).get();
		
		modelo.addAttribute("centroMedico",centroMedico);
		return "centroMedicoMostrar.html";
	}
	//Mostrar Centro por id
		@GetMapping("/mostrar/{id}")
		public String mostraCentro(Model modelo,@PathVariable("id") String id) {
			try {
				modelo.addAttribute("centroMedico", centroMedicoRepository.findById(id).get());
			}catch(Exception e){
				modelo.addAttribute("error",e.getMessage());
			}
			
			return "centroMedicoMostrar.html";
		}
}