package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

import java.io.Serializable;

public class Tutoria implements Serializable{
	private String nombre;
	private Profesor profesor;
	
	public Tutoria(Profesor profesor, String nombre) {
		setProfesor(profesor);
		setNombre(nombre);
	}
	
	public Tutoria(Tutoria tutoria) {
		if (tutoria == null) {
			throw new NullPointerException("ERROR: No es posible copiar una tutoría nula.");
		}
		setProfesor(tutoria.getProfesor());
		setNombre(tutoria.getNombre());
	}

	public String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if (nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		
		this.nombre = nombre.trim();
	}

	public Profesor getProfesor() {
		return new Profesor(profesor);
	}

	private void setProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		this.profesor = new Profesor(profesor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((profesor == null) ? 0 : profesor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tutoria other = (Tutoria) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (profesor == null) {
			if (other.profesor != null)
				return false;
		} else if (!profesor.equals(other.profesor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  "profesor=" + profesor + ", nombre=" + nombre;
	}
	
	
	
	
}
