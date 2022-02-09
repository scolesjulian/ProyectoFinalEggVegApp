package com.vegapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vegapp.clients.GeoRefClient;
import com.vegapp.clients.dtos.GeografiaDTO;

@Service
public class GeoRefService {

	private GeoRefClient client;

	@Autowired
	public GeoRefService(GeoRefClient client) {
		this.client = client;
	}

	public List<GeografiaDTO> getProvincias() {
		return this.client.getProvincias();
	}
	
	public List<GeografiaDTO> getDepartamentosByProv(String id){
		return this.client.getDepartamentosByProv(id);
	}
	
	public List<GeografiaDTO> getCiudadesByDeptos(String id){
		return this.client.getCiudadesByDeptos(id);
	}
 }
