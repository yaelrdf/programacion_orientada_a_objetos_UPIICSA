package com.centrocultural.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import com.centrocultural.MainApp;
import com.centrocultural.models.Usuario;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PantallaPrincipalController implements Initializable {
    
    @FXML private Label lblTitulo;
    @FXML private Label lblHora;
    @FXML private Label lblUsuario;
    @FXML private Button btnCerrarSesion;
    
    @FXML private VBox menuContainer;
    @FXML private Button btnUsuarios;
    @FXML private Button btnAlumnos;
    @FXML private Button btnInstructores;
    @FXML private Button btnActividades;
    @FXML private Button btnMateriales;
    
    @FXML private StackPane contentArea;
    
    private Timeline clockTimeline;
    private Usuario usuarioActual;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioActual = MainApp.getInstance().getUsuarioActual();
        configurarInterfaz();
        iniciarReloj();
        configurarBotones();
    }
    
    private void configurarInterfaz() {
        // Mostrar información del usuario
        lblUsuario.setText(usuarioActual.getNombreEmpleado());
        
        // Configurar visibilidad de botones según el área del usuario
        if (usuarioActual.esContabilidad()) {
            // Usuario de contabilidad solo puede ver alumnos
            btnUsuarios.setVisible(false);
            btnUsuarios.setManaged(false);
            btnInstructores.setDisable(true);
            btnActividades.setDisable(true);
            btnMateriales.setDisable(true);
        }
        
        // Configurar evento de cerrar sesión
        btnCerrarSesion.setOnAction(e -> cerrarSesion());
    }
    
    private void iniciarReloj() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        clockTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            LocalDateTime now = LocalDateTime.now();
            lblHora.setText(now.format(formatter));
        }));
        
        clockTimeline.setCycleCount(Timeline.INDEFINITE);
        clockTimeline.play();
        
        // Mostrar hora inicial
        lblHora.setText(LocalDateTime.now().format(formatter));
    }
    
    private void configurarBotones() {
        btnUsuarios.setOnAction(e -> cargarModulo("Usuarios"));
        btnAlumnos.setOnAction(e -> cargarModulo("Alumnos"));
        btnInstructores.setOnAction(e -> cargarModulo("Instructores"));
        btnActividades.setOnAction(e -> cargarModulo("Actividades"));
        btnMateriales.setOnAction(e -> cargarModulo("Materiales"));
    }
    
    private void cargarModulo(String modulo) {
        try {
            String fxmlFile = "/fxml/" + modulo + "View.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent content = loader.load();
            
            // Limpiar el área de contenido y agregar el nuevo módulo
            contentArea.getChildren().clear();
            contentArea.getChildren().add(content);
            
            // Actualizar estilo de botón seleccionado
            actualizarBotonSeleccionado(modulo);
            
        } catch (IOException e) {
            e.printStackTrace();
            MainApp.mostrarAlerta("Error", 
                                "No se pudo cargar el módulo", 
                                "Error al cargar " + modulo + ": " + e.getMessage(), 
                                Alert.AlertType.ERROR);
        }
    }
    
    private void actualizarBotonSeleccionado(String modulo) {
        // Quitar clase 'selected' de todos los botones
        btnUsuarios.getStyleClass().remove("menu-button-selected");
        btnAlumnos.getStyleClass().remove("menu-button-selected");
        btnInstructores.getStyleClass().remove("menu-button-selected");
        btnActividades.getStyleClass().remove("menu-button-selected");
        btnMateriales.getStyleClass().remove("menu-button-selected");
        
        // Agregar clase 'selected' al botón correspondiente
        switch (modulo) {
            case "Usuarios":
                btnUsuarios.getStyleClass().add("menu-button-selected");
                break;
            case "Alumnos":
                btnAlumnos.getStyleClass().add("menu-button-selected");
                break;
            case "Instructores":
                btnInstructores.getStyleClass().add("menu-button-selected");
                break;
            case "Actividades":
                btnActividades.getStyleClass().add("menu-button-selected");
                break;
            case "Materiales":
                btnMateriales.getStyleClass().add("menu-button-selected");
                break;
        }
    }
    
    @FXML
    private void cerrarSesion() {
        // Confirmar cierre de sesión
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("¿Desea cerrar sesión?");
        alert.setContentText("Se cerrará su sesión actual");
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            // Detener el reloj
            if (clockTimeline != null) {
                clockTimeline.stop();
            }
            
            // Cerrar sesión
            MainApp.getInstance().cerrarSesion();
        }
    }
    
    // Método para abrir ventanas modales (usado por los módulos)
    public static void abrirVentanaModal(String titulo, String fxmlFile, Object controllerData) {
        try {
            FXMLLoader loader = new FXMLLoader(
                PantallaPrincipalController.class.getResource(fxmlFile)
            );
            Parent root = loader.load();
            
            // Si hay datos para pasar al controlador
            if (controllerData != null && loader.getController() instanceof BaseController) {
                BaseController controller = loader.getController();
                controller.setData(controllerData);
            }
            
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(MainApp.getInstance().getPrimaryStage());
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
            MainApp.mostrarAlerta("Error", 
                                "No se pudo abrir la ventana", 
                                "Error: " + e.getMessage(), 
                                Alert.AlertType.ERROR);
        }
    }
}