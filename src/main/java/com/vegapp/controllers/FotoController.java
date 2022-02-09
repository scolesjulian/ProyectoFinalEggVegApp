package com.vegapp.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vegapp.entity.CentroMedico;
import com.vegapp.entity.Tienda;
import com.vegapp.entity.Usuario;
import com.vegapp.errors.ErrorService;
import com.vegapp.repository.CentroMedicoRepository;
import com.vegapp.repository.TiendaRepository;
import com.vegapp.repository.UsuarioRepository;
import com.vegapp.service.UsuarioService;

@Controller
@RequestMapping("/foto")
public class FotoController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @GetMapping("/usuario/{id}")
    public ResponseEntity<byte[]> fotoUsuario(@PathVariable String id) {

        try {
            Usuario usuario = usuarioRepository.findById(id).get();
            if (usuario.getFoto() == null) {
                throw new ErrorService("El usuario no tiene una foto asignada.");
            }
            byte[] foto = usuario.getFoto().getContenido();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (ErrorService ex) {
            Logger.getLogger(FotoController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Autowired
	private TiendaRepository tiendaRepository;
	
	@Autowired
	private CentroMedicoRepository centroMedicoRepository;
	
	
	@GetMapping("/tienda/{id}")
	public ResponseEntity<byte[]> fotoTienda(@PathVariable String id) {
		
		try {
			Tienda tienda = tiendaRepository.getById(id);
			
			if(tienda.getFoto() == null) {
				throw new ErrorService("Sin foto asignada");
			}
			
			byte[] foto = tienda.getFoto().getContenido();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);
			
			return new ResponseEntity<>(foto, headers, HttpStatus.OK);
		}catch(ErrorService e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/centroMedico/{id}")
	public ResponseEntity<byte[]> fotoCentroMedico(@PathVariable String id) {
		
		try {
			CentroMedico centroMedico = centroMedicoRepository.getById(id);
			
			if(centroMedico.getFoto() == null) {
				throw new ErrorService("Sin foto asignada");
			}
			
			byte[] foto = centroMedico.getFoto().getContenido();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);
			
			return new ResponseEntity<>(foto, headers, HttpStatus.OK);
		}catch(ErrorService e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
}
