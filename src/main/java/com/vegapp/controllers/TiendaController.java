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
import com.vegapp.repository.TiendaRepository;
import com.vegapp.repository.ZonaRepository;
import com.vegapp.service.TiendaService;
import com.vegapp.service.ZonaService;

@Controller
@RequestMapping("/tienda")
public class TiendaController {
	
	@Autowired
	public TiendaService tiendaService;
	
	@Autowired
	public TiendaRepository tiendaRepository;
	
	@Autowired
	public ZonaRepository zonaRepository;
	
	@Autowired
	public ZonaService zonaService;
	//// //// //// //// //// //// //// ////		 TIENDA REGISTRO 		//// //// //// //// //// //// //// ////

	@GetMapping("/tiendaRegistro")  
	public String tiendaRegistro(Model modelo) {		
		modelo.addAttribute("zona", new Zona());
		modelo.addAttribute("provincias", this.zonaService.getProvicias());
		return "tiendaRegistro.html";
	}
	
	@PostMapping("/tiendaRegistrar")
	public String tiendaRegistro(ModelMap modelo,MultipartFile archivo,@RequestParam String nombre, 
			@RequestParam String rubro, @RequestParam String descripcion, @RequestParam String horario, 
			@RequestParam String telefono,@RequestParam String direccion, @ModelAttribute Zona zona ){		
		
		try {
			zonaService.guardarZona(zona);
			tiendaService.generarTienda(archivo,nombre, rubro, descripcion, horario, telefono,direccion, zona.getId());
		} catch (ErrorService e) {	
			modelo.addAttribute("error", e.getMessage());
			modelo.addAttribute("nombre", nombre);
			modelo.addAttribute("rubro", rubro);
			modelo.addAttribute("descripcion", descripcion);
			modelo.addAttribute("horario", horario);
			modelo.addAttribute("telefono", telefono);
			modelo.addAttribute("direccion", direccion);
			modelo.addAttribute("zona", zona);

			return "tiendaRegistro.html";
		}
		
		modelo.addAttribute("titulo", "Registro de tienda exitoso");
		modelo.addAttribute("descripcion", "");
		return "exito.html";
	}
	
	
				//// //// //// //// //// //// //// ////		 ELIMINAR TIENDA 		//// //// //// //// //// //// //// ////
	
	@GetMapping("/tiendaEliminacion")
	public String tiendaEliminacion() {
		return "tiendaEliminacion.html";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarTienda(ModelMap modelo,@PathVariable("id") String id) {
		try {
			tiendaService.eliminarTienda(id);
		}catch(Exception e){
			modelo.put("error",e.getMessage());
		}
		return "redirect:/tienda/tiendasMostrar";
	}
	
	@PostMapping("/tiendaEliminar")
	public String tiendaEliminacion(ModelMap modelo, @RequestParam String id) {
		
		try {
			tiendaService.eliminarTienda(id);

		}catch (ErrorService e) {
			modelo.put("error", e.getMessage());
			
			return "tiendaEliminacion.html";
		}
		modelo.put("titulo", "Eliminacion tienda");
		modelo.put("descripcion", "Eliminacion exitosa");
		return "exito.html";
	}
	
	
				//// //// //// //// //// //// //// ////		 EDITAR TIENDA 		//// //// //// //// //// //// //// ////

	@GetMapping("/tiendaEdicion")
	public String tiendaEdicion() {	
		
		return "tiendaEdicion.html";
	}
	

	@GetMapping("/edicion/{id}")
	public String edicion(Model modelo,@PathVariable("id") String id) {		
		return "tiendaEdicion.html";
	}
	
	@PostMapping("/tiendaEditar")
	public String tiendaEdicion(ModelMap modelo, MultipartFile archivo,@RequestParam String id, @RequestParam String nombre, @RequestParam String rubro, @RequestParam String descripcion, @RequestParam String horario, @RequestParam String telefono,@RequestParam String direccion, @ModelAttribute Zona zona){		
		List<Zona> zonas= zonaRepository.findAll();
		modelo.put("zonas", zonas);
		try {
			tiendaService.editarTienda(archivo,id,nombre,rubro,descripcion,horario,telefono,direccion);
		} catch (ErrorService e) {	
			modelo.put("error", e.getMessage());
			modelo.put("id", id);
			modelo.put("nombre", nombre);
			modelo.put("rubro", rubro);
			modelo.put("descripcion", descripcion);
			modelo.put("horario", horario);
			modelo.put("telefono", telefono);
			modelo.put("direccion", direccion);
			//modelo.put("zona", zona);

			return "tiendaEdicion.html";
		}
		
		modelo.put("titulo", "Edición de tienda exitosa");
		modelo.put("descripcion", "");
		return "exito.html";
	}
	
	
				//// //// //// //// //// //// //// ////		 BUSCAR TIENDA 		//// //// //// //// //// //// //// ////
	
	@GetMapping("/tiendaBusqueda")
	public String tiendaBusqueda() {
		return "tiendaBusqueda.html";
	}
	
	@PostMapping("/tiendaMostrar")
	public String tiendaMostrar(ModelMap modelo, @RequestParam String nombre) {

		List<Tienda> tiendas = tiendaRepository.findByNombre(nombre);
		Tienda tienda =null;
		if(tiendas.size()==0) {
			modelo.addAttribute("error", "No se encuentra un centro médico bajo ese nombre.");
			return "redirect:/tienda/tiendasMostrar";
		}else {
			tienda = tiendas.get(0);
			
		}
		modelo.put("tienda", tienda);
		return "tiendaMostrar.html";
		
	}
	
	// Mostrar todas las Tiendas
	@GetMapping("/tiendasMostrar")
	public String tiendasMostrar(ModelMap modelo) {
		
		List<Tienda> tiendas = tiendaRepository.findAll();
		
		modelo.addAttribute("tiendas",tiendas);
		return "tiendasMostrar.html";
	}
	@GetMapping("/mostrar/{id}")
	public String mostrarTienda(ModelMap modelo,@PathVariable("id") String id) {
		Tienda tienda=tiendaRepository.findById(id).get();
		try {
			modelo.put("tiendaMedico", tienda);
		}catch(Exception e){
			modelo.put("error",e.getMessage());
		}
		modelo.put("tienda", tienda);
		return "tiendaMostrar.html";
	}
}