package com.vegapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vegapp.entity.Articulo;
import com.vegapp.entity.Comentario;
import com.vegapp.entity.Foto;
import com.vegapp.entity.Profesional;
import com.vegapp.entity.Voto;
import com.vegapp.errors.ErrorService;
import com.vegapp.repository.ArticuloRepository;

@Service
public class ArticuloService {

	@Autowired
	private ArticuloRepository articuloRepository;
	
	@Autowired
	private FotoService fotoService;
	
	@Transactional
	public Articulo guardarArticulo(Articulo articulo) throws ErrorService{
		try {
			//valido los tres campos necesarios, el resto pueden no estar (foto, comentario y voto)
			if(articulo.getTitulo().isEmpty() || articulo.getDescripcion().isEmpty() || articulo.getProfesional() == null) {
				throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos");
			}
			return articuloRepository.save(articulo);
		} catch (Exception e) {
			System.err.println(e);
			throw new ErrorService("No se pudo guardar el Articulo");
		}
		
	}
	
	@Transactional
	public Articulo guardarArticuloAtributos(MultipartFile archivo, String titulo, String descripcion
			//, Profesional profesional, 
			//List<Voto> votos, List<Comentario> comentarios
			) throws ErrorService {
		try {
			if (titulo.isEmpty() || descripcion.isEmpty()) {
				throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos");
			}
			Articulo articulo = new Articulo();
			articulo.setTitulo(titulo);
			articulo.setDescripcion(descripcion);
			//articulo.setProfesional(profesional);			
			//articulo.setVotos(votos);
			//articulo.setComentarios(comentarios);
			
			//utilizo el service de foto para guardarla y luego la seteo en el articulo
			Foto foto = fotoService.guardarFoto(archivo);
			articulo.setFoto(foto);
			
			return articuloRepository.save(articulo);
		} catch (Exception e) {
			System.err.println(e);
			throw new ErrorService("No se pudo guardar el Comentario");
		}
	}
	
	@Transactional
	public Articulo editarArticulo(String id, Articulo articulo) throws ErrorService {
		validarConsistenciaDeIDS(id, articulo);
		Optional<Articulo> respuesta = articuloRepository.findById(id);
		if (respuesta.isPresent()) {
			if(articulo.getTitulo().isEmpty() || articulo.getDescripcion().isEmpty() || articulo.getProfesional() == null) {
				throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos");
			}
		} else {
			throw new ErrorService("El Articulo no se encuentra en la base de datos");
		}
		try {
			return articuloRepository.save(articulo);
		} catch (Exception e) {
			System.err.println(e);
			throw new ErrorService("No se pudo guardar el Articulo nuevo");
		}
	}

	@Transactional
	public Articulo editarArticuloAtributos(MultipartFile archivo, String id, String titulo, String descripcion, Profesional profesional, 
			List<Voto> votos, List<Comentario> comentarios) throws ErrorService {
		Optional<Articulo> respuesta = articuloRepository.findById(id);
		if (respuesta.isPresent()) {
			if (titulo.isEmpty() || descripcion.isEmpty() ||  profesional == null) {
				throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos");
			}
		} else {
			throw new ErrorService("El Articulo no se encuentra en la base de datos");
		}
		try {
			Articulo articulo = new Articulo();
			articulo.setTitulo(titulo);
			articulo.setDescripcion(descripcion);
			articulo.setProfesional(profesional);			
			articulo.setVotos(votos);
			articulo.setComentarios(comentarios);
			//creo un id en nulo y chequeo si el articulo tiene una foto cargada, si es asi se lo asigno y uso el fotoService para actualizar
			String idFoto = null;
			if(articulo.getFoto().getId() != null) {
				idFoto = articulo.getFoto().getId();
			}			
			Foto foto = fotoService.actualizarFoto(idFoto, archivo);
			articulo.setFoto(foto);
			
			return articuloRepository.save(articulo);
		} catch (Exception e) {
			System.err.println(e);
			throw new ErrorService("No se pudo guardar el Articulo nuevo");
		}
	}
	
	
	@Transactional
	public Articulo eliminarArticulo(String id) throws ErrorService {
		Optional<Articulo> respuesta = articuloRepository.findById(id);
		if (respuesta.isPresent()) {
			Articulo articulo = respuesta.get();
			articuloRepository.delete(articulo);
			return articulo;
		}
		throw new ErrorService("El Articulo no se encuentra en la base de datos");
	}

	@Transactional
	public Articulo agregarComentario(String id, Comentario comentario) throws ErrorService{
		Optional<Articulo> respuesta = articuloRepository.findById(id);
		if (respuesta.isPresent()) {
			if (comentario == null || comentario.getComentario().isEmpty() || comentario.getTitulo().isEmpty()) {
				throw new ErrorService("El Comentario tiene algunos campos nulos o vacios");
			}
		} else {
			throw new ErrorService("El Articulo no se encuentra en la base de datos");
		}
		try {
			Articulo articulo = respuesta.get();
			articulo.getComentarios().add(comentario);
			return articuloRepository.save(articulo);
		} catch (Exception e) {
			System.err.println(e);
			throw new ErrorService("No se pudo guardar el Comentario nuevo");
		}
	}
	
	@Transactional
	public Articulo agregarVoto(String id, Voto voto) throws ErrorService{
		Optional<Articulo> respuesta = articuloRepository.findById(id);
		if (respuesta.isPresent()) {
			//faltan validaciones para cuando este la entidad voto lista
			if (voto == null ) {
				throw new ErrorService("El Voto tiene algunos campos nulos o vacios");
			}
		} else {
			throw new ErrorService("El Articulo no se encuentra en la base de datos");
		}
		try {
			Articulo articulo = respuesta.get();
			articulo.getVotos().add(voto);
			return articuloRepository.save(articulo);
		} catch (Exception e) {
			System.err.println(e);
			throw new ErrorService("No se pudo guardar el Articulo nuevo");
		}
	}
	
	@Transactional
	public List<Articulo> getAll(){
		return articuloRepository.findAll();
	}
	
	@Transactional
	public Articulo getById(String id) throws ErrorService {
		Optional<Articulo> respuesta = articuloRepository.findById(id);
		if (respuesta.isPresent()) {
			return respuesta.get();
		}
		throw new ErrorService("No se encontro el Articulo con ede ID");
	}
	
	
	public List<Articulo> buscarArticuloPorTituloCoincidencia(String palabra) {
		return articuloRepository.findByTituloContainingIgnoreCase(palabra);
	}
	
	public List<Articulo> getByProfesional(Profesional profesional){
		return articuloRepository.findByProfesional(profesional);
	}
	
	
	private void validarConsistenciaDeIDS(String id, Articulo articulo) throws ErrorService {
		if (articulo.getId() == null || !articulo.getId().equals(id)) {
			throw new ErrorService("No coincide id del body con id del path, o es nulo");
		}
	}

}
