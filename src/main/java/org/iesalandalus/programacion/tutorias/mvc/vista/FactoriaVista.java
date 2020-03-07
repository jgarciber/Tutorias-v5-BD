package org.iesalandalus.programacion.tutorias.mvc.vista;

import org.iesalandalus.programacion.tutorias.mvc.vista.texto.VistaTexto;

public enum FactoriaVista {
	TEXTO{
		@Override
		public IVista crear() {
			// TODO Auto-generated method stub
			return new VistaTexto();
		}
	};
	
	public abstract IVista crear();
}
