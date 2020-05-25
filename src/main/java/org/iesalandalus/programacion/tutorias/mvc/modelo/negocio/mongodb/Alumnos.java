package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.mongodb;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.bson.Document;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.mongodb.utilidades.MongoDB;

import com.mongodb.client.MongoCollection;

public class Alumnos implements IAlumnos {

	private static final String COLECCION = "alumnos";
	
	private MongoCollection<Document> coleccionAlumnos;

	@Override
	public void comenzar() {
		coleccionAlumnos = MongoDB.getBD().getCollection(COLECCION);
	}

	@Override
	public void terminar() {
		MongoDB.cerrarConexion();
	}

	@Override
	public List<Alumno> get() {
		List<Alumno> alumnos = new ArrayList<>();
		for (Document documentoAlumno : coleccionAlumnos.find().sort(MongoDB.getCriterioOrdenacionAlumno())) {
			alumnos.add(MongoDB.getAlumno(documentoAlumno));
		}
		return alumnos;
	}


	@Override
	public int getTamano() {
		return (int)coleccionAlumnos.countDocuments();
	}

	@Override
	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede insertar una alumno nula.");
		}
		if (buscar(alumno) != null) {
			throw new OperationNotSupportedException("ERROR: Ya existe una alumno igual.");
		} else {
			coleccionAlumnos.insertOne(MongoDB.getDocumento(alumno));
		} 
	}

	@Override
	public Alumno buscar(Alumno alumno) {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un alumno nulo.");
		}
		Document documentoAlumno = coleccionAlumnos.find()
				.filter(and(eq(MongoDB.CORREO, alumno.getCorreo()))).first();
		return MongoDB.getAlumno(documentoAlumno);	
	}

	@Override
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		if (alumno == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un alumno nulo.");
		}
		if (buscar(alumno) != null) {
			coleccionAlumnos.deleteOne(and(eq(MongoDB.CORREO, alumno.getCorreo())));
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningun alumno con ese correo.");
		} 
	}

}
