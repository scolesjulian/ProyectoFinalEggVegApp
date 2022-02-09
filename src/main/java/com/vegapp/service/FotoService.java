package com.vegapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.vegapp.entity.Foto;
import com.vegapp.errors.ErrorService;
import com.vegapp.repository.FotoRepository;

@Service
public class FotoService {

	@Autowired
	private FotoRepository fotoRepository;
	
	@Transactional
	public Foto guardarFoto(MultipartFile archivo) throws ErrorService{
		Foto foto = new Foto();
		if(archivo != null) {
			try {
				
				foto.setMime(archivo.getContentType());
				foto.setNombre(archivo.getName());
				foto.setContenido(archivo.getBytes());
				fotoRepository.save(foto);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				throw new ErrorService("No se pudo guardar la Foto");
			}
		}
		return foto;
	}
	
	@Transactional
	public Foto actualizarFoto(String id, MultipartFile archivo) throws ErrorService{
		Foto foto = new Foto();
		if(archivo != null) {
			try {
				
				
				if(id != null) {
					Optional<Foto> respuesta = fotoRepository.findById(id);
					if(respuesta.isPresent()) {
						foto = respuesta.get();
					}
				}
				
				foto.setMime(archivo.getContentType());
				foto.setNombre(archivo.getName());
				foto.setContenido(archivo.getBytes());
				return fotoRepository.save(foto);
			} catch (Exception e) {
				System.err.println(e.getMessage());
				throw new ErrorService("No se pudo guardar la Foto");
			}
		}
		return foto;
	}
	
	@Transactional
	public Foto eliminarFoto(String id) throws ErrorService{
		Optional<Foto> respuesta = fotoRepository.findById(id);
		if (respuesta.isPresent()) {
			Foto foto = respuesta.get();
			fotoRepository.delete(foto);
			return foto;
		}
		throw new ErrorService("La Foto no se encuentra en la base de datos");
	}
	
}
