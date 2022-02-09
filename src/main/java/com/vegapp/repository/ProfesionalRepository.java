package com.vegapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vegapp.entity.Profesional;

@Repository
public interface ProfesionalRepository extends JpaRepository<Profesional,String>{

}
