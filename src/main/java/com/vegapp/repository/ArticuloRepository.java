package com.vegapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vegapp.entity.Articulo;
import com.vegapp.entity.Profesional;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, String> {

	public List<Articulo> findByTituloContainingIgnoreCase(String palabra);
	
	public List<Articulo> findByProfesional(Profesional profesional);
}
