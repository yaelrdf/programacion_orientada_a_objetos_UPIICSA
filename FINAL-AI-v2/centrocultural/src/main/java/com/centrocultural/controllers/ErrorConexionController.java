package com.centrocultural.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import com.centrocultural.MainApp;
import com.centrocultural.database.DatabaseConnection;
import java.net.URL;
import java.util.ResourceBundle;

public class ErrorConexionController implements Initializable {
    
    @FXML private Label lblMensaje;
    @FXML private Label lblIntento;
    @FXML private ProgressIndicator progressIndicator;
    
    private Timeline timeline;
    private int segundosRestantes = 10;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarReintento();
    }
    
    private void iniciarReintento() {
        lblIntento.setText("Reintentando en " + segundosRestantes + " segundos...");
        
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            segundosRestantes--;
            
            if (segundosRestantes > 0) {
                lblIntento.setText("Reintentando en " + segundosRestantes + " segundos...");
            } else {
                intentarReconexion();
            }
        }));
        
        timeline.setCycleCount(10);
        timeline.play();
    }
    
    private void intentarReconexion() {
        lblIntento.setText("Intentando conectar...");
        progressIndicator.setVisible(true);
        
        // Intentar reconectar en un hilo separado
        javafx.concurrent.Task<Boolean> task = new javafx.concurrent.Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                DatabaseConnection db = DatabaseConnection.getInstance();
                return db.reconnect();
            }
        };
        
        task.setOnSucceeded(e -> {
            if (task.getValue()) {
                // Conexión exitosa
                MainApp.getInstance().mostrarLogin();
            } else {
                // Fallo en la conexión, reiniciar contador
                segundosRestantes = 10;
                progressIndicator.setVisible(false);
                iniciarReintento();
            }
        });
        
        task.setOnFailed(e -> {
            // Error al intentar conectar
            segundosRestantes = 10;
            progressIndicator.setVisible(false);
            iniciarReintento();
        });
        
        new Thread(task).start();
    }
    
    @FXML
    private void reintentar() {
        if (timeline != null) {
            timeline.stop();
        }
        segundosRestantes = 0;
        intentarReconexion();
    }
}