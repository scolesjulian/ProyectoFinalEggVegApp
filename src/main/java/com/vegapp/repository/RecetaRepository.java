package com.vegapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vegapp.entity.Receta;

@Repository
public interface RecetaRepository extends JpaRepository <Receta,String> {

}


