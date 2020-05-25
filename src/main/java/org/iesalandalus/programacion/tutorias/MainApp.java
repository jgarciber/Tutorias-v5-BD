package org.iesalandalus.programacion.tutorias;

import org.iesalandalus.programacion.tutorias.mvc.controlador.Controlador;
import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.IModelo;
import org.iesalandalus.programacion.tutorias.mvc.modelo.Modelo;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.FactoriaFuenteDatos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.tutorias.mvc.vista.FactoriaVista;
import org.iesalandalus.programacion.tutorias.mvc.vista.IVista;

public class MainApp {

	private static IModelo modelo;
	private static IVista vista;
	private static IControlador controlador;
	
	public static void main(String[] args) {
		//System.out.println("Gestión Tutorías del IES Al-Ándalus");
		IModelo modelo = new Modelo(procesarArgumentosFuenteDatos(args));
		IVista vista = procesarArgumentosVista(args);
		controlador = new Controlador(modelo,vista);
		controlador.comenzar();
	}

	private static IVista procesarArgumentosVista(String[] args) {
		IVista vista = FactoriaVista.IUGPESTANAS.crear();
		for (String argumento : args) {
			if (argumento.equalsIgnoreCase("-vpestanas")) {
				vista = FactoriaVista.IUGPESTANAS.crear();
			} else if (argumento.equalsIgnoreCase("-vtexto")) {
				vista = FactoriaVista.TEXTO.crear();
			}
		}
		return vista;
	}
	
	private static IFuenteDatos procesarArgumentosFuenteDatos(String[] args) {
		IFuenteDatos fuenteDatos = FactoriaFuenteDatos.MONGODB.crear();
		for (String argumento : args) {
			if (argumento.equalsIgnoreCase("-fdficheros")) {
				fuenteDatos = FactoriaFuenteDatos.FICHEROS.crear();
			} else if (argumento.equalsIgnoreCase("-fdmongodb")) {
				fuenteDatos = FactoriaFuenteDatos.MONGODB.crear();
			}
		}
		return fuenteDatos;
	}
}