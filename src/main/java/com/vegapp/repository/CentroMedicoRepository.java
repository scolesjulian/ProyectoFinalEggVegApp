package com.vegapp.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vegapp.entity.CentroMedico;

@Repository
public interface CentroMedicoRepository extends JpaRepository<CentroMedico, String>{
	
	@Query("SELECT c FROM CentroMedico c WHERE c.nombre LIKE %:nombre%")
	public List<CentroMedico> findByNombre(@Param("nombre")String nombre);

}
