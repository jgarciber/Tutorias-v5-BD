package org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.controladoresvistas;

import java.net.URL;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.tutorias.mvc.controlador.IControlador;
import org.iesalandalus.programacion.tutorias.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.tutorias.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirProfesor implements Initializable{
	
	private static final String ER_NOMBRE =".+";
	private static final String ER_CORREO = "\\w+(?:\\.\\w+)*@\\w+\\.\\w{2,5}";
	private static final String ER_DNI = "([0-9]{8})([A-Za-z])";

	
	private IControlador controladorMVC;
	private ObservableList<Profesor> profesores;
	
	@FXML private TextField tfNombre;
	@FXML private TextField tfDni;
	@FXML private TextField tfCorreo;
	@FXML private Button btAnadir;
	@FXML private Button btCancelar;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tfNombre.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_NOMBRE, tfNombre));
		tfDni.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_DNI, tfDni));
		tfCorreo.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_CORREO, tfCorreo));
	}
	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
		
	
	@FXML
	private void anadirProfesor() {
		Profesor profesor = null;
		try {
			profesor = getProfesor();
			controladorMVC.insertar(profesor);
			profesores.setAll(controladorMVC.getProfesores());
			Stage propietario = ((Stage) btAnadir.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Profesor", "Profesor añadido satisfactoriamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Profesor", e.getMessage());
		}	
	}
	
	@FXML
	private void cancelar() {
		((Stage) btCancelar.getScene().getWindow()).close();
	}
	
	//Este método se separa del método initialize para que una vez creada la ventana, ya no sea necesario crearla más veces
	//tan solo con llamar al método inicializa se reinician los campos sin crear un nuevo escenario, escena, etc
    public void inicializa(ObservableList<Profesor> profesores) {
		this.profesores = profesores;
    	tfNombre.setText("");
    	compruebaCampoTexto(ER_NOMBRE, tfNombre);
    	tfDni.setText("");
    	compruebaCampoTexto(ER_DNI, tfDni);
    	tfCorreo.setText("");
    	compruebaCampoTexto(ER_CORREO, tfCorreo);
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
	
	private Profesor getProfesor() {
		String nombre = tfNombre.getText();
		String dni = tfDni.getText();
		String correo = tfCorreo.getText();
		return new Profesor(nombre, dni, correo);
	}

}
