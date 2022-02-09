package com.vegapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vegapp.entity.Comentario;
import com.vegapp.entity.Usuario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, String> {

	
	public List<Comentario> findByTituloContainingIgnoreCase(String palabra);
	
	
	public List<Comentario>  findByUsuario(Usuario usuario);
}
