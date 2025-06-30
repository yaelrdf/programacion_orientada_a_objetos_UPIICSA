package com.centrocultural.controllers;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.centrocultural.MainApp;
import java.util.Optional;

public interface BaseController {
    
    // Método para recibir datos
    default void setData(Object data) {
        // Implementación por defecto vacía
    }
    
    // Método para refrescar datos
    void cargarDatos();
    
    // Métodos utilitarios comunes
    default void mostrarAlerta(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        MainApp.mostrarAlerta(titulo, encabezado, contenido, tipo);
    }
    
    default boolean confirmarAccion(String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    
    default void limpiarBusqueda(TextField campoBusqueda, TableView<?> tabla) {
        campoBusqueda.clear();
        cargarDatos();
    }
    
    default boolean validarCampoVacio(String valor, String nombreCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            mostrarAlerta("Validación", 
                         "Campo requerido", 
                         "El campo '" + nombreCampo + "' no puede estar vacío", 
                         Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }
}