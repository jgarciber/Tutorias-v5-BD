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

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ITutorias;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;

public class Tutorias implements ITutorias {
	private static final String NOMBRE_FICHERO_TUTORIAS = "datos/tutorias.dat";
	private List<Tutoria> coleccionTutorias;
	
	public Tutorias(){
		coleccionTutorias=new ArrayList<>();
	}
	
public void comenzar() {
		
		File fichero = new File(NOMBRE_FICHERO_TUTORIAS); // fichero
		Tutoria tutoria;
		try {
			// Creamos el flujo de entrada
			FileInputStream filein = new FileInputStream(fichero);
		
			// Indicamos que se trata de un flujo de entrada para leer objetos
			ObjectInputStream dataIS = new ObjectInputStream(filein);
		
			//lectura del fichero
			do{
				tutoria= (Tutoria) dataIS.readObject(); //leer una tutoria
				coleccionTutorias.add(tutoria);			 
			}while (tutoria != null);
															
			dataIS.close(); //cierra flujo de entrada
		}catch (EOFException eo) {
			System.out.println("Fichero leido correctamente.");
			eo.getMessage();
		}catch (FileNotFoundException ex) {
			System.out.println("Fichero tutorias no encontrado");
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
		
		File fichero = new File(NOMBRE_FICHERO_TUTORIAS); // fichero
		List<Tutoria> tutoriasOrdenadas = get();
		try {
			// Creamos el flujo de salida
			FileOutputStream fileout = new FileOutputStream(fichero);
		 
			// Indicamos que se trata de un flujo de salida para escribir objetos
			ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

			if (!tutoriasOrdenadas.isEmpty()) {
				for (Tutoria tutoria:tutoriasOrdenadas) {
					dataOS.writeObject(tutoria); // se escribe objeto tutoria
				}
			}
			System.out.println("Fichero tutorias escrito correctamente.");		
			dataOS.close(); // cierra flujo de salida
		} catch (FileNotFoundException ex) {
			System.out.println("No puedo crear el fichero de tutorias.");
			ex.getMessage();
		}catch (IOException ex) {
			System.out.println("Error inesperado de Entrada/Salida.");
			ex.getMessage();
		}

	}

	@Override
	public List<Tutoria> get() {
		//Las tutorías se ordenarán por profesor y por el nombre de la tutoría
		List<Tutoria> tutoriasOrdenadas = copiaProfundaTutorias();
		Comparator<Profesor> comparadorProfesor= Comparator.comparing(Profesor::getDni);
		tutoriasOrdenadas.sort(Comparator.comparing(Tutoria::getProfesor,comparadorProfesor).thenComparing(Tutoria::getNombre));
		return tutoriasOrdenadas;
	}
	
	private List<Tutoria> copiaProfundaTutorias() {
		List<Tutoria> coleccionCopia= new ArrayList<>();
		for (Tutoria tutoria:coleccionTutorias) {
			coleccionCopia.add(new Tutoria(tutoria));
		}
		return coleccionCopia;
	}
	
	@Override
	public int getTamano() {
		return coleccionTutorias.size();
	}
	
	@Override
	public List<Tutoria> get(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		List<Tutoria> coleccionCopia= new ArrayList<>();
		
		for (Tutoria tutoria:coleccionTutorias) {
			if(coleccionTutorias.get(coleccionTutorias.indexOf(tutoria)).getProfesor().equals(profesor)){
				coleccionCopia.add(new Tutoria(tutoria));
			}
		}
		//Cuando se listen las tutorías de un profesor se mostrarán ordenadas por nombre de la tutoría.
		coleccionCopia.sort(Comparator.comparing(Tutoria::getNombre));
		return coleccionCopia;
	}
	
	@Override
	public void insertar(Tutoria tutoria) throws OperationNotSupportedException{
		if (tutoria==null) {
			throw new NullPointerException("ERROR: No se puede insertar una tutoría nula.");
		}
		//Buscamos si ya existe la cita.
		if(buscar(tutoria)!=null) {
			throw new OperationNotSupportedException("ERROR: Ya existe una tutoría con ese identificador.");
		}
		coleccionTutorias.add(new Tutoria(tutoria));
		System.out.println("Tutoria introducida correctamente.");		
	}
	
	@Override
	public Tutoria buscar(Tutoria tutoria) {
		if(tutoria==null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar una tutoría nula.");
		}
		Tutoria encontrado = null; //si no encuentra cita este método devuelve null.
		int i = coleccionTutorias.indexOf(tutoria); //busca el método equals de Tutoria para obtener el índice
		if (i != -1) {
			encontrado= new Tutoria(coleccionTutorias.get(i));
		}

		return encontrado; //devuelvo una copia de la cita encontrada
	}

	@Override
	public void borrar(Tutoria tutoria) throws OperationNotSupportedException {
		if (tutoria==null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una tutoría nula.");
		}
		if (coleccionTutorias.contains(tutoria)){ //Usa el método equals de tutoria para buscarlo
			coleccionTutorias.remove(tutoria); //Usa el método equals de tutoria para borrarlo
			System.out.println("Tutoria borrada correctamente.");
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna tutoría con ese identificador.");
		}
	}
}

