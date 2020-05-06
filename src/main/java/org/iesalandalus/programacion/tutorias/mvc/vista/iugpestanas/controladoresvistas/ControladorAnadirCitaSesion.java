package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvistas;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Cita;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirCitaSesion implements Initializable {
	private static final String FORMATO_HORA = "\\d{1,2}:\\d{2}";
	private static final DateTimeFormatter FORMATEA_HORA = DateTimeFormatter.ofPattern("HH:mm");
	
	private IControlador controladorMVC;
	private ObservableList<Cita> citasSesion;
	private ObservableList<Alumno> alumnos;
	private Sesion sesion;


    @FXML private ListView<Alumno> lvAlumnos;
	@FXML private TextField tfHora;
	@FXML private Button btAnadir;
	@FXML private Button btCancelar;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tfHora.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(FORMATO_HORA, tfHora));
	}
	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	
	@FXML
	private void anadirCitaSesion() {
		Cita cita = null;
		try {
			cita = getCita();
			controladorMVC.insertar(cita);
			citasSesion.setAll(controladorMVC.getCitas());
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
	//Este método también recibe los ObservableList y los datos que va a manejar la ventana, asociando los datos pasados.
    public void inicializa(ObservableList<Cita> citasSesion, ObservableList<Alumno> alumnos, Sesion sesion) {
		this.citasSesion = citasSesion;
		this.alumnos = alumnos;
    	this.sesion = sesion;
		lvAlumnos.setItems(alumnos);
    	tfHora.setText("");
    	compruebaCampoTexto(FORMATO_HORA, tfHora);
    	lvAlumnos.getSelectionModel().clearSelection();
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
    	Alumno alumno = lvAlumnos.getSelectionModel().getSelectedItem();
		LocalTime hora = LocalTime.parse(tfHora.getText(), FORMATEA_HORA);
		return new Cita(alumno, sesion, hora);
	}
}
