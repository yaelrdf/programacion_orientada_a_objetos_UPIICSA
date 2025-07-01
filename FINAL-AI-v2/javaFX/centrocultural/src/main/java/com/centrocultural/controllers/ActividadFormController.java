package com.centrocultural.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.centrocultural.dao.ActividadDAO;
import com.centrocultural.models.Actividad;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ActividadFormController implements Initializable, BaseController {
    
    @FXML private TextField txtAreaAsignada;
    @FXML private Label lblTitulo;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<String> cmbClasificacion;
    @FXML private CheckBox chkActiva;
    @FXML private Button btnGuardar;
    
    private ActividadDAO actividadDAO = new ActividadDAO();
    private Actividad actividadEditar = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar opciones de clasificación
        cmbClasificacion.getItems().addAll(
            "deportiva",
            "cultural",
            "oficio"
        );
    }
    
    @Override
    public void setData(Object data) {
        if (data instanceof Actividad) {
            this.actividadEditar = (Actividad) data;
            cargarDatosActividad();
        }
    }
    
    private void cargarDatosActividad() {
        lblTitulo.setText("Editar Actividad");
        txtNombre.setText(actividadEditar.getNombre());
        cmbClasificacion.setValue(actividadEditar.getClasificacion());
        txtAreaAsignada.setText(actividadEditar.getAreaAsignada());
        chkActiva.setSelected(actividadEditar.isActiva());
    }
    
    @FXML
    private void guardar() {
        if (!validarFormulario()) {
            return;
        }
        
        try {
            String nombre = txtNombre.getText().trim();
            String clasificacion = cmbClasificacion.getValue();
            String areaAsignada = txtAreaAsignada.getText().trim();
            boolean activa = chkActiva.isSelected();
            
            if (actividadEditar == null) {
                // Crear nueva actividad
                Actividad nuevaActividad = new Actividad(nombre, clasificacion, areaAsignada);
                nuevaActividad.setActiva(activa);
                actividadDAO.crear(nuevaActividad);
                mostrarAlerta("Éxito", "Actividad creada", "La actividad se ha creado correctamente", Alert.AlertType.INFORMATION);
            } else {
                // Actualizar actividad existente
                actividadEditar.setNombre(nombre);
                actividadEditar.setClasificacion(clasificacion);
                actividadEditar.setAreaAsignada(areaAsignada);
                actividadEditar.setActiva(activa);
                actividadDAO.actualizar(actividadEditar);
                mostrarAlerta("Éxito", "Actividad actualizada", "La actividad se ha actualizado correctamente", Alert.AlertType.INFORMATION);
            }
            
            cerrarVentana();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error en la base de datos", 
                         "Ocurrió un error al guardar la actividad: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }

    private boolean validarFormulario() {
        String nombre = txtNombre.getText().trim();
        String clasificacion = cmbClasificacion.getValue();
        String areaAsignada = txtAreaAsignada.getText().trim();
        
        if (nombre.isEmpty()) {
            mostrarAlerta("Validación", "Campo requerido", 
                         "El nombre de la actividad es obligatorio", 
                         Alert.AlertType.WARNING);
            return false;
        }
        
        if (clasificacion == null) {
            mostrarAlerta("Validación", "Campo requerido", 
                         "Debe seleccionar una clasificación", 
                         Alert.AlertType.WARNING);
            return false;
        }
        
        if (areaAsignada.isEmpty()) {
            mostrarAlerta("Validación", "Campo requerido", 
                         "Debe especificar un área asignada", 
                         Alert.AlertType.WARNING);
            return false;
        }
        
        return true;
    }
    
    @FXML
    private void cancelar() {
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void cargarDatos() {
        // No se usa en este formulario
    }
    
    public void mostrarAlerta(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}