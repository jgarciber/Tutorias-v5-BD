package org.iesalandalus.programacion.tutorias;

import org.iesalandalus.programacion.tutorias.mvc.controlador.Controlador;
import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.IModelo;
import org.iesalandalus.programacion.tutorias.mvc.modelo.Modelo;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.FactoriaFuenteDatos;
import org.iesalandalus.programacion.tutorias.mvc.vista.FactoriaVista;
import org.iesalandalus.programacion.tutorias.mvc.vista.IVista;

public class MainApp {

	private static IModelo modelo;
	private static IVista vista;
	private static IControlador controlador;
	
	public static void main(String[] args) {
		//System.out.println("Gestión Tutorías del IES Al-Ándalus");
		modelo = new Modelo(FactoriaFuenteDatos.FICHEROS.crear());
		vista = FactoriaVista.TEXTO.crear();
		controlador = new Controlador(modelo,vista);
		controlador.comenzar();
	}

}
