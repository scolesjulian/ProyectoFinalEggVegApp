package com.vegapp.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Tienda {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String nombre;
	private String rubro;
	private String descripcion;
	private String horario;
	private String telefono;
	private String direccion;
	
	@OneToOne
	private Foto foto;
	
	/*@OneToMany
	private List<Comentario> comentarios;*/
	@OneToOne
	private Zona zona;

	

	public Tienda(){}
	
	public Tienda(String id, String nombre, String rubro, String descripcion, String horario, String telefono, String direccion, Foto foto, Zona zona) /*List<Comentario> comentarios, Zona zona, Foto foto)*/ {
		this.id = id;
		this.nombre = nombre;
		this.rubro = rubro;
		this.descripcion = descripcion;
		this.horario = horario;
		this.telefono = telefono;
		this.direccion = direccion;
		this.foto = foto;
		//this.comentarios = comentarios;
		this.zona = zona;
	}
	

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

	public String getRubro() {
		return rubro;
	}

	public void setRubro(String rubro) {
		this.rubro = rubro;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}
	
	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	/*public List<Comentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
*/

}
