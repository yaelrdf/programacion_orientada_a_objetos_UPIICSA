package com.centrocultural.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.centrocultural.dao.ActividadDAO;
import com.centrocultural.models.Actividad;
import com.centrocultural.models.Instructor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ActividadDetallesController implements Initializable {
    
    @FXML private Label lblTitulo;
    @FXML private Label lblId;
    @FXML private Label lblNombre;
    @FXML private Label lblClasificacion;
    @FXML private Label lblAreaAsignada;
    @FXML private Label lblEstado;
    @FXML private ListView<String> listInstructores;
    @FXML private ListView<String> listGrupos;
    @FXML private Button btnEliminar;
    
    private Actividad actividad;
    private ActividadDAO actividadDAO = new ActividadDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnEliminar.setOnAction(e -> eliminarActividad());
    }
    
    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
        cargarDatos();
    }
    
    private void cargarDatos() {
        lblTitulo.setText("Detalles: " + actividad.getNombre());
        lblId.setText(String.valueOf(actividad.getIdActividad()));
        lblNombre.setText(actividad.getNombre());
        lblClasificacion.setText(actividad.getClasificacion());
        lblAreaAsignada.setText(actividad.getAreaAsignada());
        lblEstado.setText(actividad.isActiva() ? "Activa" : "Inactiva");
        
        listInstructores.getItems().setAll(
            actividad.getInstructoresAutorizados().stream()
                .map(Instructor::getNombreCompleto)
                .collect(Collectors.toList())
        );
        
        listGrupos.getItems().setAll(
            actividad.getGrupos().stream()
                .map(g -> g.getNombreGrupo() + " (" + (g.isActivo() ? "Activo" : "Inactivo") + ")")
                .collect(Collectors.toList())
        );
    }
    
    private void eliminarActividad() {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Eliminar actividad?");
        confirmacion.setContentText("Esta acción no se puede deshacer");
        
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                try {
                    if (actividadDAO.eliminar(actividad.getIdActividad())) {
                        mostrarAlerta("Éxito", "Actividad eliminada", 
                                    "La actividad ha sido eliminada", 
                                    Alert.AlertType.INFORMATION);
                        cerrar();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "Error al eliminar", 
                                "No se pudo eliminar la actividad: " + e.getMessage(), 
                                Alert.AlertType.ERROR);
                }
            }
        });
    }
    
    @FXML
    private void cerrar() {
        Stage stage = (Stage) btnEliminar.getScene().getWindow();
        stage.close();
    }
    
    private void mostrarAlerta(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}