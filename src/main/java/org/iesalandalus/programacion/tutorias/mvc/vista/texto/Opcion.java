package org.iesalandalus.programacion.tutorias.mvc.vista.texto;

public enum Opcion {
	INSERTAR_ALUMNO("Insertar alumno") {
		public void ejecutar() {
			vistaTexto.insertarAlumno();
		}
	},
	BUSCAR_ALUMNO("Buscar alumno") {
		public void ejecutar() {
			vistaTexto.buscarAlumno();
		}
	},
	BORRAR_ALUMNO("Borrar alumno") {
		public void ejecutar() {
			vistaTexto.borrarAlumno();
		}
	},
	LISTAR_ALUMNOS("Listar alumnos") {
		public void ejecutar() {
			vistaTexto.listarAlumnos();
		}
	},
	INSERTAR_PROFESOR("Insertar profesor") {
		public void ejecutar() {
			vistaTexto.insertarProfesor();
		}
	},
	BUSCAR_PROFESOR("Buscar profesor") {
		public void ejecutar() {
			vistaTexto.buscarProfesor();
		}
	},
	BORRAR_PROFESOR("Borrar profesor") {
		public void ejecutar() {
			vistaTexto.borrarProfesor();
		}
	},
	LISTAR_PROFESORES("Listar profesores") {
		public void ejecutar() {
			vistaTexto.listarProfesores();
		}
	},
	INSERTAR_TUTORIA("Insertar tutoria") {
		public void ejecutar() {
			vistaTexto.insertarTutoria();
		}
	},
	BUSCAR_TUTORIA("Buscar tutoria") {
		public void ejecutar() {
			vistaTexto.buscarTutoria();
		}
	},
	BORRAR_TUTORIA("Borrar tutoria") {
		public void ejecutar() {
			vistaTexto.borrarTutoria();
		}
	},
	LISTAR_TUTORIAS("Listar tutorias") {
		public void ejecutar() {
			vistaTexto.listarTutorias();
		}
	},
	LISTAR_TUTORIAS_PROFESOR("Listar tutorias de un profesor") {
		public void ejecutar() {
			vistaTexto.listarTutoriasProfesor();
		}
	},
	INSERTAR_SESION("Insertar sesión") {
		public void ejecutar() {
			vistaTexto.insertarSesion();
		}
	},
	BUSCAR_SESION("Buscar sesión") {
		public void ejecutar() {
			vistaTexto.buscarSesion();
		}
	},
	BORRAR_SESION("Borrar sesión") {
		public void ejecutar() {
			vistaTexto.borrarSesion();
		}
	},
	LISTAR_SESIONES("Listar sesiones") {
		public void ejecutar() {
			vistaTexto.listarSesiones();
		}
	},

	LISTAR_SESIONES_TUTORIA("Listar sesiones de una tutoría") {
		public void ejecutar() {
			vistaTexto.listarSesionesTutoria();
		}
	},
	INSERTAR_CITA("Insertar cita") {
		public void ejecutar() {
			vistaTexto.insertarCita();
		}
	},
	BUSCAR_CITA("Buscar cita") {
		public void ejecutar() {
			vistaTexto.buscarCita();
		}
	},
	BORRAR_CITA("Borrar cita") {
		public void ejecutar() {
			vistaTexto.borrarCita();
		}
	},
	LISTAR_CITAS("Listar citas") {
		public void ejecutar() {
			vistaTexto.listarCitas();
		}
	},

	LISTAR_CITAS_SESION("Listar citas de una sesión") {
		public void ejecutar() {
			vistaTexto.listarCitasSesion();
		}
	},
	LISTAR_CITAS_ALUMNO("Listar citas de un alumno") {
		public void ejecutar() {
			vistaTexto.listarCitasAlumno();
		}
	},
	SALIR("Salir") {
		public void ejecutar() {
			vistaTexto.terminar();
		}
	};
	
	private String mensaje;
	private static VistaTexto vistaTexto;
	
	private Opcion(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public abstract void ejecutar();
	
	protected static void setVista(VistaTexto vistaTexto) {
		Opcion.vistaTexto = vistaTexto;
	}
	
	public static Opcion getOpcionSegunOridnal(int ordinal) {
		if (esOrdinalValido(ordinal))
			return values()[ordinal];
		else
			throw new IllegalArgumentException("Ordinal de la opción no válido");
	}
	
	public static boolean esOrdinalValido(int ordinal) {
		return (ordinal >= 0 && ordinal <= values().length - 1);
	}
		
	@Override
	public String toString() {
		return String.format("%d.- %s", ordinal(), mensaje);
	}
}
