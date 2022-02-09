 package com.vegapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vegapp.entity.Profesional;
import com.vegapp.errors.ErrorService;
import com.vegapp.repository.ProfesionalRepository;
import com.vegapp.service.ProfesionalService;

@Controller
@RequestMapping("/profesional")
public class ProfesionalController {

		@Autowired
		public ProfesionalService profesionalService;
		
		@Autowired
		public ProfesionalRepository profesionalRepository;
		
		@GetMapping("/registro")
		public String registro() {
			return "profesional-registro.html";
		}
		
		@PostMapping("/registrar")
		public String registrar(ModelMap modelo,@RequestParam(required=false) String id,@RequestParam String profesion,@RequestParam String especialidad,@RequestParam String matricula,@RequestParam String telefono) {
			try {
				
				if(id!=null && !id.isEmpty()) {
					profesionalService.editar(id, profesion, especialidad,matricula,telefono);
				}else {
					profesionalService.crear(profesion, especialidad,matricula,telefono);
				}
			} catch (ErrorService e) {
				modelo.put("error", e.getMessage());
				modelo.put("profesion", profesion);
				modelo.put("especialidad", especialidad);
				modelo.put("matricula", matricula);
				modelo.put("telefono", telefono);
				return "profesional-registro.html";
			}
			return "profesional-registro.html";
		}
		
		@GetMapping("/listado")
		public String listado(ModelMap modelo) {
			try {
				List<Profesional> profesionales=profesionalRepository.findAll();
				modelo.put("profesionales", profesionales);
					
				return "profesional-listado.html";
			} catch (Exception e) {
				modelo.put("error", e.getMessage());
			}
			return "profesional-listado.html";
		}
		
		@GetMapping("/eliminar")
		public String eliminar(ModelMap modelo,@RequestParam(required=false) String id) {
			try {
				profesionalService.eliminar(id);
			} catch (Exception e) {
				modelo.put("error",e.getMessage());
			}
			return "redirect:/profesional/listado";
		}
		
		@GetMapping("/editar")
		public String editar(ModelMap modelo,@RequestParam(required=false) String id) {
			try {
				Profesional profesional=new Profesional();
				if(id!=null) {
					profesional = profesionalRepository.findById(id).get();
				}
				
				modelo.put("profesional", profesional);
			} catch (Exception e) {
				modelo.put("error",e.getMessage());
			}
			
			return "profesional-registro.html";
		}
}
