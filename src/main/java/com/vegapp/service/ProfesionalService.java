package com.vegapp.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegapp.entity.Profesional;
import com.vegapp.errors.ErrorService;
import com.vegapp.repository.ProfesionalRepository;
import com.veggap.enums.Rol;

@Service
public class ProfesionalService {
	
	@Autowired
	private ProfesionalRepository profesionalRepository;
	
	@Transactional
	public void crear(String profesion,String especialidad,String matricula,String telefono) throws ErrorService {
		try {
			validar(profesion,especialidad,matricula,telefono);
			Profesional profesional=new Profesional();
			
			profesional.setProfesion(profesion);
			profesional.setEspecialidad(especialidad);
			profesional.setMatricula(matricula);
			profesional.setTelefono(telefono);
			
			profesional.setRol(Rol.PROFESIONAL);
			
			profesionalRepository.save(profesional);
		} catch (Exception e) {
			throw new ErrorService("Error al crear profesional");
		}
	}
	
	@Transactional
	public void editar(String id,String profesion,String especialidad,String matricula,String telefono) throws ErrorService {
		try {
			Optional<Profesional> respuesta=profesionalRepository.findById(id);
			if(respuesta.isPresent()) {
				validar(profesion,especialidad,matricula,telefono);
				Profesional profesional=new Profesional();
				
				profesional.setProfesion(profesion);
				profesional.setEspecialidad(especialidad);
				profesional.setMatricula(matricula);
				profesional.setTelefono(telefono);
				
				profesionalRepository.save(profesional);
			}else {
				throw new ErrorService("No se encuentra un profesional con ese id");
			}
		} catch (Exception e) {
			throw new ErrorService("Error al crear profesional");
		}
	}
	
	@Transactional
	public void eliminar(String id) throws ErrorService{
		try {
			Optional<Profesional> respuesta=profesionalRepository.findById(id);
			if(respuesta.isPresent()) {
				Profesional profesional=respuesta.get();
				profesionalRepository.delete(profesional);
			}else {
				throw new ErrorService("No se encuentra un profesional con ese id");
			}
		} catch (Exception e) {
			throw new ErrorService("Error al eliminar un profesional");
		}
	}
	
	@Transactional
	public void validar(String profesion,String especialidad,String matricula,String telefono) throws ErrorService {
		try {
			if(profesion==null||profesion.isEmpty()) {
				throw new ErrorService("La profesion no puede ser nula");
			}
			if(especialidad==null||especialidad.isEmpty()) {
				throw new ErrorService("La especialidad no puede ser nula");
			}
			if(matricula==null||matricula.isEmpty()) {
				throw new ErrorService("La matricula no puede ser nula");
			}
			if(telefono==null||telefono.isEmpty()) {
				throw new ErrorService("La protelefono no puede ser nulo");
			}
		} catch (Exception e) {
			throw new ErrorService("Error en la validacion del profesional");
		}
	}
}
