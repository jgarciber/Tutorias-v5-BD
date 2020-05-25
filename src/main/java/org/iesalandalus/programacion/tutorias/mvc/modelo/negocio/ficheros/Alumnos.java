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

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IAlumnos;



public class Alumnos implements IAlumnos {
	
	private static final String NOMBRE_FICHERO_ALUMNOS = "datos/alumnos.dat";

	private List<Alumno> coleccionAlumnos;
	
	public Alumnos(){
		coleccionAlumnos=new ArrayList<>();
	}
	
public void comenzar() {
		
		File fichero = new File(NOMBRE_FICHERO_ALUMNOS); // fichero
		Alumno alumno= null;
		try {
			// Creamos el flujo de entrada
			FileInputStream filein = new FileInputStream(fichero);
		
			// Indicamos que se trata de un flujo de entrada para leer objetos
			ObjectInputStream dataIS = new ObjectInputStream(filein);
		
			//lectura del fichero
			do{
				alumno= (Alumno) dataIS.readObject(); //leer una tutoria
				//hay que crear un nuevo alumno antes de añadirlo a la lista para que se introduzca correctamente el identificador del expediente
				//de lo contrario no lleva la cuenta correcta del expediente.
				coleccionAlumnos.add(new Alumno(alumno));			 
			}while (alumno != null);		
															
			dataIS.close(); //cierra flujo de entrada
		}catch (EOFException eo) {
			System.out.println("Fichero leido correctamente.");
			
			//Una vez realizada la lectura de los datos es necesario actualizar el int ultimoIdentificador de la clase Alumno
			//para que cuando se inserte un nuevo alumno, los números de identificación sigan incrementandose en vez de empezar
			//otra vez por el 0.
			Alumno ultimoAlumno = new Alumno(alumno);
			Alumno.actualizaUltimoIdentificador(ultimoAlumno);
			
			eo.getMessage();
		}catch (FileNotFoundException ex) {
			System.out.println("Fichero alumnos no encontrado");
			ex.getMessage();
		}catch (IOException io) {
			System.out.println("Error inesperado de Entrada/Salida.");
			io.getMessage();
		}catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
			e.getMessage();
		} 
	}

/**
 * @param alumno
 */
	
	public void terminar() {
		
		File fichero = new File(NOMBRE_FICHERO_ALUMNOS); // fichero
		List<Alumno> alumnosOrdenados = get();
		try {
			// Creamos el flujo de salida
			FileOutputStream fileout = new FileOutputStream(fichero);
		 
			// Indicamos que se trata de un flujo de salida para escribir objetos
			ObjectOutputStream dataOS = new ObjectOutputStream(fileout);

			if (!alumnosOrdenados.isEmpty()) {
				for (Alumno alumno:alumnosOrdenados) {
					dataOS.writeObject(alumno); // se escribe objeto tutoria
				}
			}
			System.out.println("Fichero alumnos escrito correctamente.");		
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
	public List<Alumno> get() {
		//Los alumnos se ordenarán por su correo.
		List<Alumno> alumnosOrdenados = copiaProfundaAlumnos();
		alumnosOrdenados.sort(Comparator.comparing(Alumno::getCorreo));
		return alumnosOrdenados;
	}
	
	private List<Alumno> copiaProfundaAlumnos() {
		List<Alumno> coleccionCopia= new ArrayList<>();
		for (Alumno alumno:coleccionAlumnos) {
			coleccionCopia.add(new Alumno(alumno));
		}
		return coleccionCopia;
	}
	
	@Override
	public int getTamano() {
		return coleccionAlumnos.size();
	}
	
	@Override
	public void insertar(Alumno alumno) throws OperationNotSupportedException{
		if (alumno==null) {
			throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
		}
		//Buscamos si ya existe la cita.
		if(buscar(alumno)!=null) {
			throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese expediente.");
		}
		coleccionAlumnos.add(new Alumno(alumno));
		System.out.println("Alumno introducido correctamente.");	
	}
	
	@Override
	public Alumno buscar(Alumno alumno) {
		if(alumno==null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un alumno nulo.");
		}
		Alumno encontrado = null; //si no encuentra cita este método devuelve null.
		int i = coleccionAlumnos.indexOf(alumno); //busca el método equals de Alumno para obtener el índice
		if (i != -1) {
			encontrado= new Alumno(coleccionAlumnos.get(i));
		}
		return encontrado; //devuelvo una copia de la cita encontrada
	}

	@Override
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno==null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un alumno nulo.");
		}
		if (coleccionAlumnos.contains(alumno)){ //Usa el método equals de alumno para buscarlo
			coleccionAlumnos.remove(alumno); //Usa el método equals de alumno para borrarlo
			System.out.println("Alumno borrado correctamente.");
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún alumno con ese expediente.");
		}
	}
	
}
