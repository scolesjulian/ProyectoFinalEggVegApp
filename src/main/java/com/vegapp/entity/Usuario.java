package com.vegapp.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.veggap.enums.Rol;

import lombok.Data;

@Data
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid",strategy="uuid2")
	protected String id;
	
	protected String nombre;
	protected String apellido;
	protected Integer edad;
	protected String email;
	protected String clave1;
	protected String clave2;
	
	@OneToOne
	protected Zona zona;
	
	@OneToOne(cascade = CascadeType.ALL)
	protected Foto foto;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date alta;
	
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;
	
	@Enumerated(EnumType.STRING)
	protected Rol rol;
	
}