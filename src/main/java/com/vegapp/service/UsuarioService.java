package com.vegapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.vegapp.entity.Foto;
import com.vegapp.entity.Usuario;
import com.vegapp.entity.Zona;
import com.vegapp.errors.ErrorService;
import com.vegapp.repository.UsuarioRepository;
import com.vegapp.repository.ZonaRepository;
import com.veggap.enums.Rol;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	public UsuarioRepository usuarioRepository;

	@Autowired
	public ZonaRepository zonaRepository;

	@Autowired
	public FotoService fotoService;

	@Transactional
	public void crear(MultipartFile archivo, String nombre, String apellido, Integer edad, String email, String clave1,
			String clave2, String idZona) throws ErrorService {
		try {
			validar(nombre, apellido, edad, email, clave1);
			Usuario usuario = new Usuario();

			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setEdad(edad);
			usuario.setEmail(email);

			if (!clave1.equals(clave2)) {
				throw new ErrorService("Las claves no coinciden, intente nuevamente.");
			} else if (clave1.equals(clave2)) {
				String encriptada = new BCryptPasswordEncoder().encode(clave1);
				usuario.setClave1(encriptada);
			}

			Zona zona = zonaRepository.findById(idZona).get();
			usuario.setZona(zona);

			Foto foto = fotoService.guardarFoto(archivo);
			usuario.setFoto(foto);
			
			usuario.setRol(Rol.USUARIO);

			usuarioRepository.save(usuario);
		} catch (Exception e) {
			throw new ErrorService("Error al crear el usuario");
		}
	}

	@Transactional
	public void editar(MultipartFile archivo, String id, String nombre, String apellido, Integer edad, String email,
			String clave1) throws ErrorService {
		try {
			Optional<Usuario> respuesta = usuarioRepository.findById(id);
			if (respuesta.isPresent()) {
				validar(nombre, apellido, edad, email, clave1);
				Usuario usuario = respuesta.get();

				usuario.setNombre(nombre);
				usuario.setApellido(apellido);
				usuario.setEdad(edad);
				usuario.setEmail(email);

				String encriptada = new BCryptPasswordEncoder().encode(clave1);
				usuario.setClave1(encriptada);

				//Zona zona = zonaRepository.findById(idZona).get();
				//usuario.setZona(zona);

				String idFoto = null;
				if (usuario.getFoto() != null) {
					idFoto = usuario.getFoto().getId();
				}

				Foto foto = fotoService.actualizarFoto(idFoto, archivo);

				usuario.setFoto(foto);

				usuarioRepository.save(usuario);
			} else {
				throw new ErrorService("No se encontró un usuario con ese id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ErrorService("Error al editar el usuario");
		}
	}

	@Transactional
	public void eliminar(String id) throws ErrorService {
		try {
			Optional<Usuario> respuesta = usuarioRepository.findById(id);
			if (respuesta.isPresent()) {
				Usuario usuario = respuesta.get();
				usuarioRepository.delete(usuario);
			} else {
				throw new ErrorService("No se encuentró un usuario con ese id");
			}
		} catch (Exception e) {
			throw new ErrorService("Error al eliminar el usuario");
		}
	}
	
	@Transactional
    public void deshabilitar(String id) throws ErrorService {

        Optional<Usuario> respuesta = usuarioRepository.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());
            usuarioRepository.save(usuario);
        } else {

            throw new ErrorService("No se encontró el usuario solicitado");
        }

    }

    @Transactional
    public void habilitar(String id) throws ErrorService {

        Optional<Usuario> respuesta = usuarioRepository.findById(id);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setBaja(null);
            usuarioRepository.save(usuario);
        } else {

            throw new ErrorService("No se encontró el usuario solicitado");
        }

    }
	
	@Transactional
    public void cambiarRol(String id) throws ErrorService{
    
    	Optional<Usuario> respuesta = usuarioRepository.findById(id);
    	
    	if(respuesta.isPresent()) {
    		
    		Usuario usuario = respuesta.get();
    		
    		if(usuario.getRol().equals(Rol.USUARIO)) {
    			
    		usuario.setRol(Rol.ADMIN);
    		
    		}else if(usuario.getRol().equals(Rol.ADMIN)) {
    			usuario.setRol(Rol.USUARIO);
    		}
    	}
    }

	@Transactional
	public void validar(String nombre, String apellido, Integer edad, String email, String clave1) throws ErrorService {
		try {
			if (nombre == null || nombre.isEmpty()) {
				throw new ErrorService("El nombre del usuario no puede ser nulo");
			}
			if (apellido == null || apellido.isEmpty()) {
				throw new ErrorService("El apellido del usuario no puede ser nulo");
			}
			if (edad == null) {
				throw new ErrorService("La edad del usuario no puede ser nulo");
			} else if (edad > 110 || edad < 12) {
				throw new ErrorService("La edad del usuario debe encontrarse entre los 12 a los 110");
			}
			if (email == null || email.isEmpty()) {
				throw new ErrorService("El email del usuario no puede ser nulo");
			}
			if (clave1 == null || clave1.isEmpty()) {
				throw new ErrorService("La clave del usuario no puede ser nulo");
			} else if (clave1.length() < 6) {
				throw new ErrorService("La clave del usuario debe contener más de 6 caracteres");
			}
		} catch (Exception e) {
			throw new ErrorService("Error en la validacion de usuario");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.buscarPorEmail(email);
		if (usuario != null) {

			List<GrantedAuthority> permisos = new ArrayList<>();

			GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_"+ usuario.getRol());
			permisos.add(p1);

			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("usuariosession", usuario);

			User user = new User(usuario.getEmail(), usuario.getClave1(), permisos);

			return user;
		} else {
			return null;
		}
	}
	
}
