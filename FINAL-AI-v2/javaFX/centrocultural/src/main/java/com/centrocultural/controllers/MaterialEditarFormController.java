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

public class MaterialEditarFormController implements Initializable, BaseController {
    
    @FXML private Label lblTitulo;
    @FXML private Label lblId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtAreaAlmacenamiento;
    @FXML private ComboBox<String> cmbCondicion;
    @FXML private CheckBox chkDisponible;
    @FXML private Button btnGuardar;
    
    private MaterialDAO materialDAO = new MaterialDAO();
    private Material materialEditar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarComboBox();
    }
    
    private void configurarComboBox() {
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
        lblId.setText(String.valueOf(materialEditar.getIdMaterial()));
        txtNombre.setText(materialEditar.getNombre());
        txtAreaAlmacenamiento.setText(materialEditar.getAreaAlmacenamiento());
        cmbCondicion.setValue(materialEditar.getCondicion());
        chkDisponible.setSelected(materialEditar.isDisponible());
    }
    
    @FXML
    private void guardar() {
        if (!validarFormulario()) {
            return;
        }
        
        try {
            materialEditar.setNombre(txtNombre.getText().trim());
            materialEditar.setAreaAlmacenamiento(txtAreaAlmacenamiento.getText().trim());
            materialEditar.setCondicion(cmbCondicion.getValue());
            materialEditar.setDisponible(chkDisponible.isSelected());
            
            if (materialDAO.actualizar(materialEditar)) {
                mostrarAlerta("Éxito", "Material actualizado", 
                            "Los cambios se han guardado correctamente", 
                            Alert.AlertType.INFORMATION);
                cerrarVentana(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al guardar", 
                         "No se pudieron guardar los cambios: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }
    
    private boolean validarFormulario() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarAlerta("Validación", "Campo requerido", 
                         "El nombre del material es obligatorio", 
                         Alert.AlertType.WARNING);
            return false;
        }
        
        if (txtAreaAlmacenamiento.getText().trim().isEmpty()) {
            mostrarAlerta("Validación", "Campo requerido", 
                         "El área de almacenamiento es obligatoria", 
                         Alert.AlertType.WARNING);
            return false;
        }
        
        if (cmbCondicion.getValue() == null) {
            mostrarAlerta("Validación", "Campo requerido", 
                         "Debe seleccionar una condición", 
                         Alert.AlertType.WARNING);
            return false;
        }
        
        return true;
    }
    
    @FXML
    private void cancelar() {
        cerrarVentana(false);
    }
    
    private void cerrarVentana(boolean guardado) {
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.setUserData(guardado);
        stage.close();
    }
    
    public void mostrarAlerta(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @Override
    public void cargarDatos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cargarDatos'");
    }
}