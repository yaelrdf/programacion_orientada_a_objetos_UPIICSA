package com.centrocultural;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.centrocultural.database.DatabaseConnection;
import com.centrocultural.models.Usuario;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;

public class MainApp extends Application {
    
    private Stage primaryStage;
    private Usuario usuarioActual;
    private static MainApp instance;
    
    public MainApp() {
        instance = this;
    }
    
    public static MainApp getInstance() {
        return instance;
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Centro Cultural - Sistema de Gestión");
        
        // Intentar establecer ícono si existe
        try {
            Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
            this.primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            // Si no hay ícono, continuar sin él
        }
        
        // Verificar conexión a base de datos
        if (verificarConexionBD()) {
            mostrarLogin();
        } else {
            mostrarErrorConexion();
        }
    }
    
    private boolean verificarConexionBD() {
        DatabaseConnection db = DatabaseConnection.getInstance();
        return db.testConnection();
    }
    
    private void mostrarErrorConexion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ErrorConexion.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la interfaz", 
                        "Error al cargar la pantalla de error de conexión: " + e.getMessage(), 
                        AlertType.ERROR);
        }
    }
    
    public void mostrarLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la interfaz", 
                        "Error al cargar la pantalla de login: " + e.getMessage(), 
                        AlertType.ERROR);
        }
    }
    
    public void mostrarPantallaPrincipal(Usuario usuario) {
        this.usuarioActual = usuario;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PantallaPrincipal.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la interfaz", 
                        "Error al cargar la pantalla principal: " + e.getMessage(), 
                        AlertType.ERROR);
        }
    }
    
    public void cerrarSesion() {
        usuarioActual = null;
        mostrarLogin();
        primaryStage.setMaximized(false);
    }
    
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static void mostrarAlerta(String titulo, String encabezado, String contenido, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    
    @Override
    public void stop() {
        // Cerrar conexión a base de datos al cerrar la aplicación
        DatabaseConnection.getInstance().closeConnection();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}