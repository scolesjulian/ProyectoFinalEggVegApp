package com.vegapp.service;

//import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vegapp.entity.Foto;
import com.vegapp.entity.Tienda;
import com.vegapp.entity.Zona;
import com.vegapp.errors.ErrorService;
import com.vegapp.repository.FotoRepository;
import com.vegapp.repository.TiendaRepository;
import com.vegapp.repository.ZonaRepository;

@Service
public class TiendaService {
	
	@Autowired
	public TiendaRepository tiendaRepository;
	
	@Autowired
	public ZonaRepository zonaRepository;
	
	@Autowired
	public FotoRepository fotoRepository;

	@Autowired
	public FotoService fotoService;
	
	//public static List<Tienda> tiendas = new LinkedList<>();
	
	@Transactional
	public void generarTienda(MultipartFile archivo, String nombre, String rubro, String descripcion, String horario, String telefono, String direccion, String IdZona)throws ErrorService{
		
		validarTienda(nombre, rubro, descripcion, horario, telefono, direccion);
		
		Tienda tienda = new Tienda();
		
		tienda.setNombre(nombre);
		tienda.setRubro(rubro);
		tienda.setDescripcion(descripcion);
		tienda.setHorario(horario);
		tienda.setTelefono(telefono);
		tienda.setDireccion(direccion);

		Zona zona = zonaRepository.findById(IdZona).get();
		tienda.setZona(zona);
		
		Foto foto = fotoService.guardarFoto(archivo);
		tienda.setFoto(foto);
		
		tiendaRepository.save(tienda);
		//tiendas.add(tienda);
	}
	
	@Transactional
	public void editarTienda(MultipartFile archivo,String id, String nombre, String rubro, String descripcion, String horario, String telefono, String direccion)throws ErrorService{
		
		validarTienda(nombre, rubro, descripcion, horario, telefono, direccion);
		
        Optional <Tienda> respuesta = tiendaRepository.findById(id);
        
        if(respuesta.isPresent()) {
        	
        	Tienda tienda = respuesta.get();
        	
    		tienda.setNombre(nombre);
    		tienda.setRubro(rubro);
    		tienda.setDescripcion(descripcion);
    		tienda.setHorario(horario);
    		tienda.setTelefono(telefono);
    		tienda.setDireccion(direccion);
    		
			Foto foto = fotoService.guardarFoto(archivo);
			tienda.setFoto(foto);
    		
			/*Zona zona = zonaRepository.findById(IdZona).get();
			tienda.setZona(zona);*/
            
    		tiendaRepository.save(tienda);
            
        }else {        	
        	throw new ErrorService("No se ha encontrado la tienda solicitada");
        }  
	}
	
    @Transactional
    public void eliminarTienda(String id)throws ErrorService{    	
    	
    	Optional <Tienda> respuesta = tiendaRepository.findById(id);
        
        if(respuesta.isPresent()) {
        	
        	Tienda tienda = respuesta.get();
        	            
        	tiendaRepository.delete(tienda);
            
        }else {        	
        	throw new ErrorService("No se ha encontrado la tienda solicitada");
        } 
    }
    
    /*public List<Tienda> returnListaTiendas() {
    	return tiendas;
    }*/
    
	
    public void validarTienda(String nombre, String rubro, String descripcion, String horario, String telefono, String direccion) throws ErrorService {
		
    	if(nombre == null) {
    		throw new ErrorService("El nombre no puede ser vacio");
    	}
    	
    	if(rubro == null) {
    		throw new ErrorService("El rubro no puede ser vacio");
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
