package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvistas;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Sesion;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirSesion implements Initializable {
	
	private static final DateTimeFormatter FORMATEA_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter FORMATEA_HORA = DateTimeFormatter.ofPattern("HH:mm");
	private static final String FORMATO_HORA = "\\d{1,2}:\\d{2}";
	
	private IControlador controladorMVC;
	private ObservableList<Sesion> sesionesTutoria;
	private Tutoria tutoria;
	
    @FXML private DatePicker dpFecha;
	@FXML private TextField tfHoraInicio;
	@FXML private TextField tfHoraFin;
	@FXML private TextField tfDuracion;
	@FXML private Button btAnadir;
	@FXML private Button btCancelar;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tfHoraInicio.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(FORMATO_HORA, tfHoraInicio));
		tfHoraFin.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(FORMATO_HORA, tfHoraFin));
	}
	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
		
	
	@FXML
	private void anadirSesion() {
		Sesion sesion = null;
		try {
			sesion = getSesion();
			controladorMVC.insertar(sesion);
			sesionesTutoria.setAll(controladorMVC.getSesiones());
			Stage propietario = ((Stage) btAnadir.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Sesión", "Sesión añadida satisfactoriamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Sesión", e.getMessage());
		}	
	}
	
	@FXML
	private void cancelar() {
		((Stage) btCancelar.getScene().getWindow()).close();
	}
	
	//Este método se separa del método initialize para que una vez creada la ventana, ya no sea necesario crearla más veces
	//tan solo con llamar al método inicializa se reinician los campos sin crear un nuevo escenario, escena, etc
    public void inicializa(ObservableList<Sesion> sesionesTutoria, Tutoria tutoria) {
    	this.sesionesTutoria = sesionesTutoria;
    	this.tutoria = tutoria;
    	tfHoraInicio.setText("");
    	compruebaCampoTexto(FORMATO_HORA, tfHoraInicio);
    	tfHoraFin.setText("");
    	compruebaCampoTexto(FORMATO_HORA, tfHoraFin);
    	tfDuracion.setText("");
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
	
	private Sesion getSesion() {
		LocalDate fecha = dpFecha.getValue();
		//LocalDate fecha = LocalDate.parse(dpFecha.getValue().toString(), FORMATEA_FECHA);
		LocalTime horaInicio = LocalTime.parse(tfHoraInicio.getText(), FORMATEA_HORA);
		LocalTime horaFin = LocalTime.parse(tfHoraFin.getText(), FORMATEA_HORA);
		int Duracion = Integer.parseInt(tfDuracion.getText());
		return new Sesion(tutoria, fecha, horaInicio, horaFin, Duracion);
	}
}
