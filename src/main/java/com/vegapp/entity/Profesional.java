package com.vegapp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class Profesional extends Usuario{
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid2")
	protected String id;
	
	protected String profesion;
	protected String especialidad;
	protected String matricula;
	protected String telefono;
	
}
