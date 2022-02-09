package com.vegapp.clients.dtos;

public class GeografiaDTO implements Comparable<GeografiaDTO>{

	private String id;
	private String nombre;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	@Override
	public int compareTo(GeografiaDTO o) {
		return this.nombre.compareTo(o.getNombre());
	}
	
	
}
