package com.vegapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegapp.entity.Comentario;
import com.vegapp.entity.Usuario;
import com.vegapp.entity.Voto;
import com.vegapp.errors.ErrorService;
import com.vegapp.repository.ComentarioRepository;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository comentarioRepository;

	@Transactional
	public Comentario guardarComentario(Comentario comentario) throws ErrorService {
		try {
			if (comentario.getComentario().isEmpty() || comentario.getComentario() == null || comentario.getUsuario() == null
					|| comentario.getVoto() == null) {
				throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos");
			}
			return comentarioRepository.save(comentario);
		} catch (Exception e) {
			System.err.println(e);
			throw new ErrorService("No se pudo guardar el Comentario");
		}
	}
	
	
	
	@Transactional
	public Comentario guardarComentarioAtributos(List<Voto> voto, String comentario, String titulo, Usuario usuario) throws ErrorService {
		try {
			if (comentario.isEmpty() || comentario == null || usuario == null || voto == null || voto == null) {
				throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos");
			}
			Comentario coment = new Comentario();
			coment.setComentario(comentario);
			coment.setTitulo(titulo);
			coment.setUsuario(usuario);
			coment.setVoto(voto);
			return comentarioRepository.save(coment);
		} catch (Exception e) {
			System.err.println(e);
			throw new ErrorService("No se pudo guardar el Comentario");
		}
	}
	
	
	

	@Transactional
	public Comentario editarComentario(String id, Comentario comentario) throws ErrorService {
		validarConsistenciaDeIDS(id, comentario);
		Optional<Comentario> respuesta = comentarioRepository.findById(id);
		if (respuesta.isPresent()) {
			if (comentario.getComentario().isEmpty() || comentario.getComentario() == null || comentario.getUsuario() == null 
					|| comentario.getVoto() == null ) {
				throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos");
			}
		} else {
			throw new ErrorService("El comentario no se encuentra en la base de datos");
		}
		try {
			return comentarioRepository.save(comentario);
		} catch (Exception e) {
			System.err.println(e);
			throw new ErrorService("No se pudo guardar el Comentario nuevo");
		}
	}
	
	@Transactional
	public Comentario editarComentarioAtributos(String id, List<Voto> voto, String comentario, String titulo, Usuario usuario) throws ErrorService{
		Optional<Comentario> respuesta = comentarioRepository.findById(id);
		if (respuesta.isPresent()) {
			if (comentario.isEmpty() || comentario == null	|| usuario == null || voto == null) {
				throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos");
			}
		} else {
			throw new ErrorService("El comentario no se encuentra en la base de datos");
		}
		try {
			Comentario coment = respuesta.get();
			coment.setComentario(comentario);
			coment.setTitulo(titulo);
			coment.setUsuario(usuario);
			coment.setVoto(voto);
			return comentarioRepository.save(coment);
		} catch (Exception e) {
			System.err.println(e);
			throw new ErrorService("No se pudo guardar el Comentario nuevo");
		}
	}
	
	
	

	@Transactional
	public Comentario eliminarComentario(String id) throws ErrorService {
		Optional<Comentario> respuesta = comentarioRepository.findById(id);
		if (respuesta.isPresent()) {
			Comentario comentario = respuesta.get();
			comentarioRepository.delete(comentario);
			return comentario;
		}
		throw new ErrorService("El comentario no se encuentra en la base de datos");
	}
	
	
	@Transactional
	public Comentario agregarVoto(String id, Voto voto) throws ErrorService{
		Optional<Comentario> respuesta = comentarioRepository.findById(id);
		if (respuesta.isPresent()) {
			//faltan validaciones para cuando este la entidad voto lista
			if (voto == null ) {
				throw new ErrorService("El Voto tiene algunos campos nulos o vacios");
			}
		} else {
			throw new ErrorService("El Comentario no se encuentra en la base de datos");
		}
		try {
			Comentario comentario = respuesta.get();
			comentario.getVoto().add(voto);
			return comentarioRepository.save(comentario);
		} catch (Exception e) {
			System.err.println(e);
			throw new ErrorService("No se pudo guardar el Articulo nuevo");
		}
	}

	@Transactional
	public Comentario getById(String id) throws ErrorService {
		Optional<Comentario> respuesta = comentarioRepository.findById(id);
		if(respuesta.isPresent()) {
			return respuesta.get();
		}
		throw new ErrorService("No se encontro el Comentario con ede ID");
	}
	
	
	@Transactional
	public List<Comentario> getByUsuario(Usuario usuario) throws ErrorService{
		return comentarioRepository.findByUsuario(usuario);
	}
	
	
	
	@Transactional
	public List<Comentario> buscaComentarioPorTituloCoincidencia(String palabra) throws ErrorService {
		return comentarioRepository.findByTituloContainingIgnoreCase(palabra);
	}
	
	
	private void validarConsistenciaDeIDS(String id, Comentario comentario) throws ErrorService {
		if (comentario.getId() == null || !comentario.getId().equals(id)) {
			throw new ErrorService("No coincide id del body con id del path, o es nulo");
		}
	}
}
