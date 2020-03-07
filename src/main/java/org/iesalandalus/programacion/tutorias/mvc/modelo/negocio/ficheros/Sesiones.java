package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ISesiones;

public class Sesiones implements ISesiones {
	private static final String NOMBRE_FICHERO_SESIONES = "datos/sesiones.dat";
	private List<Sesion> coleccionSesiones;
	
	public Sesiones(){
		coleccionSesiones=new ArrayList<>();
	}
	
public void comenzar() {
		
		File fichero = new File(NOMBRE_FICHERO_SESIONES); // fichero
		Sesion sesion;
		try {
			// Creamos el flujo de entrada
			FileInputStream filein = new FileInputStream(fichero);
		
			// Indicamos que se trata de un flujo de entrada para leer objetos
			ObjectInputStream dataIS = new ObjectInputStream(filein);
		
			//lectura del fichero
			do{
				sesion= (Sesion) dataIS.readObject(); //leer una sesion
				coleccionSesiones.add(sesion);			 
			}while (sesion != null);
															
			dataIS.close(); //cierra flujo de entrada
		}catch (EOFException eo) {
			System.out.println("Fichero leido correctamente.");
			eo.getMessage();
		}catch (FileNotFoundException ex) {
			System.out.println("Fichero sesiones no encontrado");
			ex.getMessage();
		}catch (IOException io) {
			System.out.println("Error inesperado de Entrada/Salida.");
			io.getMessage();
		}catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
			e.getMessage();
		} 
	}
	
	public void terminar() {
		
		File fichero = new File(NOMBRE_FICHERO_SESIONES); // fichero
		List<Sesion> sesionesOrdenadas = get();
		try {
			// Creamos el flujo de salida
			FileOutputStream fileout = new FileOutputStream(fichero);
		 
			// Indicamos que se trata de un flujo de salida para escribir objetos
			ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

			if (!sesionesOrdenadas.isEmpty()) {
				for (Sesion sesion:sesionesOrdenadas) {
					dataOS.writeObject(sesion); // se escribe objeto sesion
				}
			}
			System.out.println("Fichero sesiones escrito correctamente.");	
			dataOS.close(); // cierra flujo de salida
		} catch (FileNotFoundException ex) {
			System.out.println("No puedo crear el fichero de sesiones.");
			ex.getMessage();
		}catch (IOException ex) {
			System.out.println("Error inesperado de Entrada/Salida.");
			ex.getMessage();
		}

	}

	@Override
	public List<Sesion> get() {
		//Las sesiones se ordenarán por tutoría y por fecha.
		List<Sesion> sesionesOrdenadas = copiaProfundaSesiones();
		Comparator<Profesor> comparadorProfesor = Comparator.comparing(Profesor::getDni);
		Comparator<Tutoria> comparadorTutoria = Comparator.comparing(Tutoria::getProfesor, comparadorProfesor).thenComparing(Tutoria::getNombre);
		sesionesOrdenadas.sort(Comparator.comparing(Sesion::getTutoria,comparadorTutoria).thenComparing(Sesion::getFecha));
		return sesionesOrdenadas;
	}
	
	private List<Sesion> copiaProfundaSesiones() {
		List<Sesion> coleccionCopia= new ArrayList<>();
		for (Sesion sesion:coleccionSesiones) {
			coleccionCopia.add(new Sesion(sesion));
		}
		return coleccionCopia;
	}
	
	@Override
	public int getTamano() {
		return coleccionSesiones.size();
	}
	
	@Override
	public List<Sesion> get(Tutoria tutoria) {
		if (tutoria == null) {
			throw new NullPointerException("ERROR: La tutoría no puede ser nula.");
		}
		
		List<Sesion> coleccionCopia= new ArrayList<>();
		
		for (Sesion	sesion:coleccionSesiones) {
			if(coleccionSesiones.get(coleccionSesiones.indexOf(sesion)).getTutoria().equals(tutoria)){
				coleccionCopia.add(new Sesion(sesion));
			}
		}
		//Cuando se listen las sesiones de una tutoría se mostrarán ordenadas por fecha de la sesión.
		coleccionCopia.sort(Comparator.comparing(Sesion::getFecha));
		return coleccionCopia;
	}

	@Override
	public void insertar(Sesion sesion) throws OperationNotSupportedException{
		if (sesion==null) {
			throw new NullPointerException("ERROR: No se puede insertar una sesión nula.");
		}
		if(buscar(sesion)!=null) {
			throw new OperationNotSupportedException("ERROR: Ya existe una sesión con esa fecha.");
		}
		coleccionSesiones.add(new Sesion(sesion));
		System.out.println("sesion introducido correctamente.");		
	}
	
	@Override
	public Sesion buscar(Sesion sesion) {
		if(sesion==null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una sesión nula.");
		}
		Sesion encontrado = null; //si no encuentra cita este método devuelve null.
		int i = coleccionSesiones.indexOf(sesion); //busca el método equals de Sesion para obtener el índice
		if (i != -1) {
			encontrado= new Sesion(coleccionSesiones.get(i));
		}
		
		return encontrado; //devuelvo una copia de la cita encontrada
	}

	@Override
	public void borrar(Sesion sesion) throws OperationNotSupportedException {
		if (sesion==null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una sesión nula.");
		}
		if (coleccionSesiones.contains(sesion)){ //Usa el método equals de sesion para buscarlo
			coleccionSesiones.remove(sesion); //Usa el método equals de sesion para borrarlo
			System.out.println("sesion borrado correctamente.");
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna sesión con esa fecha.");
		}
	}
}
