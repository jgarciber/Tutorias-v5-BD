package org.iesalandalus.programacion.tutorias.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Profesor implements Serializable{
	 
	private static final String ER_NOMBRE ="[a-zA-ZáéíóúüÁÉÍÓÓÜ]+";
	private static final String ER_DNI = "([0-9]{8})([A-Za-z])";
	private static final String ER_CORREO = ".+@[a-zA-Z]+\\.[a-zA-Z]+";
	
	private String nombre;
	private String dni;
	private String correo;
	
	public Profesor(String nombre, String dni, String correo) {
		setNombre(nombre);
		setDni(dni);
		setCorreo(correo);
	}
		
	public Profesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: No es posible copiar un profesor nulo.");
		}
		setNombre(profesor.getNombre());
		setDni(profesor.getDni());
		setCorreo(profesor.getCorreo());
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
	
	public String getDni() {
		return dni;
	}
	
	private void setDni(String dni) {
		if(dni == null) {
			throw new NullPointerException("ERROR: El DNI no puede ser nulo.");
		}
		dni=dni.toUpperCase();
		if (dni.trim().isEmpty()) {
			throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido.");
		}
		if(!comprobarLetraDni(dni)) {
			throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido.");
		}
		
		this.dni = dni;
	}
	
	private boolean comprobarLetraDni(String dni){
		if (dni==null || dni.trim().isEmpty())
			throw new NullPointerException("ERROR: El DNI de un paciente no puede ser nulo o vacío.");
		
		char[] LETRAS_DNI = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
      	Pattern patron = null;
        Matcher comparador;
        patron = Pattern.compile(ER_DNI);
        comparador = patron.matcher(dni);
        boolean dniCorrecto=false;
        char letra;
        int numerosDni;
        
        if (!comparador.matches()) {
        	return false;
        }
        
        try {
           numerosDni = Integer.parseInt(comparador.group(1));
           /*convierto la parte de la cadena dni que contiene los digitos a un tipo int para 
            * luego poder dividir dicho número. Una cadena no se puede dividir aunque se indique
            *  con (int).
            */
        }
        catch (NumberFormatException e)
        //this function can throw a NumberFormatException, which of course you have to handle
        {
           numerosDni = 0;
        }
        
        letra=LETRAS_DNI[numerosDni % 23];
      
        
        if (comparador.group(2).charAt(0)==letra) {
        	/*se indica charAt(0) ya que comparador.group(2) es una cadena aunque sea de un 
        	solo carácter. No se puede comparar una cadena con un carácter.*/
        	dniCorrecto=true;
        }else {
        	throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta.");
        }
        return dniCorrecto;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	private void setCorreo(String correo) {
		if (correo == null) {
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
	
	
	public static Profesor getProfesorFicticio(String dni) {
		return new Profesor("Profesor Ficticio", dni, "profesorficticio@hotmail.com");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Profesor other = (Profesor) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("nombre=%s (%S), DNI=%s, correo=%s", nombre, getIniciales().toUpperCase(), dni, correo);

		//String cadenaTelefono = (telefono == null) ? "" : ", teléfono=" + telefono;
		//return String.format("nombre=%s, correo=%s%s", nombre, correo, cadenaTelefono);
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
	
}
