package com.vegapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vegapp.entity.Foto;

@Repository
public interface FotoRepository extends JpaRepository<Foto, String> {

}
