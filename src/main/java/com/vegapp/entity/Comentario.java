package com.vegapp.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Comentario {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2" )
	private String id;

	@OneToMany
	private List<Voto> voto;

	private String comentario;
	
	private String titulo;

	@OneToOne
	private Usuario usuario;
	
	public Comentario() {}

	

	public Comentario(String id, List<Voto> voto, String comentario, String titulo, Usuario usuario) {
		super();
		this.id = id;
		this.voto = voto;
		this.comentario = comentario;
		this.titulo = titulo;
		this.usuario = usuario;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Voto> getVoto() {
		return voto;
	}

	public void setVoto(List<Voto> voto) {
		this.voto = voto;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	
	

}
