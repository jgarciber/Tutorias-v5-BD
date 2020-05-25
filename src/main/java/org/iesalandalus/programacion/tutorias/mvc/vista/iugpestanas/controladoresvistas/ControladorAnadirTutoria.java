package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvistas;

import java.net.URL;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Tutoria;
import org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

class ControladorAnadirTutoria implements Initializable{
	
	private static final String ER_NOMBRE ="[a-zA-ZáéíóúüÁÉÍÓÓÜ]+";

	private IControlador controladorMVC;
	private Profesor profesor;
	
	@FXML private TextField tfNombreTutoria;
	@FXML private Button btAnadir;
	@FXML private Button btCancelar;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tfNombreTutoria.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_NOMBRE, tfNombreTutoria));
	}
	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
		
	
	@FXML
	private void anadirTutoria() {
		Tutoria tutoria = null;
		try {
			tutoria = getTutoria();
			controladorMVC.insertar(tutoria);
			Stage propietario = ((Stage) btAnadir.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Tutoría", "Tutoría añadida satisfactoriamente", propietario);
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
    public void inicializa(Profesor profesor) {
    	this.profesor = profesor;
    	tfNombreTutoria.setText("");
    	compruebaCampoTexto(ER_NOMBRE, tfNombreTutoria);
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
	
	private Tutoria getTutoria() {
		String nombreTutoria = tfNombreTutoria.getText();
		return new Tutoria(profesor, nombreTutoria);
	}
}
