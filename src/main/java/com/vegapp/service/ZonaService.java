package com.vegapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegapp.clients.dtos.GeografiaDTO;
import com.vegapp.entity.Zona;
import com.vegapp.errors.ErrorService;
import com.vegapp.repository.ZonaRepository;

@Service
public class ZonaService {

	private ZonaRepository zonaRepository;
	private GeoRefService geoRefService;

	@Autowired
	public ZonaService(ZonaRepository zonaRepository, GeoRefService geoRefService) {
		this.zonaRepository = zonaRepository;
		this.geoRefService = geoRefService;
	}

	public List<GeografiaDTO> getProvicias() {
		return this.geoRefService.getProvincias();
	}

	public List<GeografiaDTO> getDepartamentosByProv(String id) {
		return this.geoRefService.getDepartamentosByProv(id);
	}

	public List<GeografiaDTO> getCiudadesByDeptos(String id) {
		return this.geoRefService.getCiudadesByDeptos(id);
	}

	@Transactional
	public Zona guardarZona(Zona zona) throws ErrorService {
		try {
			if (zona.getCiudad().isEmpty() || zona.getDepartamento().isEmpty()
					 || zona.getProvincia().isEmpty()) {
				throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos");

			}
			return zonaRepository.save(zona);
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			throw new ErrorService("No se pudo guardar la Zona");
			
		}
	}

	@Transactional
	public Zona guardarZonaAtributos(String provincia, String ciudad, String departamento, String barrio,
			String direccion) throws ErrorService {
		try {
			if (provincia.isEmpty() || ciudad.isEmpty() || departamento.isEmpty() || barrio.isEmpty()
					|| direccion.isEmpty()) {
				throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos");
			}
			Zona zona = new Zona();
			zona.setBarrio(barrio);
			zona.setCiudad(ciudad);
			zona.setDepartamento(departamento);
			zona.setDireccion(direccion);
			zona.setProvincia(provincia);
			return zonaRepository.save(zona);
		} catch (Exception e) {
			System.err.println(e);
			throw new ErrorService("No se pudo guardar la Zona");
		}
	}

	@Transactional
	public Zona editarZona(String id, Zona zona) throws ErrorService {
		validarConsistenciaDeIDS(id, zona);
		Optional<Zona> respuesta = zonaRepository.findById(id);
		if (respuesta.isPresent()) {
			if (zona.getBarrio().isEmpty() || zona.getCiudad().isEmpty() || zona.getDepartamento().isEmpty()
					|| zona.getDireccion().isEmpty() || zona.getProvincia().isEmpty()) {
				throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos");
			}
		} else {
			throw new ErrorService("La Zona no se encuentra en la base de datos");
		}
		try {
			return zonaRepository.save(zona);
		} catch (Exception e) {
			System.err.println(e);
			throw new ErrorService("No se pudo guardar la Zona nueva");
		}
	}

	@Transactional
	public Zona editarZonaAtributos(String id, String provincia, String ciudad, String departamento, String barrio,
			String direccion) throws ErrorService {
		Optional<Zona> respuesta = zonaRepository.findById(id);
		if (respuesta.isPresent()) {
			if (provincia.isEmpty() || ciudad.isEmpty() || departamento.isEmpty() || barrio.isEmpty()
					|| direccion.isEmpty()) {
				throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos");
			}
		} else {
			throw new ErrorService("La Zona no se encuentra en la base de datos");
		}
		try {
			Zona zona = new Zona();
			zona.setBarrio(barrio);
			zona.setCiudad(ciudad);
			zona.setDepartamento(departamento);
			zona.setDireccion(direccion);
			zona.setProvincia(provincia);
			return zonaRepository.save(zona);
		} catch (Exception e) {
			System.err.println(e);
			throw new ErrorService("No se pudo guardar la Zona nueva");
		}
	}

	@Transactional
	public Zona eliminarZona(String id) throws ErrorService {
		Optional<Zona> respuesta = zonaRepository.findById(id);
		if (respuesta.isPresent()) {
			Zona zona = respuesta.get();
			zonaRepository.delete(zona);
			return zona;
		}
		throw new ErrorService("La Zona no se encuentra en la base de datos");
	}

	@Transactional
	public Zona getById(String id) throws ErrorService {
		Optional<Zona> respuesta = zonaRepository.findById(id);
		if (respuesta.isPresent()) {
			return respuesta.get();
		}
		throw new ErrorService("No se encontro la Zona con ede ID");
	}

	@Transactional
	public List<Zona> getAll() {
		return zonaRepository.findAll();
	}

	private void validarConsistenciaDeIDS(String id, Zona zona) throws ErrorService {
		if (zona.getId() == null || !zona.getId().equals(id)) {
			throw new ErrorService("No coincide id del body con id del path, o es nulo");
		}
	}
}
