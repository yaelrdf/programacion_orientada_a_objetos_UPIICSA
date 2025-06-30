package com.centrocultural.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.centrocultural.dao.InstructorDAO;
import com.centrocultural.models.Actividad;
import com.centrocultural.models.Instructor;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class InstructoresViewController implements Initializable, BaseController {
    
    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnAgregar;
    @FXML private Button btnGenerarReporte;
    
    @FXML private TableView<Instructor> tablaInstructores;
    @FXML private TableColumn<Instructor, CheckBox> colSeleccion;
    @FXML private TableColumn<Instructor, Integer> colExpediente;
    @FXML private TableColumn<Instructor, String> colNombre;
    @FXML private TableColumn<Instructor, Integer> colEdad;
    @FXML private TableColumn<Instructor, String> colActividades;
    @FXML private TableColumn<Instructor, Button> colDetalles;
    
    private InstructorDAO instructorDAO = new InstructorDAO();
    private ObservableList<Instructor> listaInstructores = FXCollections.observableArrayList();
    private List<Instructor> instructoresSeleccionados = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        configurarEventos();
        cargarDatos();
    }
    
    private void configurarTabla() {
        colSeleccion.setCellFactory(col -> new TableCell<Instructor, CheckBox>() {
            @Override
            protected void updateItem(CheckBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    Instructor instructor = getTableView().getItems().get(getIndex());
                    
                    checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                        if (isSelected) {
                            instructoresSeleccionados.add(instructor);
                        } else {
                            instructoresSeleccionados.remove(instructor);
                        }
                        actualizarBotonReporte();
                    });
                    
                    setGraphic(checkBox);
                }
            }
        });
        
        colExpediente.setCellValueFactory(new PropertyValueFactory<>("noExpediente"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        
        colEdad.setCellValueFactory(cellData -> {
        LocalDate fechaNacimiento = cellData.getValue().getFechaNacimiento();
        int edad = fechaNacimiento != null ? Period.between(fechaNacimiento, LocalDate.now()).getYears() : 0;
        return new javafx.beans.property.SimpleIntegerProperty(edad).asObject(); // Devuelve IntegerProperty
        });
        
        colActividades.setCellValueFactory(cellData -> 
            new SimpleStringProperty(String.valueOf(cellData.getValue().getActividadesAutorizadas().size()))
        );
        
        colDetalles.setCellFactory(col -> new TableCell<Instructor, Button>() {
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Instructor instructor = getTableView().getItems().get(getIndex());
                    
                    HBox botones = new HBox(5);
                    Button btnEditar = new Button("Editar");
                    Button btnEliminar = new Button("Eliminar");
                    
                    btnEditar.getStyleClass().add("button-detalles");
                    btnEliminar.getStyleClass().add("button-danger");
                    
                    btnEditar.setOnAction(e -> editarInstructor(instructor));
                    btnEliminar.setOnAction(e -> eliminarInstructor(instructor));
                    
                    botones.getChildren().addAll(btnEditar, btnEliminar);
                    setGraphic(botones);
                }
            }
        });
        
        // Ajustar anchos de columnas
        colSeleccion.setPrefWidth(40);
        colExpediente.setPrefWidth(100);
        colNombre.setPrefWidth(250);
        colEdad.setPrefWidth(60);
        colActividades.setCellFactory(column -> new TableCell<Instructor, String>() {
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            Instructor instructor = getTableView().getItems().get(getIndex());
            
            // Crear un TextFlow con las actividades
            TextFlow textFlow = new TextFlow();
            textFlow.setPrefWidth(200); // Ancho máximo
            
            // Agregar cada actividad como un Text separado
            for (Actividad actividad : instructor.getActividadesAutorizadas()) {
                Text text = new Text(actividad.getNombre() + "\n");
                text.setStyle("-fx-fill: #2c3e50; -fx-font-weight: bold;");
                textFlow.getChildren().add(text);
            }
            
            // Mostrar mensaje si no hay actividades
            if (instructor.getActividadesAutorizadas().isEmpty()) {
                Text text = new Text("No hay actividades asignadas");
                text.setStyle("-fx-fill: #7f8c8d; -fx-font-style: italic;");
                textFlow.getChildren().add(text);
            }
            
            setGraphic(textFlow);
        }
    }
});

        colDetalles.setPrefWidth(150);
        
        tablaInstructores.setItems(listaInstructores);

        
    }
    
    private void configurarEventos() {
        btnBuscar.setOnAction(e -> buscar());
        btnLimpiar.setOnAction(e -> limpiar());
        btnAgregar.setOnAction(e -> agregarInstructor());
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
            listaInstructores.clear();
            instructoresSeleccionados.clear();
            listaInstructores.addAll(instructorDAO.listarTodos());
            actualizarBotonReporte();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar datos", 
                         "No se pudieron cargar los instructores: " + e.getMessage(), 
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
            
            listaInstructores.clear();
            instructoresSeleccionados.clear();
            listaInstructores.addAll(instructorDAO.buscar(criterio));
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
    
    private void agregarInstructor() {
        PantallaPrincipalController.abrirVentanaModal(
            "Agregar Instructor", 
            "/fxml/InstructorForm.fxml", 
            null
        );
        cargarDatos();
    }
    
    private void editarInstructor(Instructor instructor) {
        PantallaPrincipalController.abrirVentanaModal(
            "Editar Instructor", 
            "/fxml/InstructorForm.fxml", 
            instructor
        );
        cargarDatos();
    }
    
    private void eliminarInstructor(Instructor instructor) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Eliminar instructor?");
        confirmacion.setContentText("¿Está seguro que desea eliminar al instructor " + instructor.getNombreCompleto() + "?");
        
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                try {
                    if (instructorDAO.eliminar(instructor.getNoExpediente())) {
                        mostrarAlerta("Éxito", "Instructor eliminado", 
                                    "El instructor ha sido eliminado correctamente", 
                                    Alert.AlertType.INFORMATION);
                        cargarDatos();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "Error al eliminar", 
                                 "No se pudo eliminar el instructor: " + e.getMessage(), 
                                 Alert.AlertType.ERROR);
                }
            }
        });
    }
    
    private void actualizarBotonReporte() {
        btnGenerarReporte.setDisable(instructoresSeleccionados.isEmpty());
    }
    
    private void generarReporte() {
        if (instructoresSeleccionados.isEmpty()) {
            mostrarAlerta("Información", "Sin selección", 
                         "Seleccione al menos un instructor para generar el reporte", 
                         Alert.AlertType.INFORMATION);
            return;
        }
        
        try {
            /*String archivo = PDFGenerator.generarReporteInstructores(instructoresSeleccionados);
            mostrarAlerta("Éxito", "Reporte generado", 
                         "El reporte se ha guardado en: " + archivo, 
                         Alert.AlertType.INFORMATION);
            
            instructoresSeleccionados.clear();
            cargarDatos();*/
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al generar reporte", 
                         "No se pudo generar el reporte: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }
}