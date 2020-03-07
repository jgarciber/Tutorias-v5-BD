package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;



public class Cita implements Serializable {
	public static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
	private LocalTime hora;
	private Sesion sesion;
	private Alumno alumno;
	
	public Cita(Alumno alumno, Sesion sesion, LocalTime hora){
		setAlumno(alumno);
		setSesion(sesion);
		setHora(hora);
	}
	
	public Cita(Cita cita) {
		if (cita==null) {
			throw new NullPointerException("ERROR: No es posible copiar una cita nula.");
		}
		setAlumno(cita.getAlumno());
		setSesion(cita.getSesion());
		setHora(cita.getHora());
	}
	
	public Alumno getAlumno() {
		return new Alumno(alumno);
	}

	private void setAlumno(Alumno alumno) {
		if (alumno==null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		this.alumno = new Alumno(alumno);
	}
	
	public Sesion getSesion() {
		return new Sesion(sesion);
	}

	private void setSesion(Sesion sesion) {
		if (sesion==null) {
			throw new NullPointerException("ERROR: La sesión no puede ser nula.");
		}
		this.sesion = new Sesion(sesion);
	}

	public LocalTime getHora() {
		return hora;
	}

	private void setHora(LocalTime hora) {
		if (hora==null) {
			throw new NullPointerException("ERROR: La hora no puede ser nula.");
		}
		if (hora.isBefore(sesion.getHoraInicio()) || hora.isAfter(sesion.getHoraFin().minusMinutes(sesion.getMinutosDuracion()))) {
			throw new IllegalArgumentException("ERROR: La hora debe estar comprendida entre la hora de inicio y fin de la sesión.");
		}
		
		//He declarado HORA_COMIENZO_CLASES Y HORA_FIN_CLASES como public ya que de lo contrario este método no funciona, no se puede acceder al valor de las constantes de la clase Sesion
		
		if (hora.until(sesion.getHoraInicio(), ChronoUnit.MINUTES) % sesion.getMinutosDuracion() != 0) {
			throw new IllegalArgumentException("ERROR: La hora debe comenzar en un múltiplo de los minutos de duración.");
		}
		this.hora = hora;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alumno == null) ? 0 : alumno.hashCode());
		result = prime * result + ((hora == null) ? 0 : hora.hashCode());
		result = prime * result + ((sesion == null) ? 0 : sesion.hashCode());
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
		Cita other = (Cita) obj;
		if (alumno == null) {
			if (other.alumno != null)
				return false;
		} else if (!alumno.equals(other.alumno))
			return false;
		if (hora == null) {
			if (other.hora != null)
				return false;
		} else if (!hora.equals(other.hora))
			return false;
		if (sesion == null) {
			if (other.sesion != null)
				return false;
		} else if (!sesion.equals(other.sesion))
			return false;
		return true;
	}

	@Override
	public String toString() {
		//return "alumno=" + getAlumno().toString() + ", sesion=" + getSesion().toString() + ", hora=" + getHora();
		return String.format("alumno=%s, sesion=%s, hora=%s", alumno, sesion, hora.format(FORMATO_HORA));
	
		//DUDAAaaaaaaaaaaaaaaaaaaaaaaaaaa
	}
	

	
}
