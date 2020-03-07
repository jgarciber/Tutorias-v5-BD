package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Alumno implements Serializable {
	private static final String ER_NOMBRE ="[a-zA-ZáéíóúüÁÉÍÓÓÜ]+";
	//static final String ER_NOMBRE ="[a-zA-ZáéíóúÁÉÍÓÚ]+(\\s+[a-zA-ZáíéóúÁÉÍÓÚ]+)+";
	private static final String PREFIJO_EXPEDIENTE ="SP_";
	//DUDAAAAAAAAAAAAAAAAAAAAAAAAAAA ¿no se aplica formato al inicio del correo? empieza directamente con .@
	private static final String ER_CORREO  = ".+@[a-zA-Z]+\\.[a-zA-Z]+";
	private static int ultimoIdentificador=0;
	private String nombre;
	private String correo;
	private String expediente;
	
	public Alumno(String nombre, String correo) {
		setNombre(nombre);
		setCorreo(correo);
		setExpediente();
	}
	
	public Alumno(Alumno alumno) {
		if (alumno == null) {
			throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
		}
		setNombre(alumno.getNombre());
		setCorreo(alumno.getCorreo());
		expediente=alumno.getExpediente();
	}
	
	public static Alumno getAlumnoFicticio(String correo) {
		return new Alumno("Alumno Ficticio", correo);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		
		Pattern patron = null;
        Matcher comparador;
        patron = Pattern.compile(ER_NOMBRE);
        comparador = patron.matcher(nombre);
		if (comparador.matches() || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		nombre=formateaNombre(nombre);
		this.nombre = nombre;
	}
	
	private String formateaNombre(String nombre) {
		String nombreFormat, palabraFormateada, palabraMayus, nombreResultado;
		//nombreFormat es una variable que sirve para realizar las modificaciones del nombre
		//palabraFormateada es el resultado de todas los modificaciones realizadas con una palabra
		//nombreResultado es el resultado de la suma de todas las palabras formateadas
		nombreResultado="";
		char primerCaracter;
		int finPalabra;
		
		nombreFormat=nombre.toLowerCase().trim();//elimino espacios en blanco al inicio y al final de la cadena
		String []palabras=nombreFormat.split("\\s+");
			
	
		for(int i=0;i<palabras.length;i++){			
			finPalabra=palabras[i].length();
			palabraMayus=palabras[i].substring(0,finPalabra).toUpperCase(); //obtengo la palabra en mayúsculas.
			primerCaracter=palabraMayus.charAt(0);
			palabraFormateada=primerCaracter+palabras[i].substring(0+1,finPalabra);
			if (i!=0) {
				nombreResultado+=" ";
			}
			nombreResultado+=palabraFormateada;
		}
		return nombreResultado;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	private void setCorreo(String correo) {
		if(correo == null) {
			throw new NullPointerException("ERROR: El correo no puede ser nulo.");
		}
	
		Pattern patron = null;
        Matcher comparador;
        patron = Pattern.compile(ER_CORREO);
        comparador = patron.matcher(correo);
		if (!comparador.matches() || correo.trim().isEmpty()) {
			throw new IllegalArgumentException("ERROR: El formato del correo no es válido.");
		}
		this.correo = correo;
	}
	
	public String getExpediente() {
		return expediente;
	}
	
	private void setExpediente() {
		incrementaUltimoIdentificador();
		this.expediente = PREFIJO_EXPEDIENTE+getIniciales()+"_"+(ultimoIdentificador);
	}
	
	private static void incrementaUltimoIdentificador() {
		ultimoIdentificador++;
	}
	
	private String getIniciales(){
		String iniciales="";
		
		String [] palabras=nombre.split("\\s+");
		
		for(int i=0;i<palabras.length;i++)
		{
			iniciales=iniciales+palabras[i].charAt(0);
		}
		
		return iniciales;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
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
		Alumno other = (Alumno) obj;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("nombre=%s (%s), correo=%s, expediente=%s", nombre, getIniciales().toUpperCase(), correo,expediente);
		//return "nombre=" + nombre +" ("+ getIniciales().toUpperCase() + "), correo=" + correo + ", expediente=" + expediente;
	}
	
	
	
	
	
	
}
