package com.vegapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vegapp.entity.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, String> {

}
