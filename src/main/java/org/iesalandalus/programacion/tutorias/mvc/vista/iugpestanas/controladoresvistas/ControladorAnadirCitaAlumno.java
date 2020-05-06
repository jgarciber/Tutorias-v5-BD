package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvistas;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControladorAnadirCitaAlumno implements Initializable{
	
	public static final DateTimeFormatter FORMATEA_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String FORMATO_HORA = "\\d{1,2}:\\d{2}";
	private static final DateTimeFormatter FORMATEA_HORA = DateTimeFormatter.ofPattern("HH:mm");
	
	private IControlador controladorMVC;
	private ObservableList<Cita> citasAlumno;
	private Alumno alumno;

    @FXML private TableView<Sesion> tvSesiones;
    @FXML private TableColumn<Sesion, String> tcSFecha;
    @FXML private TableColumn<Sesion, String> tcSHoraInicio;
    @FXML private TableColumn<Sesion, String> tcSHoraFin;
    @FXML private TableColumn<Sesion, String> tcSDuracion;
    @FXML private TableColumn<Sesion, String> tcSTutoria;
    @FXML private TableColumn<Sesion, String> tcSProfesor;

	@FXML private TextField tfHora;
	@FXML private Button btAnadir;
	@FXML private Button btCancelar;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcSFecha.setCellValueFactory(sesion -> new SimpleStringProperty(FORMATEA_FECHA.format(sesion.getValue().getFecha())));
    	tcSHoraInicio.setCellValueFactory(new PropertyValueFactory<>("horaInicio"));
    	tcSHoraFin.setCellValueFactory(new PropertyValueFactory<>("horaFin"));
    	tcSDuracion.setCellValueFactory(new PropertyValueFactory<>("minutosDuracion"));
    	tcSTutoria.setCellValueFactory(sesion -> new SimpleStringProperty(sesion.getValue().getTutoria().getNombre()));
    	tcSProfesor.setCellValueFactory(sesion -> new SimpleStringProperty(sesion.getValue().getTutoria().getProfesor().getNombre()));
		tfHora.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(FORMATO_HORA, tfHora));
	}
	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

	
	@FXML
	private void anadirCitaAlumno() {
		Cita cita = null;
		try {
			cita = getCita();
			controladorMVC.insertar(cita);
			citasAlumno.setAll(controladorMVC.getCitas());
			Stage propietario = ((Stage) btAnadir.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Cita", "Cita añadida satisfactoriamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Cita", e.getMessage());
		}	
	}
	
	@FXML
	private void cancelar() {
		((Stage) btCancelar.getScene().getWindow()).close();
	}
	
	//Este método se separa del método initialize para que una vez creada la ventana, ya no sea necesario crearla más veces
	//tan solo con llamar al método inicializa se reinician los campos sin crear un nuevo escenario, escena, etc
    public void inicializa(ObservableList<Sesion> sesiones, ObservableList<Cita> citasAlumno, Alumno alumno) {
		this.citasAlumno = citasAlumno;
    	this.alumno = alumno;
    	tfHora.setText("");
    	compruebaCampoTexto(FORMATO_HORA, tfHora);
    	tvSesiones.setItems(sesiones);
    	tvSesiones.getSelectionModel().clearSelection();
    }
	
	private void compruebaCampoTexto(String er, TextField campoTexto) {	
		String texto = campoTexto.getText();
		if (texto.matches(er)) {
			campoTexto.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
		}
		else {
			campoTexto.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
		}
	}
	
	private Cita getCita() {
    	Sesion sesion = tvSesiones.getSelectionModel().getSelectedItem();
		LocalTime hora = LocalTime.parse(tfHora.getText(), FORMATEA_HORA);

		return new Cita(alumno, sesion, hora);
	}
	
	}
//return String.format("fecha=" + fecha.format(FORMATO_FECHA) + ", horaInicio=" + horaInicio.format(FORMATO_HORA) + ", horaFin=" + horaFin.format(FORMATO_HORA) + ", minutosDuracion="+ minutosDuracion);
