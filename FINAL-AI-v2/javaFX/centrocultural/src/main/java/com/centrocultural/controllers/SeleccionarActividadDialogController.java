package com.centrocultural.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.centrocultural.models.Actividad;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class SeleccionarActividadDialogController implements Initializable {
    
    @FXML private TableView<Actividad> tablaActividades;
    
    private ObservableList<Actividad> actividadesDisponibles;
    private Actividad actividadSeleccionada;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar tabla
        tablaActividades.setItems(actividadesDisponibles);
    }
    
    public void setData(Object data) {
        if (data instanceof ObservableList) {
            this.actividadesDisponibles = (ObservableList<Actividad>) data;
        }
    }
    
    @FXML
    private void seleccionar() {
        actividadSeleccionada = tablaActividades.getSelectionModel().getSelectedItem();
        if (actividadSeleccionada != null) {
            cerrarVentana();
        }
    }
    
    @FXML
    private void cancelar() {
        actividadSeleccionada = null;
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) tablaActividades.getScene().getWindow();
        stage.close();
    }
    
    public Actividad getActividadSeleccionada() {
        return actividadSeleccionada;
    }
}