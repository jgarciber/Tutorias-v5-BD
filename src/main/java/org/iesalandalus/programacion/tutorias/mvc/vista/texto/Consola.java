package org.iesalandalus.programacion.tutorias.mvc.vista.texto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.utilidades.Entrada;


public class Consola {

	private Consola() {	
	}
	
	public static void mostrarMenu() {
		for (Opcion opcion: Opcion.values()) {
			System.out.println(opcion);
		}
	}
	
	public static void mostrarCabecera(String mensaje) {
		System.out.printf("%n%s%n", mensaje);
		//el formato de la cadena sera un número (%d) que ocupa una anchura de mensaje.length() y todo el espacio a su izquierda relleno de "0"
		String formatoStr = "%0" + mensaje.length() + "d%n";
		System.out.println(String.format(formatoStr, 0).replace("0", "-"));
	}
	
	public static int elegirOpcion() {
		int ordinalOpcion;
		do {
			System.out.print("\nElige una opción: ");
			ordinalOpcion = Entrada.entero();
		} while (!Opcion.esOrdinalValido(ordinalOpcion));
		return ordinalOpcion;
	}
	
	public static Alumno leerAlumno() {
		System.out.print("Introduce el nombre del alumno: ");
		String nombre = Entrada.cadena();
		System.out.print("Introduce el correo del alumno: ");
		String correo = Entrada.cadena();
		
		return new Alumno(nombre, correo);
	}
	
	public static Alumno leerAlumnoFicticio() {
		System.out.print("Introduce el correo del alumno: ");
		return Alumno.getAlumnoFicticio(Entrada.cadena());
	}
	
	public static Profesor leerProfesor() {
		System.out.print("Introduce el nombre del profesor: ");
		String nombre = Entrada.cadena();
		System.out.print("Introduce el DNI del profesor: ");
		String dni = Entrada.cadena();
		System.out.print("Introduce el correo del profesor: ");
		String correo = Entrada.cadena();

		return new Profesor(nombre, dni, correo);
	}
	
	public static Profesor leerProfesorFicticio() {
		System.out.print("Introduce el DNI del profesor: ");
		String dni=Entrada.cadena();
		return Profesor.getProfesorFicticio(dni);
	}
	
	public static Tutoria leerTutoria() {
		System.out.print("Introduce el nombre de la tutoría: ");
		String nombre = Entrada.cadena();
		System.out.println("El profesor debe haberse registrado previamente");
		System.out.println("Se buscará el profesor en la base de datos");
		Profesor profesor = leerProfesorFicticio();
		return new Tutoria(profesor, nombre);
	}
	
	public static Sesion leerSesion() {
		System.out.println("La tutoría debe haberse registrado previamente");

		Tutoria tutoria = leerTutoria();

		/*
		System.out.print("Introduce el día: ");
		int dia = Entrada.entero();
		System.out.print("Introduce el número del mes: ");
		int mes = Entrada.entero();
		System.out.print("Introduce el año: ");
		int ano = Entrada.entero();
		*/
		
		LocalDate fecha = null;
		String fechaIntroducida;
		do {
			System.out.println("Introduce la fecha con formato(dd/MM/yyyy)");
			System.out.print("Fecha: ");
			fechaIntroducida=Entrada.cadena();
			try 
			{
				fecha = LocalDate.parse(fechaIntroducida, Sesion.FORMATO_FECHA);
			} 
			catch (DateTimeParseException e) 
			{
				fecha = null;
				System.out.print("Formato incorrecto de la fecha");
			}
		} while (fecha == null);
		
		LocalTime horaInicio,horaFin = null;
		String horaIntroducida1, horaIntroducida2;
		do {
			System.out.println("Introduce la hora con formato(HH:mm)");
			System.out.print("Hora inicio: ");
			horaIntroducida1=Entrada.cadena();
			System.out.print("Hora fin: ");
			horaIntroducida2=Entrada.cadena();
			try 
			{
				horaInicio = LocalTime.parse(horaIntroducida1, Sesion.FORMATO_HORA);
				horaFin = LocalTime.parse(horaIntroducida2, Sesion.FORMATO_HORA);
			} 
			catch (DateTimeParseException e) 
			{
				horaInicio = null;
				System.out.print("Formato incorrecto de la hora");
			}
		} while (horaInicio == null);
		
		int minutosDuracion;
		System.out.println("Introduzca la duración de la sesión en minutos");
		System.out.print("Minutos sesión:");
		minutosDuracion=Entrada.entero();

		return new Sesion(tutoria, fecha, horaInicio, horaFin, minutosDuracion);
		//return new Sesion(tutoria, LocalDate.of(ano, mes, dia), Sesion.HORA_COMIENZO_CLASES, Sesion.HORA_FIN_CLASES, minutosDuracion);
	}
	
	public static Sesion leerSesionFicticia() {
		Tutoria tutoria = leerTutoria();
		LocalDate fecha = null;
		String fechaIntroducida;
		do {
			System.out.println("Introduce la fecha con formato(dd/MM/yyyy)");
			System.out.print("Fecha: ");
			fechaIntroducida=Entrada.cadena();
			try 
			{
				fecha = LocalDate.parse(fechaIntroducida, Sesion.FORMATO_FECHA);
			} 
			catch (DateTimeParseException e) 
			{
				fecha = null;
				System.out.print("Formato incorrecto de la fecha");
			}
		} while (fecha == null);

		return Sesion.getSesionFicticia(tutoria,fecha);
	}
	
	public static Cita leerCita() {
		System.out.println("El alumno y la sesión deben haberse registrado previamente");
		System.out.println("Se buscará el alumno y la sesión en la base de datos");
		Alumno alumno = leerAlumnoFicticio();
		Sesion sesion = leerSesionFicticia();
		LocalTime hora= null;
		String horaIntroducida1;
		do {
			System.out.println("Introduce la hora con formato(HH:mm)");
			System.out.print("Hora: ");
			horaIntroducida1=Entrada.cadena();
			try 
			{
				hora = LocalTime.parse(horaIntroducida1, Cita.FORMATO_HORA);
			} 
			catch (DateTimeParseException e) 
			{
				hora = null;
				System.out.print("Formato incorrecto de la hora");
			}
		} while (hora == null);
	
		return new Cita(alumno, sesion, hora);
	}
	
}
