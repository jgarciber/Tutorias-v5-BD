package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;



public class Sesion implements Serializable{
	private static final LocalTime HORA_COMIENZO_CLASES = LocalTime.of(16, 00);
	private static final LocalTime HORA_FIN_CLASES = LocalTime.of(22, 15);
	public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
	
	private Tutoria tutoria;
	private LocalDate fecha;
	private LocalTime horaInicio ;
	private LocalTime horaFin;
	private int minutosDuracion;
	
	public Sesion (Tutoria tutoria, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, int minutosDuracion) {
		setTutoria(tutoria);
		setFecha(fecha);
		setHoraInicio(horaInicio);
		setHoraFin(horaFin);
		setMinutosDuracion(minutosDuracion);
		comprobarValidezSesion();
		//al comprobar la validez de la sesion en el último paso, si el método anterior no devuelve ninguna excepción se asume que la sesión es válida y por tanto se crea el objeto.
	}
	
	public Sesion (Sesion sesion) {
		if (sesion == null) {
			throw new NullPointerException("ERROR: No es posible copiar una sesión nula.");
		}
		setTutoria(sesion.getTutoria());
		setFecha(sesion.getFecha());
		setHoraInicio(sesion.getHoraInicio());
		setHoraFin(sesion.getHoraFin());
		setMinutosDuracion(sesion.getMinutosDuracion());
	}
	
	private void comprobarValidezSesion(){
		LocalDate manana= LocalDate.now().plusDays(1);
		if (fecha.isBefore(manana)) {
			throw new IllegalArgumentException("ERROR: Las sesiones de deben planificar para fechas futuras.");
		}
		if (horaInicio.isBefore(HORA_COMIENZO_CLASES) || horaInicio.equals(HORA_FIN_CLASES) || horaInicio.isAfter(HORA_FIN_CLASES)) {
			throw new IllegalArgumentException("ERROR: La hora de inicio no es válida.");
		}
		if (horaFin.isBefore(HORA_COMIENZO_CLASES) || horaFin.isAfter(HORA_FIN_CLASES)) {
			throw new IllegalArgumentException("ERROR: La hora de fin no es válida.");
		}
		if (horaInicio.isAfter(horaFin) || horaInicio.equals(horaFin)) {
			throw new IllegalArgumentException("ERROR: Las hora para establecer la sesión no son válidas.");
		}
		if ((horaInicio.until(horaFin,ChronoUnit.MINUTES) % minutosDuracion) != 0) {
			throw new IllegalArgumentException("ERROR: Los minutos de duración no es divisor de los minutos establecidos para toda la sesión.");
		}
	}
	
	public static Sesion getSesionFicticia(Tutoria tutoria, LocalDate fecha) {
		if (tutoria==null) {
			throw new NullPointerException("ERROR: La tutoría no puede ser nula.");
		}
		if (fecha==null) {
			throw new NullPointerException("ERROR: La fecha no puede ser nula.");
		}
		return new Sesion(tutoria, fecha, HORA_COMIENZO_CLASES, HORA_FIN_CLASES, 1);

	}

	public LocalDate getFecha() {
		return fecha;
	}

	private void setFecha(LocalDate fecha) {
		if (fecha==null) {
			throw new NullPointerException("ERROR: La fecha no puede ser nula.");
		}
		
		this.fecha = fecha;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	private void setHoraInicio(LocalTime horaInicio) {
		if (horaInicio==null) {
			throw new NullPointerException("ERROR: La hora de inicio no puede ser nula.");
		}
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	private void setHoraFin(LocalTime horaFin) {
		if (horaFin==null) {
			throw new NullPointerException("ERROR: La hora de fin no puede ser nula.");
		}
		this.horaFin = horaFin;
	}

	public int getMinutosDuracion() {
		return minutosDuracion;
	}

	private void setMinutosDuracion(int minutosDuracion) {
		if (minutosDuracion<=0) {
			throw new IllegalArgumentException("ERROR: Los minutos de duración no son válidos.");
		}
		this.minutosDuracion = minutosDuracion;
	}

	public Tutoria getTutoria() {
		return new Tutoria(tutoria);
	}

	private void setTutoria(Tutoria tutoria) {
		if (tutoria==null) {
			throw new NullPointerException("ERROR: La tutoría no puede ser nula.");
		}
		this.tutoria = new Tutoria(tutoria);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((tutoria == null) ? 0 : tutoria.hashCode());
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
		Sesion other = (Sesion) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (tutoria == null) {
			if (other.tutoria != null)
				return false;
		} else if (!tutoria.equals(other.tutoria))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("tutoria=profesor="+ getTutoria().getProfesor() + ", nombre=" + getTutoria().getNombre()+ ", fecha=" + fecha.format(FORMATO_FECHA) + ", horaInicio=" + horaInicio.format(FORMATO_HORA) + ", horaFin=" + horaFin.format(FORMATO_HORA) + ", minutosDuracion="+ minutosDuracion);
	}
	
	
	
}
