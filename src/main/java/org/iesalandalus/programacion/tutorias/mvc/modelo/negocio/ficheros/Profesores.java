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
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores {
	private static final String NOMBRE_FICHERO_PROFESORES = "datos/profesores.dat";
	private List<Profesor> coleccionProfesores;
	
	public Profesores(){
		coleccionProfesores=new ArrayList<>();
	}
	
public void comenzar() {
		
		File fichero = new File(NOMBRE_FICHERO_PROFESORES); // fichero
		Profesor profesor;
		try {
			// Creamos el flujo de entrada
			FileInputStream filein = new FileInputStream(fichero);
		
			// Indicamos que se trata de un flujo de entrada para leer objetos
			ObjectInputStream dataIS = new ObjectInputStream(filein);
		
			//lectura del fichero
			do{
				profesor= (Profesor) dataIS.readObject(); //leer un profesor
				coleccionProfesores.add(profesor);			 
			}while (profesor != null);
															
			dataIS.close(); //cierra flujo de entrada
		}catch (EOFException eo) {
			System.out.println("Fichero leido correctamente.");
			eo.getMessage();
		}catch (FileNotFoundException ex) {
			System.out.println("Fichero profesores no encontrado");
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
		
		File fichero = new File(NOMBRE_FICHERO_PROFESORES); // fichero
		List<Profesor> profesoresOrdenados = get();
		try {
			// Creamos el flujo de salida
			FileOutputStream fileout = new FileOutputStream(fichero);
		 
			// Indicamos que se trata de un flujo de salida para escribir objetos
			ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

			if (!profesoresOrdenados.isEmpty()) {
				for (Profesor profesor:profesoresOrdenados) {
					dataOS.writeObject(profesor); // se escribe objeto profesor
				}
			}
			System.out.println("Fichero profesores escrito correctamente.");
					
			dataOS.close(); // cierra flujo de salida
		} catch (FileNotFoundException ex) {
			System.out.println("No puedo crear el fichero de profesores.");
			ex.getMessage();
		}catch (IOException ex) {
			System.out.println("Error inesperado de Entrada/Salida.");
			ex.getMessage();
		}

	}

	
	@Override
	public List<Profesor> get() {
		//Los profesores se ordenarán por su DNI.
		List<Profesor> profesoresOrdenados = copiaProfundaProfesores();
		profesoresOrdenados.sort(Comparator.comparing(Profesor::getDni));
		return profesoresOrdenados;
	}
	
	private List<Profesor> copiaProfundaProfesores() {
		List<Profesor> coleccionCopia= new ArrayList<>();
		for (Profesor Profesor:coleccionProfesores) {
			coleccionCopia.add(new Profesor(Profesor));
		}
		return coleccionCopia;
	}
	
	@Override
	public int getTamano() {
		return coleccionProfesores.size();
	}
	
	@Override
	public void insertar(Profesor profesor) throws OperationNotSupportedException{
		if (profesor==null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}
		//Buscamos si ya existe la cita.
		if(buscar(profesor)!=null) {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese DNI.");
		}
		coleccionProfesores.add(new Profesor(profesor));
		System.out.println("Profesor introducido correctamente.");
	}
	
	@Override
	public Profesor buscar(Profesor profesor) {
		if(profesor==null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un profesor nulo.");
		}
		Profesor encontrado = null; //si no encuentra cita este método devuelve null.
		int i = coleccionProfesores.indexOf(profesor); //busca el método equals de Profesor para obtener el índice
		if (i != -1) {
			encontrado= new Profesor(coleccionProfesores.get(i));
		} 
		
		return encontrado; //devuelvo una copia de la cita encontrada
	}

	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor==null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un profesor nulo.");
		}
		if (coleccionProfesores.contains(profesor)){ //Usa el método equals de Profesor para buscarlo
			coleccionProfesores.remove(profesor); //Usa el método equals de Profesor para borrarlo
			System.out.println("Profesor borrado correctamente.");
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese DNI.");
		}
	}
	
}
