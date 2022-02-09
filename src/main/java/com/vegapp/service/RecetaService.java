package com.vegapp.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vegapp.entity.Foto;
import com.vegapp.entity.Receta;
import com.vegapp.entity.Usuario;
import com.vegapp.errors.ErrorService;
//import com.vegapp.entity.Voto;
import com.vegapp.repository.RecetaRepository;

@Service
public class RecetaService {
	
		
		@Autowired
		private RecetaRepository recetaRepository;
		
		@Transactional
		public void saveReceta(Receta receta) throws ErrorService { // SAVE POR OBJETO
			try {
				if (receta.getTitulo() == null || receta.getDescripcion() == null) {
					throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos.");
				}
				recetaRepository.save(receta);
			} catch (Exception e) {
				System.err.println(e);
				throw new ErrorService("No se pudo guardar la receta");
			}
		}
		
		@Transactional
		public void saveRecetaAtributos(String titulo, String descripcion, Usuario usuario, Foto foto) { //SAVE ATRIB
			try { 
				if (titulo == null || descripcion == null || usuario == null || foto == null ) {
					throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos.");
			}
				
				Receta receta = new Receta();
				receta.setTitulo(titulo);
				receta.setDescripcion(descripcion);
				receta.setUsuario(usuario);
				receta.setFoto(foto);
				recetaRepository.save(receta);
			
			} catch (Exception e) {
				System.err.println(e);
			
			}
			
		}
		
		public List<Receta> listAll(){  //CONSULTA
			return recetaRepository.findAll();
		}
		
		public Optional<Receta> findById(String id) {
			return recetaRepository.findById(id);
		}
		
		public void modificarRecetaAtributos(String id, String titulo, String descripcion, Foto foto) { // MODIF ATRIB
			Receta receta = recetaRepository.findById(id).get();
			
			try {
				if (receta.getTitulo() == null
						|| receta.getDescripcion() == null || receta.getUsuario() == null || receta.getFoto() == null) {
					throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos.");
				}
					receta.setTitulo(titulo);
					receta.setDescripcion(descripcion);
					//receta.setUsuario(usuario);
					receta.setFoto(foto);
					recetaRepository.save(receta);
				
			} catch (Exception e) {
				System.err.println(e);
				
			}	
			
		}
		
		
		
		@Transactional
		public void eliminarRecetaid(String id) throws ErrorService{ // ELIM
			Optional <Receta> optional = recetaRepository.findById(id);
			if (optional.isPresent()) {
				recetaRepository.delete(optional.get());	
			}
			throw new ErrorService("La receta no se encuentra en la base de datos.");
			
		}
		
		@Transactional
		public void eliminarReceta(String id) {
			recetaRepository.deleteById(id);
		}
		
		
		@Transactional
		private void validarReceta (String titulo, String descripcion, Usuario usuario, Foto foto) throws ErrorService{
			try {
				
				if (titulo == null || titulo.isEmpty()){
					throw new ErrorService("El titulo de la receta no puede ser nulo");
				}
				if (descripcion == null || descripcion.isEmpty()){
					throw new ErrorService("La descripcion de la receta no puede ser nulo");
				}
				if (usuario == null){
					throw new ErrorService("El nombre del usuario no puede ser nulo");
				}
				
				if (foto == null){
					throw new ErrorService("La receta debe tener una foto");
				}
				
			} catch (Exception e) {
				throw new ErrorService("Error en la validacion de usuario");
			}
		}

		//// RAARO POR ACA ME LOS PIDIO CREAR //// 
		public void editarReceta(MultipartFile archivo, String id, String nombre, String descripcion) {
			// TODO Auto-generated method stub
			
		}

		
		
		/*@Transactional
		private void votarPositivo (String id, Usuario usuario) {
			Voto v = new Voto();
			Receta receta = recetaRepository.findById(id).get();
			VotoService votoService = new VotoService(); 
			v = votoService.votoPositivo();
			receta.getListVoto().add(v);
			recetaRepository.save(receta);			
		}
		
		@Transactional
		private Integer mostrarVotoPositivo(String id) {
			Receta receta = recetaRepository.findById(id).get();
			Integer contador = 0;
			for (Voto aux : receta.getListVoto()) {
				if(aux.getPositivo()==1) {
					contador+=1;
				}
				
			}
			return contador;
		}*/
		
		
	}