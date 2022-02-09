package com.vegapp.clients;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.vegapp.clients.dtos.CiudadesDTO;
import com.vegapp.clients.dtos.DepartamentosDTO;
import com.vegapp.clients.dtos.GeografiaDTO;
import com.vegapp.clients.dtos.ProvinciasDTO;

@Component
public class GeoRefClient {

	private WebClient client;

	@Autowired
	public GeoRefClient() {
		this.client = WebClient.builder()
				.baseUrl("https://apis.datos.gob.ar/georef/api")
				.build();
	}
	
	public List<GeografiaDTO> getProvincias() {
		ProvinciasDTO p = this.client.get()
				.uri("/provincias")
				.retrieve()
				.bodyToMono(ProvinciasDTO.class).block();
		
		/////ORDENO LOS RESULTADOS POR NOMBRE CON LA IMPLEMENTACION DEL compareTo DE LA CLASE GeografiaDTO//////
		
		List<GeografiaDTO> provincias = p.getProvincias();
		Collections.sort(provincias);
		return provincias;
		
	}

	public List<GeografiaDTO> getDepartamentosByProv(String id) {
		DepartamentosDTO d = this.client.get()
				.uri("/departamentos?max=1000&provincia=" + id)
				.retrieve()
				.bodyToMono(DepartamentosDTO.class).block();
		List<GeografiaDTO> departamentos = d.getDepartamentos();
		Collections.sort(departamentos);
		return departamentos;
	}
	
	public List<GeografiaDTO> getCiudadesByDeptos(String id) {
		CiudadesDTO c = this.client.get()
				.uri("/localidades?max=1000&departamento=" + id)
				.retrieve()
				.bodyToMono(CiudadesDTO.class).block();
		List<GeografiaDTO> ciudades = c.getLocalidades();
		Collections.sort(ciudades);
		return ciudades;
	}
	
}
