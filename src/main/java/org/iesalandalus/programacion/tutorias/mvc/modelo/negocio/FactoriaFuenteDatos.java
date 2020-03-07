package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ficheros.FactoriaFuenteDatosFicheros;

public enum FactoriaFuenteDatos {
	FICHEROS {
		@Override
		public IFuenteDatos crear() {
			// TODO Auto-generated method stub
			return new FactoriaFuenteDatosFicheros();
		}
	};
	
	public abstract IFuenteDatos crear();
	
}
