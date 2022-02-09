package com.vegapp.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Articulo {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String titulo;
	private String descripcion;

	@OneToMany
	private List<Voto> votos;
	@OneToMany
	private List<Comentario> comentarios;
	@OneToOne
	private Foto foto;
	@OneToOne
	private Profesional profesional;

	public Articulo() {
	};

	public Articulo(String id, String titulo, String descripcion, List<Voto> votos, List<Comentario> comentarios,
			Foto foto, Profesional profesional) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.votos = votos;
		this.comentarios = comentarios;
		this.foto = foto;
		this.profesional = profesional;
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

	public List<Voto> getVotos() {
		return votos;
	}

	public void setVotos(List<Voto> votos) {
		this.votos = votos;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public Profesional getProfesional() {
		return profesional;
	}

	public void setProfesional(Profesional profesional) {
		this.profesional = profesional;
	}

}
