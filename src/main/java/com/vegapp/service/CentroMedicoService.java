package com.vegapp.service;

//import java.util.LinkedList;
//import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vegapp.entity.CentroMedico;
import com.vegapp.entity.Foto;
import com.vegapp.entity.Zona;
import com.vegapp.errors.ErrorService;
import com.vegapp.repository.CentroMedicoRepository;
import com.vegapp.repository.FotoRepository;
import com.vegapp.repository.ZonaRepository;

@Service
public class CentroMedicoService {
	
	@Autowired
	public CentroMedicoRepository centroMedicoRepository;
	
	@Autowired
	public ZonaRepository zonaRepository;
	
	@Autowired
	public FotoRepository fotoRepository;

	@Autowired
	public FotoService fotoService;
	
	//public static List<CentroMedico> centrosMedicos = new LinkedList<>();
	
	@Transactional
	public void generarCentroMedico(MultipartFile archivo, String nombre, String especialidad, String descripcion, String horario, String telefono, String direccion, String IdZona)throws ErrorService{
		
		validarCentroMedico(nombre, especialidad, descripcion, horario, telefono, direccion);
		
		CentroMedico centroMedico = new CentroMedico();
		
		centroMedico.setNombre(nombre);
		centroMedico.setEspecialidad(especialidad);
		centroMedico.setDescripcion(descripcion);
		centroMedico.setHorario(horario);
		centroMedico.setTelefono(telefono);
		centroMedico.setDireccion(direccion);

		Zona zona = zonaRepository.findById(IdZona).get();
		centroMedico.setZona(zona);
		
		Foto foto = fotoService.guardarFoto(archivo);
		centroMedico.setFoto(foto);
		
		centroMedicoRepository.save(centroMedico);
		//centrosMedicos.add(centroMedico);
	}
	
	@Transactional
	public void editarCentroMedico(MultipartFile archivo,String id, String nombre, String especialidad, String descripcion, String horario, String telefono, String direccion)throws ErrorService{
		
		validarCentroMedico(nombre, especialidad, descripcion, horario, telefono, direccion);
		
        Optional <CentroMedico> respuesta = centroMedicoRepository.findById(id);
        
        if(respuesta.isPresent()) {
        	
        	CentroMedico centroMedico = respuesta.get();
        	
        	centroMedico.setNombre(nombre);
        	centroMedico.setEspecialidad(especialidad);
        	centroMedico.setDescripcion(descripcion);
        	centroMedico.setHorario(horario);
        	centroMedico.setTelefono(telefono);
        	centroMedico.setDireccion(direccion);
    		
			Foto foto = fotoService.guardarFoto(archivo);
			centroMedico.setFoto(foto);
    		
			//Zona zona = zonaRepository.findById(IdZona).get();
			//centroMedico.setZona(zona);
            
        	centroMedicoRepository.save(centroMedico);
            
        }else {        	
        	throw new ErrorService("No se ha encontrado el centroMedico solicitada");
        }  
	}
	
    @Transactional
    public void eliminarCentroMedico(String id)throws ErrorService{    	
    	
        Optional<CentroMedico> respuesta=centroMedicoRepository.findById(id);
        
        if(respuesta.isPresent()) {
        	
        	CentroMedico centroMedico = respuesta.get();
        	            
        	centroMedicoRepository.delete(centroMedico);
            
        }else {        	
        	throw new ErrorService("No se ha encontrado el centroMedico solicitada");
        }  
    }
    
    
    /*public List<CentroMedico> mostrarCentroMedico(String nombre){
    	
    	List<CentroMedico> centroMedico = centroMedicoRepository.findByNombre(nombre);
    	
		return centroMedico;
    }
    
    public List<CentroMedico> returnListaCentrosMedicos() {
    	return centrosMedicos;
    }*/
    
	
    public void validarCentroMedico(String nombre, String especialidad, String descripcion, String horario, String telefono, String direccion) throws ErrorService {
		
    	if(nombre == null) {
    		throw new ErrorService("El nombre no puede ser vacio");
    	}
    	
    	if(especialidad == null) {
    		throw new ErrorService("La especialidad no puede ser vacio");
    	}
    	
    	if(descripcion == null) {
    		throw new ErrorService("La descripcion no puede ser vacio");
    	}
		
    	if(horario == null) {
    		throw new ErrorService("El horario no puede ser vacio");
    	}
    	
    	if(telefono == null) {
    		throw new ErrorService("El telefono no puede ser vacio");
    	}
    	
    	if(direccion == null) {
    		throw new ErrorService("La direccion no puede ser vacia");
    	}
    
	}
}