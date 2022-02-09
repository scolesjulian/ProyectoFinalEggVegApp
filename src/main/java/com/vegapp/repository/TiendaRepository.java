package com.vegapp.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vegapp.entity.Tienda;
@Repository
public interface TiendaRepository extends JpaRepository<Tienda, String>{

	@Query("SELECT c FROM Tienda c WHERE c.nombre = :nombre")
	public List<Tienda> findByNombre(@Param("nombre")String nombre);
}


