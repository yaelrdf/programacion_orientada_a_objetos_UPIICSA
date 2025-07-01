package com.centrocultural.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import com.centrocultural.models.Actividad;

public class SeleccionarActividadDialogController implements Initializable {
    
    @FXML private ListView<Actividad> lstActividadesDisponibles;
    
    private ObservableList<Actividad> actividadesDisponibles;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configuración inicial
    }
    
    public void setActividadesDisponibles(ObservableList<Actividad> actividades) {
        this.actividadesDisponibles = actividades;
        lstActividadesDisponibles.setItems(actividadesDisponibles);
    }
    
    public Actividad getActividadSeleccionada() {
        return lstActividadesDisponibles.getSelectionModel().getSelectedItem();
    }
}