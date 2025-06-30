package com.centrocultural.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.centrocultural.dao.MaterialDAO;
import com.centrocultural.models.Material;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MaterialesFormController implements Initializable, BaseController {
    
    @FXML private Label lblTitulo;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<String> cmbAreaAlmacenamiento;
    @FXML private ComboBox<String> cmbCondicion;
    @FXML private CheckBox chkDisponible;
    @FXML private Button btnGuardar;
    
    private MaterialDAO materialDAO = new MaterialDAO();
    private Material materialEditar = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarComboBoxes();
    }
    
    private void configurarComboBoxes() {
        // Áreas de almacenamiento basadas en las áreas de grupos
        cmbAreaAlmacenamiento.getItems().addAll(
            "deportiva",
            "cultural",
            "oficios",
            "almacen_general"
        );
        
        // Condiciones posibles para los materiales
        cmbCondicion.getItems().addAll(
            "bueno",
            "regular",
            "malo"
        );
    }
    
    @Override
    public void setData(Object data) {
        if (data instanceof Material) {
            this.materialEditar = (Material) data;
            cargarDatosMaterial();
        }
    }
    
    private void cargarDatosMaterial() {
        lblTitulo.setText("Editar Material");
        txtNombre.setText(materialEditar.getNombre());
        cmbAreaAlmacenamiento.setValue(materialEditar.getAreaAlmacenamiento());
        cmbCondicion.setValue(materialEditar.getCondicion());
        chkDisponible.setSelected(materialEditar.isDisponible());
    }
    
    @FXML
    private void guardar() {
        if (!validarFormulario()) {
            return;
        }
        
        try {
            String nombre = txtNombre.getText().trim();
            String areaAlmacenamiento = cmbAreaAlmacenamiento.getValue();
            String condicion = cmbCondicion.getValue();
            boolean disponible = chkDisponible.isSelected();
            
            if (materialEditar == null) {
                // Crear nuevo material
                Material nuevoMaterial = new Material(
                    nombre,
                    areaAlmacenamiento,
                    condicion
                );
                nuevoMaterial.setDisponible(disponible);
                
                int id = materialDAO.crear(nuevoMaterial);
                if (id > 0) {
                    mostrarAlerta("Éxito", "Material creado", 
                                "El material ha sido registrado exitosamente", 
                                Alert.AlertType.INFORMATION);
                    cerrarVentana();
                }
            } else {
                // Actualizar material existente
                materialEditar.setNombre(nombre);
                materialEditar.setAreaAlmacenamiento(areaAlmacenamiento);
                materialEditar.setCondicion(condicion);
                materialEditar.setDisponible(disponible);
                
                if (materialDAO.actualizar(materialEditar)) {
                    mostrarAlerta("Éxito", "Material actualizado", 
                                "Los datos del material han sido actualizados", 
                                Alert.AlertType.INFORMATION);
                    cerrarVentana();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al guardar", 
                        "No se pudo guardar el material: " + e.getMessage(), 
                        Alert.AlertType.ERROR);
        }
    }
    
    private boolean validarFormulario() {
        String nombre = txtNombre.getText().trim();
        String areaAlmacenamiento = cmbAreaAlmacenamiento.getValue();
        String condicion = cmbCondicion.getValue();
        
        if (nombre.isEmpty()) {
            mostrarAlerta("Validación", "Campo requerido", 
                         "El nombre del material es obligatorio", 
                         Alert.AlertType.WARNING);
            return false;
        }
        
        if (areaAlmacenamiento == null) {
            mostrarAlerta("Validación", "Campo requerido", 
                         "Debe seleccionar un área de almacenamiento", 
                         Alert.AlertType.WARNING);
            return false;
        }
        
        if (condicion == null) {
            mostrarAlerta("Validación", "Campo requerido", 
                         "Debe seleccionar una condición", 
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