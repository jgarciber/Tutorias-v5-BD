package org.iesalandalus.programacion.tutorias.mvc.modelo.negocio;

import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.ficheros.FactoriaFuenteDatosFicheros;
import org.iesalandalus.programacion.tutorias.mvc.modelo.negocio.mongodb.FactoriaFuenteDatosMongoDB;

public enum FactoriaFuenteDatos {
	FICHEROS {
		@Override
		public IFuenteDatos crear() {
			// TODO Auto-generated method stub
			return new FactoriaFuenteDatosFicheros();
		}
	},
	MONGODB {
		@Override
		public IFuenteDatos crear() {
			// TODO Auto-generated method stub
			return new FactoriaFuenteDatosMongoDB();
		}
	};
	
	
	public abstract IFuenteDatos crear();
	
}
