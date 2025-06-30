package com.centrocultural.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import com.centrocultural.MainApp;
import com.centrocultural.dao.UsuarioDAO;
import com.centrocultural.models.Usuario;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    
    @FXML private VBox loginBox;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private Button btnIngresar;
    @FXML private Label lblError;
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Ocultar mensaje de error al inicio
        lblError.setVisible(false);
        
        // Configurar eventos
        btnIngresar.setOnAction(e -> intentarLogin());
        
        // Permitir login con Enter
        txtUsuario.setOnKeyPressed(this::manejarTecla);
        txtContrasena.setOnKeyPressed(this::manejarTecla);
        
        // Focus en campo usuario
        txtUsuario.requestFocus();
    }
    
    private void manejarTecla(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            intentarLogin();
        }
    }
    
    @FXML
    private void intentarLogin() {
        String nombreUsuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText();
        
        // Validar campos vacíos
        if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
            mostrarError("Por favor complete todos los campos");
            return;
        }
        
        try {
            // Intentar login
            Usuario usuario = usuarioDAO.login(nombreUsuario, contrasena);
            
            if (usuario != null) {
                // Login exitoso
                lblError.setVisible(false);
                MainApp.getInstance().mostrarPantallaPrincipal(usuario);
            } else {
                // Credenciales incorrectas
                mostrarError("Usuario o contraseña incorrectos");
                txtContrasena.clear();
                txtContrasena.requestFocus();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError("Error de conexión con la base de datos");
        }
    }
    
    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setVisible(true);
        
        // Animar el mensaje de error
        loginBox.getStyleClass().add("login-error");
        
        // Quitar la clase después de la animación
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(
            javafx.util.Duration.seconds(0.5)
        );
        pause.setOnFinished(e -> loginBox.getStyleClass().remove("login-error"));
        pause.play();
    }
}