package org.iesalandalus.programacion.tutorias.mvc.modelo;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ICitas;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ISesiones;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ITutorias;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Modelo implements IModelo {
	
	private IProfesores profesores;
	private ITutorias tutorias;
	private ISesiones sesiones;
	private ICitas citas;
	private IAlumnos alumnos;
	
	public Modelo(IFuenteDatos fuenteDatos) {
		//profesores = crearProfesores();

		profesores = fuenteDatos.crearProfesores();
		tutorias = fuenteDatos.crearTutorias();
		sesiones = fuenteDatos.crearSesiones();
		citas = fuenteDatos.crearCitas();
		alumnos = fuenteDatos.crearAlumnos();

		/* = new Profesores();
		tutorias = new Tutorias();
		sesiones = new Sesiones();
		citas = new Citas();
		alumnos = new Alumnos();*/
	}
	
	public void comenzar() {
		alumnos.comenzar();
		profesores.comenzar();
		tutorias.comenzar();
		sesiones.comenzar();
		citas.comenzar();
	}
	
	public void terminar() {
		alumnos.terminar();
		profesores.terminar();
		tutorias.terminar();
		sesiones.terminar();
		citas.terminar();
	}
	
	@Override
	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		alumnos.insertar(alumno);
	}
	
	@Override
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		profesores.insertar(profesor);
	}
	
	@Override
	public void insertar(Tutoria tutoria) throws OperationNotSupportedException {
		//compruebo que el profesor existe ya que la tutoria no podrá ser creada si el profesor no existe
		//lo hago desde el modelo ya que es la clase que reune todos lo métodos buscar del negocio, no se podría implementar esta
		//condición dentro de la clase Tutorias ya que carece del método buscar por profesor.
		if(tutoria==null) {
			throw new NullPointerException("ERROR: No se puede insertar una tutoría nula.");
		}
		Profesor profesor=buscar(tutoria.getProfesor());
		if(profesor==null){
			throw new OperationNotSupportedException("ERROR: No existe el profesor de esta tutoría.");
		}
		
		//Creo una nueva tutoria con el resultado de la busqueda del profesor que devolverá un profesor si existe con todos sus datos, y por otro lado el nombre de la tutoria
		Tutoria tutoria2= new Tutoria(profesor,tutoria.getNombre());
		tutorias.insertar(tutoria2);
	}
	
	@Override
	public void insertar(Sesion sesion) throws OperationNotSupportedException{
		//Si la tutoría existe entonces también existe el profesor ya que la tutoría se compone de un profesor entre otros parametros
		if(sesion==null) {
			throw new NullPointerException("ERROR: No se puede insertar una sesión nula.");
		}
		Tutoria tutoria=buscar(sesion.getTutoria());
		if (tutoria==null) {
			throw new OperationNotSupportedException("ERROR: No existe la tutoría de esta sesión.");
		}
		//Creo una nueva sesion con el resultado de la busqueda de la tutoría que devolverá una tutoría si existe con todos sus datos, además de la fecha, horaInicio, horaFin y los minutos de duración de la sesión

		Sesion sesion2= new Sesion(tutoria,sesion.getFecha(),sesion.getHoraInicio(),sesion.getHoraFin(),sesion.getMinutosDuracion());
		sesiones.insertar(sesion2);
	}
	
	@Override
	public void insertar(Cita cita) throws OperationNotSupportedException  {
		if(cita==null) {
			throw new NullPointerException("ERROR: No se puede insertar una cita nula.");
		}
		if(buscar(cita.getAlumno())==null){
			throw new OperationNotSupportedException("ERROR: No existe el alumno de esta cita.");
		}
		if(buscar(cita.getSesion())==null) {
			throw new OperationNotSupportedException("ERROR: No existe la sesión de esta cita.");
		}
		LocalDate manana= LocalDate.now().plusDays(1);
		
		/* esta comprobación se ha añadido para asegurarse de que al insertar una cita, la sesión sea en el futuro y no esté caducada.
		esta situación se produce ya que los datos almacenados en archivos o en bases de datos pueden ser antiguos y para evitar problemas
		con la lectura de los datos se debe permitir crear sesiones con fechas anteriores a la actual, pero no crear una cita con una sesión
		de fecha desactualizada. Aquí se realiza esa comprobación para evita citas con fechas anteriores a la actual.
		*/
		if (cita.getSesion().getFecha().isBefore(manana)) {
			throw new IllegalArgumentException("ERROR: Está sesión ya ha caducado, no es posible concretar la cita.");
		}
		
		//añado esta comprobación para evitar que un alumno concrete una cita que otro alumno ya ha realizado en esa sesión y hora.
		List<Cita> coleccionCitas = new ArrayList<>();
		coleccionCitas = getCitas(cita.getSesion());
		for (Cita citaColeccion : coleccionCitas) {
			if (citaColeccion.getHora()==cita.getHora()) {
				throw new OperationNotSupportedException("ERROR: Ya existe un alumno con una cita para esa sesión y hora.");
			}
		}
		//Creo una nueva cita con el resultado de la busqueda del alumno que devolverá un alumno si existe con todos sus datos, el resultado de la búsqueda de la sesión que devolverá una sesión si existe con todos sus datos, ademaás de la hora de la cita

		Cita cita2= new Cita(buscar(cita.getAlumno()),buscar(cita.getSesion()),cita.getHora());
		citas.insertar(cita2);
	}
	
	
	
	@Override
	public Alumno buscar(Alumno alumno) {
		//La Vista ya se encarga de mostrar el resultado, haya encontrodo o no el alumno. En caso de no haberlo encontrado se pasa un null que se interpreta en la Vista
		return alumnos.buscar(alumno);
	}
	
	@Override
	public Profesor buscar(Profesor profesor) {
		return profesores.buscar(profesor);
	}
	
	@Override
	public Tutoria buscar(Tutoria tutoria) {
		return tutorias.buscar(tutoria);
	}
	
	@Override
	public Sesion buscar(Sesion sesion) {
		return sesiones.buscar(sesion);
	}
	
	@Override
	public Cita buscar(Cita cita) {
		return citas.buscar(cita);
	}
	
	
	
	@Override
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		List<Cita> coincidencias= getCitas(alumno); // EL ArrayList ya lo crea el método getCitas
		/* Código antiguo
		int opcion = 0;
		if(!coincidencias.isEmpty()){
			do {
				System.out.println("Este alumno tiene asociadas "+coincidencias.size()+" citas");
				System.out.println("¿Desea continuar con el borrado del alumno y todas sus citas asociadas?, introduzca 1(Sí) o 0(No)");
				opcion=Entrada.entero();
				if(opcion==1) {
					for(Cita cita:coincidencias) {
						citas.borrar(cita);
					}
					alumnos.borrar(alumno);
				}
			}while(opcion!=0 && opcion!=1);
		}
		 */
		if(!coincidencias.isEmpty()){
			System.out.println("Este alumno tiene asociadas "+coincidencias.size()+" citas");
			for(Cita cita:coincidencias) {
				citas.borrar(cita);
			}
		}
		alumnos.borrar(alumno);
	}
	
	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException {		
		List<Tutoria> coincidencias= getTutorias(profesor); // EL ArrayList ya lo crea el método getTutorias
		if(!coincidencias.isEmpty()){
			System.out.println("Este profesor tiene asociadas "+coincidencias.size()+" tutorias");
			for(Tutoria tutoria:coincidencias) {		
				borrar(tutoria);
			}
		}
		profesores.borrar(profesor);
	}
	
	@Override
	public void borrar(Tutoria tutoria) throws OperationNotSupportedException {		
		List<Sesion> coincidencias= getSesiones(tutoria); // EL ArrayList ya lo crea el método getSesiones
		if(!coincidencias.isEmpty()){
			System.out.println("Esta tutoría tiene asociadas "+coincidencias.size()+" sesiones");
			for(Sesion sesion:coincidencias) {
				borrar(sesion);
			}
		}
		tutorias.borrar(tutoria);
	}
	
	@Override
	public void borrar(Sesion sesion) throws OperationNotSupportedException {		
		List<Cita> coincidencias= getCitas(sesion); // EL ArrayList ya lo crea el método getCitas
		if(!coincidencias.isEmpty()){
			System.out.println("Esta sesión tiene asociadas "+coincidencias.size()+" citas");
			for(Cita cita:coincidencias) {
				borrar(cita);
			}
		}
		sesiones.borrar(sesion);
	}
	
	@Override
	public void borrar(Cita cita) throws OperationNotSupportedException {
		citas.borrar(cita);
	}
	
	
	@Override
	public List<Alumno> getAlumnos() {
		return alumnos.get();
	}
	
	@Override
	public List<Profesor> getProfesores() {
		return profesores.get();
	}
	
	@Override
	public List<Tutoria> getTutorias() {
		return tutorias.get();
	}
	
	@Override
	public List<Tutoria> getTutorias(Profesor profesor) {
		return tutorias.get(profesor);
	}
	
	@Override
	public List<Sesion> getSesiones() {
		return sesiones.get();
	}
	
	@Override
	public List<Sesion> getSesiones(Tutoria tutoria) {
		return sesiones.get(tutoria);
	}
	
	@Override
	public List<Cita> getCitas() {
		return citas.get();
	}
	
	@Override
	public List<Cita> getCitas(Sesion sesion) {
		return citas.get(sesion);
	}
	
	@Override
	public List<Cita> getCitas(Alumno alumno) {
		return citas.get(alumno);
	}
	
	

}

