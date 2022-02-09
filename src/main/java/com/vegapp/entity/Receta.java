package com.vegapp.entity;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Receta {
	@Id
	@GeneratedValue (generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid2")
	private String id;
	
	private String titulo;
	private String descripcion;
	@OneToOne
	private Usuario usuario;
	@OneToOne
	private Foto foto;
	@OneToMany
	private List<Comentario> listComentario;
	
	public Receta() {}
	
	public Receta(String id, String titulo, String descripcion, Usuario usuario, Foto foto, List<Comentario> listComentario) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.usuario = usuario;
		this.foto = foto;
		this.listComentario = listComentario;
		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public List<Comentario> getListComentario() {
		return listComentario;
	}

	public void setListComentario(List<Comentario> listComentario) {
		this.listComentario = listComentario;
	}

	
	 
	
}

