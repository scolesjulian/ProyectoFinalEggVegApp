package com.vegapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vegapp.entity.Zona;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, String> {

	
}
