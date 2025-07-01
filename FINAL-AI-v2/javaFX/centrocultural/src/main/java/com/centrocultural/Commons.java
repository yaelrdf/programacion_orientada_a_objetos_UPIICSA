package com.centrocultural;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Commons {
    public static boolean confirmarAccion(String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        
        return alert.showAndWait().get() == ButtonType.OK;
    }
}
