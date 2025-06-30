package com.centrocultural.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import com.centrocultural.MainApp;
import com.centrocultural.dao.AlumnoDAO;
import com.centrocultural.models.Alumno;
import com.centrocultural.models.Usuario;
//import com.centrocultural.utils.PDFGenerator;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AlumnosViewController implements Initializable, BaseController {
    
    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnAgregar;
    @FXML private Button btnGenerarReporte;
    
    @FXML private TableView<Alumno> tablaAlumnos;
    @FXML private TableColumn<Alumno, CheckBox> colSeleccion;
    @FXML private TableColumn<Alumno, Integer> colExpediente;
    @FXML private TableColumn<Alumno, String> colNombre;
    @FXML private TableColumn<Alumno, String> colFechaNacimiento;
    @FXML private TableColumn<Alumno, String> colActividades;
    @FXML private TableColumn<Alumno, Button> colDetalles;
    
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private ObservableList<Alumno> listaAlumnos = FXCollections.observableArrayList();
    private List<Alumno> alumnosSeleccionados = new ArrayList<>();
    private Usuario usuarioActual;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioActual = MainApp.getInstance().getUsuarioActual();
        configurarTabla();
        configurarEventos();
        configurarPermisos();
        cargarDatos();
    }
    
    private void configurarTabla() {
        // Configurar columnas
        colSeleccion.setCellFactory(col -> new TableCell<Alumno, CheckBox>() {
            @Override
            protected void updateItem(CheckBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    Alumno alumno = getTableView().getItems().get(getIndex());
                    
                    checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                        if (isSelected) {
                            alumnosSeleccionados.add(alumno);
                        } else {
                            alumnosSeleccionados.remove(alumno);
                        }
                        actualizarBotonReporte();
                    });
                    
                    setGraphic(checkBox);
                }
            }
        });
        
        colExpediente.setCellValueFactory(new PropertyValueFactory<>("noExpediente"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        
        colFechaNacimiento.setCellValueFactory(cellData -> {
            if (cellData.getValue().getFechaNacimiento() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                return new SimpleStringProperty(
                    cellData.getValue().getFechaNacimiento().format(formatter)
                );
            }
            return new SimpleStringProperty("");
        });
        
        colActividades.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getActividadesInscritas())
        );
        
        colDetalles.setCellFactory(col -> new TableCell<Alumno, Button>() {
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Button btnDetalles = new Button("Detalles");
                    btnDetalles.getStyleClass().add("button-detalles");
                    btnDetalles.setOnAction(e -> {
                        Alumno alumno = getTableView().getItems().get(getIndex());
                        mostrarDetalles(alumno);
                    });
                    setGraphic(btnDetalles);
                }
            }
        });
        
        // Ajustar anchos de columnas
        colSeleccion.setPrefWidth(50);
        colExpediente.setPrefWidth(100);
        colNombre.setPrefWidth(250);
        colFechaNacimiento.setPrefWidth(120);
        colActividades.setPrefWidth(300);
        colDetalles.setPrefWidth(100);
        
        tablaAlumnos.setItems(listaAlumnos);
    }
    
    private void configurarEventos() {
        btnBuscar.setOnAction(e -> buscar());
        btnLimpiar.setOnAction(e -> limpiarBusqueda(txtBuscar, tablaAlumnos));
        btnAgregar.setOnAction(e -> agregarAlumno());
        //btnGenerarReporte.setOnAction(e -> generarReporte());
        
        // Buscar al presionar Enter
        txtBuscar.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                buscar();
            }
        });
    }
    
    private void configurarPermisos() {
        // Si es usuario de contabilidad, no puede agregar alumnos
        if (usuarioActual.esContabilidad()) {
            btnAgregar.setVisible(false);
            btnAgregar.setManaged(false);
        }
    }
    
    @Override
    public void cargarDatos() {
        try {
            listaAlumnos.clear();
            alumnosSeleccionados.clear();
            List<Alumno> alumnos = alumnoDAO.listarTodos();
            listaAlumnos.addAll(alumnos);
            actualizarBotonReporte();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar datos", 
                         "No se pudieron cargar los alumnos: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }
    
    private void buscar() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty()) {
            cargarDatos();
            return;
        }
        
        try {
            listaAlumnos.clear();
            alumnosSeleccionados.clear();
            List<Alumno> alumnos = alumnoDAO.buscar(criterio);
            listaAlumnos.addAll(alumnos);
            actualizarBotonReporte();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al buscar", 
                         "No se pudo realizar la búsqueda: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }
    
    private void agregarAlumno() {
        PantallaPrincipalController.abrirVentanaModal(
            "Agregar Alumno", 
            "/fxml/AlumnoForm.fxml", 
            null
        );
        cargarDatos();
    }
    
    private void mostrarDetalles(Alumno alumno) {
        try {
            // Cargar alumno completo con inscripciones
            Alumno alumnoCompleto = alumnoDAO.buscarPorId(alumno.getNoExpediente());
            
            PantallaPrincipalController.abrirVentanaModal(
                "Detalles de Alumno", 
                "/fxml/AlumnoDetalles.fxml", 
                alumnoCompleto
            );
            cargarDatos();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar detalles", 
                         "No se pudieron cargar los detalles del alumno: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }
    
    private void actualizarBotonReporte() {
        btnGenerarReporte.setDisable(alumnosSeleccionados.isEmpty());
    }
    
    /*private void generarReporte() {
        if (alumnosSeleccionados.isEmpty()) {
            mostrarAlerta("Información", "Sin selección", 
                         "Seleccione al menos un alumno para generar el reporte", 
                         Alert.AlertType.INFORMATION);
            return;
        }
        
        try {
            String archivo = PDFGenerator.generarReporteAlumnos(alumnosSeleccionados);
            mostrarAlerta("Éxito", "Reporte generado", 
                         "El reporte se ha guardado en: " + archivo, 
                         Alert.AlertType.INFORMATION);
            
            // Limpiar selección
            alumnosSeleccionados.clear();
            cargarDatos();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al generar reporte", 
                         "No se pudo generar el reporte: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }*/
}