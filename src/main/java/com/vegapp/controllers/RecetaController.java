 package com.vegapp.controllers;

import java.util.List;
import java.util.Optional;

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

import com.vegapp.entity.Receta;
import com.vegapp.entity.Zona;
import com.vegapp.repository.RecetaRepository;
import com.vegapp.errors.ErrorService;
import com.vegapp.service.RecetaService;

@Controller
@RequestMapping("/receta")
public class RecetaController {
		
		@Autowired
		private RecetaService recetaService;
		private RecetaRepository recetaRepository;
		
		
		@GetMapping("")
		public String verRecetas() {
			return "recetas.html";
		}
		
		@GetMapping("/crear")
		public String crearReceta(Model model, @RequestParam(required = false) String id) {
			if (id != null) {
				Optional<Receta> optional = recetaService.findById(id);
				if (optional.isPresent()) {
					model.addAttribute("receta", optional.get());
				} else {
					return "recetas.html";   
				}
			} else {
				model.addAttribute("receta", new Receta());
			}
			return "formularioreceta.html";
		}
		////// edicion /////
		@GetMapping("/recetasEdicion")
		public String recetaEdicion() {	
			
			return "recetasEdicion.html";
		}
		

		@GetMapping("/edicion/{id}")
		public String edicion(Model modelo,@PathVariable("id") String id) {		
			return "recetasEdicion.html";
		}
		
		@PostMapping("/recetaEditar")
		public String recetaEdicion(ModelMap modelo, MultipartFile archivo,@RequestParam String id, @RequestParam String nombre, @RequestParam String descripcion){		
			List<Receta> recetas= recetaRepository.findAll();
			modelo.put("recetas", recetas);
			try {
				recetaService.editarReceta(archivo,id,nombre,descripcion);
				/*aca decia ErrorService*/
			} catch (Error e) {	
				modelo.put("error", e.getMessage());
				modelo.put("id", id);
				modelo.put("nombre", nombre);
				
				modelo.put("descripcion", descripcion);
				

				return "recetasEdicion.html";
			}
			
			modelo.put("titulo", "Edición de receta exitosa");
			modelo.put("descripcion", "");
			return "exito.html";
		}
		
		//// guardar /////
		@PostMapping("/save")
		public String saveReceta(@ModelAttribute Receta receta) {
			try {
				recetaService.saveReceta(receta);
			} catch (com.vegapp.errors.ErrorService e) {
				
				e.printStackTrace();
			}
			return "recetas.html";
		}
		
		///// eliminar /////
		
		@GetMapping("/recetaEliminacion")
		public String recetaEliminacion() {
			return "recetaEliminacion.html";
		}
		
		@GetMapping("/eliminar/{id}")
		public String eliminarReceta(ModelMap modelo,@PathVariable("id") String id) {
			try {
				recetaService.eliminarReceta(id);
			}catch(Exception e){
				modelo.put("error",e.getMessage());
			}
			return "redirect:/receta/recetasMostrar";
		}
		
		@PostMapping("/recetaEliminar")
		public String recetaEliminacion(ModelMap modelo, @RequestParam String id) {
			
			recetaService.eliminarReceta(id);
			modelo.put("titulo", "Eliminacion receta");
			modelo.put("descripcion", "Eliminacion exitosa");
			return "exito.html";
		}
			
		/*//// //// //// //// //// //// //// ////		 ELIMINAR TIENDA 		//// //// //// //// //// //// //// ////
	
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
	*/
		/*
		@GetMapping("/delete")
		public String eliminarReceta(@ModelAttribute Receta receta) {
			recetaService.eliminarReceta(receta);
			return "redirect:/receta/delete/success";
		}
		*/
		@GetMapping ("/recetas")
		public String recetasMostrar(ModelMap model) {
			
			List<Receta> recetas = recetaRepository.findAll();
			
			model.addAttribute("recetas",recetas);
			return "recetas.html";
		}
		
		///// buscar ////
		@GetMapping("/buscarReceta")
		public String BuscarReceta(){
		return "buscarReceta.html";
	}
		@GetMapping("/recetaMostrar")
		public String VerReceta(){
		return "recetaMostrar.html";
	}
		
		@GetMapping("/alubiasblancasconverdura")
			public String VerAlubias(){
			return "alubiasblancasconverdura.html";
		}
		@GetMapping("/ensaladadequinoa")
			public String VerEnsalada(){
			return "ensaladadequinoa.html";
		}
		@GetMapping("/falafelligero")
			public String VerFalafelligero(){
			return "falafelligero.html";
		}
		@GetMapping("/revueltoveganodetofu")
			public String VerRevueltoVeganoDeTofu(){
			return "revueltoveganodetofu.html";
		}
		
		@GetMapping("/garbanzosconespinaca")
			public String verGarbanzosconEspinaca() {
			return "garbanzosconespinaca.html";
		}
		@GetMapping("/tortilladecalabazin")
		public String verTortilladeCalabazin() {
		return "tortilladecalabazin.html";
	}
		
	}
	

