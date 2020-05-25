package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvistas;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.utilidades.Dialogos;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorVentanaPrincipal {
	
	public static final DateTimeFormatter FORMATEA_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private static final String BORRAR_PROFESOR = "Borrar Profesor";
	private static final String BORRAR_TUTORIA = "Borrar Tutoría";
	private static final String BORRAR_SESION = "Borrar Sesión";
	private static final String BORRAR_CITA = "Borrar Cita";
	private static final String BORRAR_ALUMNO = "Borrar Alumno";
	
	private IControlador controladorMVC;
	
    private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
    private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
    private ObservableList<Sesion> sesiones = FXCollections.observableArrayList();
    private ObservableList<Tutoria> tutoriasProfesor = FXCollections.observableArrayList();
    private ObservableList<Sesion> sesionesTutoria = FXCollections.observableArrayList();
    private ObservableList<Cita> citasAlumno = FXCollections.observableArrayList();
    private ObservableList<Cita> citasSesion = FXCollections.observableArrayList();

	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	//Pestaña de Profesores
    @FXML private TableView<Profesor> tvProfesores;
    @FXML private TableColumn<Profesor, String> tcNombreProfesor;
    @FXML private TableColumn<Profesor, String> tcDniProfesor;
    @FXML private TableColumn<Profesor, String> tcCorreoProfesor;
    
    @FXML private TableView<Tutoria> tvTutoriasProfesor;
    @FXML private TableColumn<Tutoria, String> tcTPNombre;

    @FXML private TableView<Sesion> tvSesionesTutoria;
    @FXML private TableColumn<Sesion, String> tcSTFecha;
    @FXML private TableColumn<Sesion, String> tcSTHoraInicio;
    @FXML private TableColumn<Sesion, String> tcSTHoraFin;
    @FXML private TableColumn<Sesion, String> tcSTDuracion;
    
    @FXML private TableView<Cita> tvCitasSesion;
    @FXML private TableColumn<Cita, String> tcCSHoraCita;
    @FXML private TableColumn<Cita, String> tcCSNombreAlumno;

    //Pestaña de Alumnos
    @FXML private TableView<Alumno> tvAlumnos;
	@FXML private TableColumn<Alumno, String> tcNombreAlumno;
    @FXML private TableColumn<Alumno, String> tcCorreoAlumno;
    @FXML private TableColumn<Alumno, String> tcExpedienteAlumno;
	
    @FXML private TableView<Cita> tvCitasAlumno;
    @FXML private TableColumn<Cita, String> tcCADiaCita;
    @FXML private TableColumn<Cita, String> tcCAHoraInicio;
    @FXML private TableColumn<Cita, String> tcCAMinutosDuracion;    
    @FXML private TableColumn<Cita, String> tcCATutoria;  
    @FXML private TableColumn<Cita, String> tcCAProfesor;
 
    
	private Stage anadirAlumno;
	private ControladorAnadirAlumno cAnadirAlumno;
	
	private Stage anadirCitaAlumno;
	private ControladorAnadirCitaAlumno cAnadirCitaAlumno;
	
	private Stage anadirCitaSesion;
	private ControladorAnadirCitaSesion cAnadirCitaSesion;
	
	private Stage anadirProfesor;
	private ControladorAnadirProfesor cAnadirProfesor;
	
	private Stage anadirSesion;
	private ControladorAnadirSesion cAnadirSesion;
	
	//private Stage anadirTutoria;
	//private ControladorAnadirTutoria cAnadirTutoria;
	
    @FXML
    private void initialize() {
    	//Pestaña de Profesores
       	
    	tcNombreProfesor.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	tcDniProfesor.setCellValueFactory(new PropertyValueFactory<>("dni"));
    	tcCorreoProfesor.setCellValueFactory(new PropertyValueFactory<>("correo"));
    	tvProfesores.setItems(profesores);
    	tvProfesores.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarTutoriasProfesor(nv));
    	
       	tcTPNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	tvTutoriasProfesor.setItems(tutoriasProfesor);
    	tvTutoriasProfesor.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarSesionesTutoria(nv));
    	
    	tcSTFecha.setCellValueFactory(sesion -> new SimpleStringProperty(FORMATEA_FECHA.format(sesion.getValue().getFecha())));
    	tcSTHoraInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
    	tcSTHoraFin.setCellValueFactory(new PropertyValueFactory<>("horaFin"));
    	tcSTDuracion.setCellValueFactory(new PropertyValueFactory<>("minutosDuracion"));
    	tvSesionesTutoria.setItems(sesionesTutoria);
    	tvSesionesTutoria.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarCitasSesion(nv));
    	
    	
    	tcCSHoraCita.setCellValueFactory(new PropertyValueFactory<>("hora"));
    	tcCSNombreAlumno.setCellValueFactory(cita -> new SimpleStringProperty(cita.getValue().getAlumno().getNombre()));
    	tvCitasSesion.setItems(citasSesion);
    	
    	
    	//Pestaña de Alumnos
    	tcNombreAlumno.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    	tcCorreoAlumno.setCellValueFactory(new PropertyValueFactory<>("correo"));
    	tcExpedienteAlumno.setCellValueFactory(new PropertyValueFactory<>("expediente"));
    	tvAlumnos.setItems(alumnos);
    	tvAlumnos.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarCitasAlumno(nv));
    	
    	tcCADiaCita.setCellValueFactory(cita -> new SimpleStringProperty(FORMATEA_FECHA.format(cita.getValue().getSesion().getFecha())));
    	tcCAHoraInicio.setCellValueFactory(new PropertyValueFactory<>("hora"));
    	tcCAMinutosDuracion.setCellValueFactory(cita -> new SimpleStringProperty(Integer.toString(cita.getValue().getSesion().getMinutosDuracion())));
    	tcCATutoria.setCellValueFactory(cita -> new SimpleStringProperty(cita.getValue().getSesion().getTutoria().getNombre()));
    	tcCAProfesor.setCellValueFactory(cita -> new SimpleStringProperty(cita.getValue().getSesion().getTutoria().getProfesor().getNombre()));
    	tvCitasAlumno.setItems(citasAlumno);
    	  	
        }
    
    @FXML
	private void salir() {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", null)) {
			controladorMVC.terminar();
			System.exit(0);
		}
	}
	
	@FXML
	private void acercaDe() throws IOException {
		VBox contenido = FXMLLoader.load(getClass().getResource("../vistas/AcercaDe.fxml"));
		Dialogos.mostrarDialogoInformacionPersonalizado("Reservas Tutorias", contenido);
	}
	
	@FXML
	private void refrescar() {
		
		//guardo los objetos seleccionados actualmente que no están sincronizados
		Profesor profesor = tvProfesores.getSelectionModel().getSelectedItem();
		Tutoria tutoria = tvTutoriasProfesor.getSelectionModel().getSelectedItem();
		Sesion sesion= tvSesionesTutoria.getSelectionModel().getSelectedItem();
 		Alumno alumno = tvAlumnos.getSelectionModel().getSelectedItem();
		
		/*
 		//elimino la selección de los objetos para que se pueda volver a seleccionar sobre el mismo objeto que estaba seleccionado
    	tvProfesores.getSelectionModel().clearSelection();
    	tvTutoriasProfesor.getSelectionModel().clearSelection();
    	tvSesionesTutoria.getSelectionModel().clearSelection();
    	tvCitasSesion.getSelectionModel().clearSelection();
    	tvAlumnos.getSelectionModel().clearSelection();
    	tvCitasAlumno.getSelectionModel().clearSelection();
		*/
 		
    	//vuelvo a sincronizar los datos
		mostrarTutoriasProfesor(profesor);
		mostrarSesionesTutoria(tutoria);
		mostrarCitasSesion(sesion);
		mostrarCitasAlumno(alumno);
	}
	
    @FXML
    void anadirProfesor(ActionEvent event) throws IOException {
    	crearAnadirProfesor();
		anadirProfesor.showAndWait();
		actualizaVentanaPrincipal();
    }

    @FXML
    void borrarProfesor(ActionEvent event) {
    	Profesor profesor = null;
		try {
			profesor = tvProfesores.getSelectionModel().getSelectedItem();
			if (profesor != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_PROFESOR, "¿Estás seguro de que quieres borrar el profesor?", null)) {
				controladorMVC.borrar(profesor);
				profesores.remove(profesor);
				actualizaVentanaPrincipal();
				Dialogos.mostrarDialogoInformacion(BORRAR_PROFESOR, "Profesor borrado satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_PROFESOR, e.getMessage());
		}
    }

    @FXML
    void anadirTutoria(ActionEvent event) throws IOException {
    	crearAnadirTutoria();
		//anadirTutoria.showAndWait();
		mostrarTutoriasProfesor(tvTutoriasProfesor.getSelectionModel().getSelectedItem().getProfesor());
    }

    @FXML
    void borrarTutoria(ActionEvent event) {
    	Tutoria tutoria = null;
		try {
			tutoria = tvTutoriasProfesor.getSelectionModel().getSelectedItem();
			if (tutoria != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_TUTORIA, "¿Estás seguro de que quieres borrar la tutoría?", null)) {
				controladorMVC.borrar(tutoria);
				tutoriasProfesor.remove(tutoria);
				mostrarTutoriasProfesor(tutoria.getProfesor());
				Dialogos.mostrarDialogoInformacion(BORRAR_TUTORIA, "Tutoría borrada satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_TUTORIA, e.getMessage());
		}
    }
    
    @FXML
    void anadirSesion(ActionEvent event) throws IOException {
    	crearAnadirSesion();
		anadirSesion.showAndWait();
		mostrarSesionesTutoria(tvSesionesTutoria.getSelectionModel().getSelectedItem().getTutoria());
    }

    @FXML
    void borrarSesion(ActionEvent event) {
    	Sesion sesion = null;
		try {
			sesion = tvSesionesTutoria.getSelectionModel().getSelectedItem();
			if (sesion != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_SESION, "¿Estás seguro de que quieres borrar el sesion?", null)) {
				controladorMVC.borrar(sesion);
				sesionesTutoria.remove(sesion);
				mostrarSesionesTutoria(sesion.getTutoria());
				Dialogos.mostrarDialogoInformacion(BORRAR_SESION, "Sesión borrada satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_SESION, e.getMessage());
		}
    }
    
    @FXML
    void anadirCitaSesion(ActionEvent event) throws IOException {
    	crearAnadirCitaSesion();
		anadirCitaSesion.showAndWait();
		mostrarCitasSesion(tvCitasSesion.getSelectionModel().getSelectedItem().getSesion());
	}


    @FXML
    void borrarCitaSesion(ActionEvent event) {
    	Cita cita = null;
		try {
			cita = tvCitasSesion.getSelectionModel().getSelectedItem();
			if (cita != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_CITA, "¿Estás seguro de que quieres borrar la cita?", null)) {
				controladorMVC.borrar(cita);
				citasSesion.remove(cita);
				mostrarCitasSesion(cita.getSesion());
				Dialogos.mostrarDialogoInformacion(BORRAR_CITA, "Cita borrada satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_CITA, e.getMessage());
		}
    }
    
    @FXML
    void anadirAlumno(ActionEvent event) throws IOException {
    	crearAnadirAlumno();
		anadirAlumno.showAndWait();
		actualizaVentanaPrincipal();
    }

    
    @FXML
    void borrarAlumno(ActionEvent event) {
    	Alumno alumno = null;
		try {
			alumno = tvAlumnos.getSelectionModel().getSelectedItem();
			if (alumno != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_ALUMNO, "¿Estás seguro de que quieres borrar el alumno?", null)) {
				controladorMVC.borrar(alumno);
				alumnos.remove(alumno);
				actualizaVentanaPrincipal();
				Dialogos.mostrarDialogoInformacion(BORRAR_ALUMNO, "Alumno borrado satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_ALUMNO, e.getMessage());
		}
    }
    
    @FXML
    void anadirCitaAlumno(ActionEvent event) throws IOException {
    	crearAnadirCitaAlumno();
		anadirCitaAlumno.showAndWait();
		mostrarCitasAlumno(tvCitasAlumno.getSelectionModel().getSelectedItem().getAlumno());
    }

    @FXML
    void borrarCitaAlumno(ActionEvent event) {
    	Cita cita = null;
		try {
			cita = tvCitasAlumno.getSelectionModel().getSelectedItem();
			if (cita != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_CITA, "¿Estás seguro de que quieres borrar la cita?", null)) {
				controladorMVC.borrar(cita);
				citasAlumno.remove(cita);
				mostrarCitasAlumno(cita.getAlumno());
				Dialogos.mostrarDialogoInformacion(BORRAR_CITA, "Cita borrada satisfactoriamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_CITA, e.getMessage());
		}
    }
    
    public void actualizaVentanaPrincipal() {
    	profesores.setAll(controladorMVC.getProfesores());
    	alumnos.setAll(controladorMVC.getAlumnos());
    	tutoriasProfesor.setAll(FXCollections.observableArrayList());
		sesionesTutoria.setAll(FXCollections.observableArrayList());
		citasSesion.setAll(FXCollections.observableArrayList());
		citasAlumno.setAll(FXCollections.observableArrayList());
    }
    
    
    //Los métodos mostrar... se utilizan para sincronizar tablas logrando una vista-detalle
        
    public void mostrarTutoriasProfesor(Profesor profesor) {
    	try {
    		if (profesor != null) {
    			tutoriasProfesor.setAll(controladorMVC.getTutorias(profesor));
    		}
    		sesionesTutoria.setAll(FXCollections.observableArrayList());
			citasSesion.setAll(FXCollections.observableArrayList());
		} catch (IllegalArgumentException e) {
			tutoriasProfesor.setAll(FXCollections.observableArrayList());
			
			sesionesTutoria.setAll(FXCollections.observableArrayList());
			citasSesion.setAll(FXCollections.observableArrayList());
		}
    }
    
    public void mostrarSesionesTutoria(Tutoria tutoria) {
    	try {
    		if (tutoria != null) {
    			sesionesTutoria.setAll(controladorMVC.getSesiones(tutoria));
       		}
			citasSesion.setAll(FXCollections.observableArrayList());
		} catch (IllegalArgumentException e) {
			sesionesTutoria.setAll(FXCollections.observableArrayList());
			
			citasSesion.setAll(FXCollections.observableArrayList());
		}
    }
    
    public void mostrarCitasSesion(Sesion sesion) {
    	try {
    		if (sesion != null) {
    			citasSesion.setAll(controladorMVC.getCitas(sesion));
    		}
		} catch (IllegalArgumentException e) {
			citasSesion.setAll(FXCollections.observableArrayList());
		}
    }
    
    public void mostrarCitasAlumno(Alumno alumno) {
    	try {
    		if (alumno != null) {
    			citasAlumno.setAll(controladorMVC.getCitas(alumno));
    		}
		} catch (IllegalArgumentException e) {
			citasAlumno.setAll(FXCollections.observableArrayList());
		}
    }
    
	
	private void crearAnadirProfesor() throws IOException {
		if (anadirProfesor == null) {
			anadirProfesor = new Stage();
			FXMLLoader cargadorAnadirProfesor = new FXMLLoader(
						getClass().getResource("../vistas/AnadirProfesor.fxml"));
			VBox raizAnadirProfesor = cargadorAnadirProfesor.load();
			cAnadirProfesor = cargadorAnadirProfesor.getController();
			cAnadirProfesor.setControladorMVC(controladorMVC);
			Scene escenaAnadirProfesor = new Scene(raizAnadirProfesor);
			anadirProfesor.setTitle("Añadir Profesor");
			anadirProfesor.initModality(Modality.APPLICATION_MODAL); 
			anadirProfesor.setScene(escenaAnadirProfesor);
			cAnadirProfesor.inicializa(profesores);
		} else {
			cAnadirProfesor.inicializa(profesores);
		}
	}
	
	private void crearAnadirAlumno() throws IOException {
		if (anadirAlumno == null) {
			anadirAlumno = new Stage();
			FXMLLoader cargadorAnadirAlumno = new FXMLLoader(
						getClass().getResource("../vistas/AnadirAlumno.fxml"));
			VBox raizAnadirAlumno = cargadorAnadirAlumno.load();
			cAnadirAlumno = cargadorAnadirAlumno.getController();
			cAnadirAlumno.setControladorMVC(controladorMVC);
			Scene escenaAnadirAlumno = new Scene(raizAnadirAlumno);
			anadirAlumno.setTitle("Añadir Alumno");
			anadirAlumno.initModality(Modality.APPLICATION_MODAL); 
			anadirAlumno.setScene(escenaAnadirAlumno);
			cAnadirAlumno.inicializa(alumnos);
		} else {
			cAnadirAlumno.inicializa(alumnos);
		}
	}
	
	private void crearAnadirCitaAlumno() throws IOException {
		Alumno alumno = tvAlumnos.getSelectionModel().getSelectedItem();
		if (anadirCitaAlumno == null) {
			anadirCitaAlumno = new Stage();
			FXMLLoader cargadorAnadirCitaAlumno = new FXMLLoader(
						getClass().getResource("../vistas/AnadirCitaAlumno.fxml"));
			VBox raizAnadirCitaAlumno = cargadorAnadirCitaAlumno.load();
			cAnadirCitaAlumno = cargadorAnadirCitaAlumno.getController();
			cAnadirCitaAlumno.setControladorMVC(controladorMVC);
			Scene escenaAnadirCitaAlumno = new Scene(raizAnadirCitaAlumno);
			anadirCitaAlumno.setTitle("Añadir Cita");
			anadirCitaAlumno.initModality(Modality.APPLICATION_MODAL); 
			anadirCitaAlumno.setScene(escenaAnadirCitaAlumno);
			
			sesiones.setAll(controladorMVC.getSesiones());
			cAnadirCitaAlumno.inicializa(sesiones,alumno);
		} else {
			sesiones.setAll(controladorMVC.getSesiones());
			cAnadirCitaAlumno.inicializa(sesiones,alumno);
		}
	}
	
	private void crearAnadirCitaSesion() throws IOException {
		Sesion sesion= tvSesionesTutoria.getSelectionModel().getSelectedItem();
		if (anadirCitaSesion == null) {
			anadirCitaSesion = new Stage();
			FXMLLoader cargadorAnadirCitaSesion = new FXMLLoader(
						getClass().getResource("../vistas/AnadirCitaSesion.fxml"));
			VBox raizAnadirCitaSesion = cargadorAnadirCitaSesion.load();
			cAnadirCitaSesion = cargadorAnadirCitaSesion.getController();
			cAnadirCitaSesion.setControladorMVC(controladorMVC);
			Scene escenaAnadirCitaSesion = new Scene(raizAnadirCitaSesion);
			anadirCitaSesion.setTitle("Añadir Cita");
			anadirCitaSesion.initModality(Modality.APPLICATION_MODAL); 
			anadirCitaSesion.setScene(escenaAnadirCitaSesion);
			cAnadirCitaSesion.inicializa(alumnos,sesion);
		} else {
			cAnadirCitaSesion.inicializa(alumnos,sesion);
		}
	}
	
	private void crearAnadirSesion() throws IOException {
		Tutoria tutoria = tvTutoriasProfesor.getSelectionModel().getSelectedItem();
		if (anadirSesion == null) {
			anadirSesion = new Stage();
			FXMLLoader cargadorAnadirSesion = new FXMLLoader(
						getClass().getResource("../vistas/AnadirSesion.fxml"));
			VBox raizAnadirSesion = cargadorAnadirSesion.load();
			cAnadirSesion = cargadorAnadirSesion.getController();
			cAnadirSesion.setControladorMVC(controladorMVC);
			Scene escenaAnadirSesion = new Scene(raizAnadirSesion);
			anadirSesion.setTitle("Añadir Sesion");
			anadirSesion.initModality(Modality.APPLICATION_MODAL); 
			anadirSesion.setScene(escenaAnadirSesion);
			cAnadirSesion.inicializa(tutoria);
		} else {
			cAnadirSesion.inicializa(tutoria);
		}
	}
	
	private void crearAnadirTutoria() throws IOException {
		Profesor profesor = tvProfesores.getSelectionModel().getSelectedItem();
		String nombreTutoria = Dialogos.mostrarDialogoTexto("Añadir tutoría", "Tutoría:");		
		try{
			Tutoria tutoria = new Tutoria (profesor, nombreTutoria);
			controladorMVC.insertar(tutoria);
			Dialogos.mostrarDialogoInformacion("Añadir Tutoría", "Tutoría añadida satisfactoriamente");
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Tutoría", e.getMessage());
		}	
		/*
		if (anadirTutoria == null) {
			anadirTutoria = new Stage();
			FXMLLoader cargadorAnadirTutoria = new FXMLLoader(
						getClass().getResource("../vistas/AnadirTutoria.fxml"));
			VBox raizAnadirTutoria = cargadorAnadirTutoria.load();
			cAnadirTutoria = cargadorAnadirTutoria.getController();
			cAnadirTutoria.setControladorMVC(controladorMVC);
			Scene escenaAnadirTutoria = new Scene(raizAnadirTutoria);
			anadirTutoria.setTitle("Añadir Tutoría");
			anadirTutoria.initModality(Modality.APPLICATION_MODAL); 
			anadirTutoria.setScene(escenaAnadirTutoria);
			cAnadirTutoria.inicializa(tutoriasProfesor,profesor);
		} else {
			cAnadirTutoria.inicializa(tutoriasProfesor,profesor);
		}
		*/
	}	
	
    @FXML
    void buscarProfesor(ActionEvent event) throws IOException {
		String dniProfesor = Dialogos.mostrarDialogoTexto("Buscar Profesor", "DNI del Profesor:");		
		try{
			Profesor profesor = controladorMVC.buscar(Profesor.getProfesorFicticio(dniProfesor));
			String mensaje = (profesor != null) ? profesor.toString() : "No existe dicho profesor.";
			Dialogos.mostrarDialogoInformacion("Resultado Búsqueda Profesor", mensaje);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Buscar Profesor", e.getMessage());
		}
    }
    @FXML
    void buscarAlumno(ActionEvent event) throws IOException {
		String correoAlumno = Dialogos.mostrarDialogoTexto("Buscar Alumno", "Correo del Alumno:");		
		try{
			Alumno alumno = controladorMVC.buscar(Alumno.getAlumnoFicticio(correoAlumno));
			String mensaje = (alumno != null) ? alumno.toString() : "No existe dicho alumno.";
			Dialogos.mostrarDialogoInformacion("Resultado Búsqueda Alumno", mensaje);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Buscar Alumno", e.getMessage());
		}
    }
    
    /*
    @FXML
    void buscarTutoria(ActionEvent event) throws IOException {
		String correoTutoria = Dialogos.mostrarDialogoTexto("Buscar Tutoría", "Correo del Tutoria:");		
		try{
			Tutoria tutoria = controladorMVC.buscar(Tutoria.getTutoriaFicticio(correoTutoria));
			String mensaje = (tutoria != null) ? tutoria.toString() : "No existe dicho tutoria.";
			Dialogos.mostrarDialogoInformacion("Resultado Búsqueda Tutoria", mensaje);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Buscar Tutoria", e.getMessage());
		}
    }
    @FXML
    void buscarSesion(ActionEvent event) throws IOException {
		String correoSesion = Dialogos.mostrarDialogoTexto("Buscar Sesion", "Correo del Sesion:");		
		try{
			Sesion sesion = controladorMVC.buscar(Sesion.getSesionFicticio(correoSesion));
			String mensaje = (sesion != null) ? sesion.toString() : "No existe dicho sesion.";
			Dialogos.mostrarDialogoInformacion("Resultado Búsqueda Sesion", mensaje);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Buscar Sesion", e.getMessage());
		}
    }
    @FXML
    void buscarCita(ActionEvent event) throws IOException {
		String correoCita = Dialogos.mostrarDialogoTexto("Buscar Cita", "Correo del Cita:");		
		try{
			Cita cita = controladorMVC.buscar(Cita.getCitaFicticio(correoCita));
			String mensaje = (cita != null) ? cita.toString() : "No existe dicho cita.";
			Dialogos.mostrarDialogoInformacion("Resultado Búsqueda Cita", mensaje);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Buscar Cita", e.getMessage());
		}
    }
    */
}
