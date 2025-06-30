package com.centrocultural.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.centrocultural.dao.UsuarioDAO;
import com.centrocultural.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UsuarioFormController implements Initializable, BaseController {
    
    @FXML private Label lblTitulo;
    @FXML private TextField txtNombreUsuario;
    @FXML private PasswordField txtContrasena;
    @FXML private PasswordField txtConfirmarContrasena;
    @FXML private TextField txtNombreEmpleado;
    @FXML private ComboBox<String> cmbAreaAsignada;
    @FXML private Label lblNotaContrasena;
    @FXML private Button btnGuardar;
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Usuario usuarioEditar = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarComboBox();
    }
    
    private void configurarComboBox() {
        cmbAreaAsignada.getItems().addAll(
            "administracion",
            "contabilidad"
        );
    }
    
    @Override
    public void setData(Object data) {
        if (data instanceof Usuario) {
            this.usuarioEditar = (Usuario) data;
            cargarDatosUsuario();
        }
    }
    
    private void cargarDatosUsuario() {
        lblTitulo.setText("Editar Usuario");
        txtNombreUsuario.setText(usuarioEditar.getNombreUsuario());
        txtNombreEmpleado.setText(usuarioEditar.getNombreEmpleado());
        cmbAreaAsignada.setValue(usuarioEditar.getAreaAsignada());
        lblNotaContrasena.setVisible(true);
    }
    
    @FXML
    private void guardar() {
        if (!validarFormulario()) {
            return;
        }
        
        try {
            String nombreUsuario = txtNombreUsuario.getText().trim();
            String nombreEmpleado = txtNombreEmpleado.getText().trim();
            String areaAsignada = cmbAreaAsignada.getValue();
            
            if (usuarioEditar == null) {
                // Crear nuevo usuario
                String contrasena = txtContrasena.getText();
                
                Usuario nuevoUsuario = new Usuario(
                    nombreUsuario,
                    contrasena,
                    nombreEmpleado,
                    areaAsignada
                );
                
                int id = usuarioDAO.crear(nuevoUsuario);
                if (id > 0) {
                    mostrarAlerta("Éxito", "Usuario creado", 
                                "El usuario ha sido registrado exitosamente", 
                                Alert.AlertType.INFORMATION);
                    cerrarVentana();
                }
            } else {
                // Actualizar usuario existente
                usuarioEditar.setNombreUsuario(nombreUsuario);
                usuarioEditar.setNombreEmpleado(nombreEmpleado);
                usuarioEditar.setAreaAsignada(areaAsignada);
                
                // Solo actualizar contraseña si se proporcionó una nueva
                if (!txtContrasena.getText().isEmpty()) {
                    usuarioEditar.setContrasena(txtContrasena.getText());
                }
                
                if (usuarioDAO.actualizar(usuarioEditar)) {
                    mostrarAlerta("Éxito", "Usuario actualizado", 
                                "Los datos del usuario han sido actualizados", 
                                Alert.AlertType.INFORMATION);
                    cerrarVentana();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al guardar", 
                        "No se pudo guardar el usuario: " + e.getMessage(), 
                        Alert.AlertType.ERROR);
        }
    }
    
    private boolean validarFormulario() {
        String nombreUsuario = txtNombreUsuario.getText().trim();
        String nombreEmpleado = txtNombreEmpleado.getText().trim();
        String contrasena = txtContrasena.getText();
        String confirmarContrasena = txtConfirmarContrasena.getText();
        String areaAsignada = cmbAreaAsignada.getValue();
        
        if (nombreUsuario.isEmpty()) {
            mostrarAlerta("Validación", "Campo requerido", 
                         "El nombre de usuario es obligatorio", 
                         Alert.AlertType.WARNING);
            return false;
        }
        
        if (nombreEmpleado.isEmpty()) {
            mostrarAlerta("Validación", "Campo requerido", 
                         "El nombre del empleado es obligatorio", 
                         Alert.AlertType.WARNING);
            return false;
        }
        
        if (areaAsignada == null) {
            mostrarAlerta("Validación", "Campo requerido", 
                         "Debe seleccionar un área asignada", 
                         Alert.AlertType.WARNING);
            return false;
        }
        
        // Validaciones específicas para nuevo usuario
        if (usuarioEditar == null) {
            if (contrasena.isEmpty()) {
                mostrarAlerta("Validación", "Campo requerido", 
                             "La contraseña es obligatoria para nuevos usuarios", 
                             Alert.AlertType.WARNING);
                return false;
            }
            
            if (!contrasena.equals(confirmarContrasena)) {
                mostrarAlerta("Validación", "Contraseñas no coinciden", 
                             "Las contraseñas ingresadas no coinciden", 
                             Alert.AlertType.WARNING);
                return false;
            }
        } else {
            // Validaciones para edición (solo si se cambia la contraseña)
            if (!contrasena.isEmpty() && !contrasena.equals(confirmarContrasena)) {
                mostrarAlerta("Validación", "Contraseñas no coinciden", 
                             "Las contraseñas ingresadas no coinciden", 
                             Alert.AlertType.WARNING);
                return false;
            }
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
}