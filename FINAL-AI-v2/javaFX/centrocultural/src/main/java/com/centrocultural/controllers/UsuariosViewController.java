package com.centrocultural.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.centrocultural.dao.UsuarioDAO;
import com.centrocultural.models.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class UsuariosViewController implements Initializable, BaseController {
    
    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnAgregar;
    @FXML private Button btnGenerarReporte;
    
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, CheckBox> colSeleccion;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colUsuario;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colArea;
    @FXML private TableColumn<Usuario, String> colFechaCreacion;
    @FXML private TableColumn<Usuario, Button> colAcciones;
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    private List<Usuario> usuariosSeleccionados = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        configurarEventos();
        cargarDatos();
    }
    
    private void configurarTabla() {
        colSeleccion.setCellFactory(col -> new TableCell<Usuario, CheckBox>() {
            @Override
            protected void updateItem(CheckBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    
                    checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                        if (isSelected) {
                            usuariosSeleccionados.add(usuario);
                        } else {
                            usuariosSeleccionados.remove(usuario);
                        }
                        actualizarBotonReporte();
                    });
                    
                    setGraphic(checkBox);
                }
            }
        });
        
        colId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
        colArea.setCellValueFactory(new PropertyValueFactory<>("areaAsignada"));
        
        colFechaCreacion.setCellValueFactory(cellData -> 
            new SimpleStringProperty(
                cellData.getValue().getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        ));
        
        colAcciones.setCellFactory(col -> new TableCell<Usuario, Button>() {
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    
                    HBox botones = new HBox(5);
                    Button btnEditar = new Button("Editar");
                    Button btnEliminar = new Button("Eliminar");
                    
                    btnEditar.getStyleClass().add("button-detalles");
                    btnEliminar.getStyleClass().add("button-danger");
                    
                    btnEditar.setOnAction(e -> editarUsuario(usuario));
                    btnEliminar.setOnAction(e -> eliminarUsuario(usuario));
                    
                    botones.getChildren().addAll(btnEditar, btnEliminar);
                    setGraphic(botones);
                }
            }
        });
        
        // Ajustar anchos de columnas
        colSeleccion.setPrefWidth(40);
        colId.setPrefWidth(60);
        colUsuario.setPrefWidth(150);
        colNombre.setPrefWidth(200);
        colArea.setPrefWidth(150);
        colFechaCreacion.setPrefWidth(150);
        colAcciones.setPrefWidth(150);
        
        tablaUsuarios.setItems(listaUsuarios);
    }
    
    private void configurarEventos() {
        btnBuscar.setOnAction(e -> buscar());
        btnLimpiar.setOnAction(e -> limpiar());
        btnAgregar.setOnAction(e -> agregarUsuario());
        btnGenerarReporte.setOnAction(e -> generarReporte());
        
        txtBuscar.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                buscar();
            }
        });
    }
    
    @Override
    public void cargarDatos() {
        try {
            listaUsuarios.clear();
            usuariosSeleccionados.clear();
            listaUsuarios.addAll(usuarioDAO.listarTodos());
            actualizarBotonReporte();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar datos", 
                         "No se pudieron cargar los usuarios: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }
    
    private void buscar() {
        try {
            String criterio = txtBuscar.getText().trim();
            if (criterio.isEmpty()) {
                cargarDatos();
                return;
            }
            
            listaUsuarios.clear();
            usuariosSeleccionados.clear();
            listaUsuarios.addAll(usuarioDAO.buscar(criterio));
            actualizarBotonReporte();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al buscar", 
                         "No se pudo realizar la búsqueda: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }
    
    private void limpiar() {
        txtBuscar.clear();
        cargarDatos();
    }
    
    private void agregarUsuario() {
        PantallaPrincipalController.abrirVentanaModal(
            "Agregar Usuario", 
            "/fxml/UsuarioForm.fxml", 
            null
        );
        cargarDatos();
    }
    
    private void editarUsuario(Usuario usuario) {
        PantallaPrincipalController.abrirVentanaModal(
            "Editar Usuario", 
            "/fxml/UsuarioForm.fxml", 
            usuario
        );
        cargarDatos();
    }
    
    private void eliminarUsuario(Usuario usuario) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Eliminar usuario?");
        confirmacion.setContentText("¿Está seguro que desea eliminar al usuario " + usuario.getNombreEmpleado() + "?");
        
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                try {
                    if (usuarioDAO.eliminar(usuario.getIdUsuario())) {
                        mostrarAlerta("Éxito", "Usuario eliminado", 
                                    "El usuario ha sido eliminado correctamente", 
                                    Alert.AlertType.INFORMATION);
                        cargarDatos();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "Error al eliminar", 
                                 "No se pudo eliminar el usuario: " + e.getMessage(), 
                                 Alert.AlertType.ERROR);
                }
            }
        });
    }
    
    private void actualizarBotonReporte() {
        btnGenerarReporte.setDisable(usuariosSeleccionados.isEmpty());
    }
    
    private void generarReporte() {
        if (usuariosSeleccionados.isEmpty()) {
            mostrarAlerta("Información", "Sin selección", 
                         "Seleccione al menos un usuario para generar el reporte", 
                         Alert.AlertType.INFORMATION);
            return;
        }
        
        try {
            /*String archivo = PDFGenerator.generarReporteUsuarios(usuariosSeleccionados);
            mostrarAlerta("Éxito", "Reporte generado", 
                         "El reporte se ha guardado en: " + archivo, 
                         Alert.AlertType.INFORMATION);
            
            usuariosSeleccionados.clear();
            cargarDatos();*/
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al generar reporte", 
                         "No se pudo generar el reporte: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }
}