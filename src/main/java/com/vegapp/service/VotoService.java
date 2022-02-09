package com.vegapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vegapp.entity.Voto;
import com.vegapp.errors.ErrorService;
import com.vegapp.repository.VotoRepository;

@Service
public class VotoService {

	private VotoRepository votoRepository;
	
//	@Transactional
//	public Voto guardarVoto(Voto voto) throws ErrorService {
//		try {
//			if (voto.getUsuario() == null) {
//				throw new ErrorService("Alguno de los campos solicitados estan vacios o son nulos");
//			}
//			return votoRepository.save(voto);
//		} catch (Exception e) {
//			System.err.println(e);
//			throw new ErrorService("No se pudo guardar el Voto");
//		}
//	}
}
