package com.centrocultural.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.centrocultural.dao.ActividadDAO;
import com.centrocultural.models.Actividad;
//import com.centrocultural.utils.PDFGenerator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class ActividadesViewController implements Initializable, BaseController {
    
    @FXML private ComboBox<String> cmbClasificacion;
    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnAgregar;
    @FXML private Button btnGenerarReporte;
    
    @FXML private TableView<Actividad> tablaActividades;
    @FXML private TableColumn<Actividad, CheckBox> colSeleccion;
    @FXML private TableColumn<Actividad, Integer> colId;
    @FXML private TableColumn<Actividad, String> colNombre;
    @FXML private TableColumn<Actividad, String> colClasificacion;
    @FXML private TableColumn<Actividad, String> colArea;
    @FXML private TableColumn<Actividad, Integer> colGrupos;
    @FXML private TableColumn<Actividad, String> colEstado;
    @FXML private TableColumn<Actividad, Button> colDetalles;
    
    private ActividadDAO actividadDAO = new ActividadDAO();
    private ObservableList<Actividad> listaActividades = FXCollections.observableArrayList();
    private List<Actividad> actividadesSeleccionadas = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarComboBox();
        configurarTabla();
        configurarEventos();
        cargarDatos();
    }
    
    private void configurarComboBox() {
        cmbClasificacion.getItems().addAll(
            "Todas las clasificaciones",
            "deportiva",
            "cultural",
            "oficio"
        );
        cmbClasificacion.setValue("Todas las clasificaciones");
    }
    
    private void configurarTabla() {
        colSeleccion.setCellFactory(col -> new TableCell<Actividad, CheckBox>() {
            @Override
            protected void updateItem(CheckBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    Actividad actividad = getTableView().getItems().get(getIndex());
                    
                    checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                        if (isSelected) {
                            actividadesSeleccionadas.add(actividad);
                        } else {
                            actividadesSeleccionadas.remove(actividad);
                        }
                        actualizarBotonReporte();
                    });
                    
                    setGraphic(checkBox);
                }
            }
        });
        
        colId.setCellValueFactory(new PropertyValueFactory<>("idActividad"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colClasificacion.setCellValueFactory(new PropertyValueFactory<>("clasificacion"));
        colArea.setCellValueFactory(cellData -> 
            new SimpleStringProperty(
                cellData.getValue().getAreaAsignada() != null ? 
                cellData.getValue().getAreaAsignada() : "-"
            )
        );
        
        colGrupos.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getGrupos().size()).asObject()
        );
        
        colEstado.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().isActiva() ? "Activa" : "Inactiva")
        );
        
        colDetalles.setCellFactory(col -> new TableCell<Actividad, Button>() {
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Button btnDetalles = new Button("Detalles");
                    btnDetalles.getStyleClass().add("button-detalles");
                    btnDetalles.setOnAction(e -> {
                        Actividad actividad = getTableView().getItems().get(getIndex());
                        mostrarDetalles(actividad);
                    });
                    setGraphic(btnDetalles);
                }
            }
        });
        
        // Ajustar anchos
        colSeleccion.setPrefWidth(50);
        colId.setPrefWidth(60);
        colNombre.setPrefWidth(200);
        colClasificacion.setPrefWidth(100);
        colArea.setPrefWidth(150);
        colGrupos.setPrefWidth(80);
        colEstado.setPrefWidth(80);
        //colDetalles.setPrefWidth(100);
        colDetalles.setCellFactory(col -> new TableCell<Actividad, Button>() {
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox contenedorBotones = new HBox(5);
                    
                    Button btnEditar = new Button("Editar");
                    btnEditar.getStyleClass().add("button-secondary");
                    btnEditar.setOnAction(e -> {
                        Actividad actividad = getTableView().getItems().get(getIndex());
                        editarActividad(actividad);
                    });
                    
                    Button btnDetalles = new Button("Detalles");
                    btnDetalles.getStyleClass().add("button-detalles");
                    btnDetalles.setOnAction(e -> {
                        Actividad actividad = getTableView().getItems().get(getIndex());
                        mostrarDetalles(actividad);
                    });
                    
                    contenedorBotones.getChildren().addAll(btnEditar, btnDetalles);
                    setGraphic(contenedorBotones);
                }
            }
        });
        
        tablaActividades.setItems(listaActividades);
    }
    
    private void configurarEventos() {
        btnBuscar.setOnAction(e -> buscar());
        btnLimpiar.setOnAction(e -> limpiar());
        btnAgregar.setOnAction(e -> agregarActividad());
        //btnGenerarReporte.setOnAction(e -> generarReporte());
        
        cmbClasificacion.setOnAction(e -> filtrarPorClasificacion());
        
        txtBuscar.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                buscar();
            }
        });
    }
    
    @Override
    public void cargarDatos() {
        try {
            listaActividades.clear();
            actividadesSeleccionadas.clear();
            List<Actividad> actividades = actividadDAO.listarTodos();
            
            // Cargar datos completos
            for (Actividad actividad : actividades) {
                Actividad completa = actividadDAO.buscarPorId(actividad.getIdActividad());
                listaActividades.add(completa);
            }
            
            actualizarBotonReporte();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar datos", 
                         "No se pudieron cargar las actividades: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }
    
    private void editarActividad(Actividad actividad) {
        PantallaPrincipalController.abrirVentanaModal(
            "Editar Actividad", 
            "/fxml/ActividadForm.fxml", 
            actividad
        );
        cargarDatos();
    }
    
    private void mostrarDetalles(Actividad actividad) {
        try {
            Actividad actividadCompleta = actividadDAO.buscarPorId(actividad.getIdActividad());
            
            PantallaPrincipalController.abrirVentanaModal(
                "Detalles de Actividad", 
                "/fxml/ActividadDetalles.fxml", 
                actividadCompleta
            );
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar detalles", 
                         "No se pudieron cargar los detalles: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }

    private void buscar() {
        String criterio = txtBuscar.getText().trim();
        if (criterio.isEmpty() && cmbClasificacion.getValue().equals("Todas las clasificaciones")) {
            cargarDatos();
            return;
        }
        
        filtrar();
    }
    
    private void filtrarPorClasificacion() {
        filtrar();
    }
    
    private void filtrar() {
        try {
            listaActividades.clear();
            actividadesSeleccionadas.clear();
            
            String criterio = txtBuscar.getText().trim();
            String clasificacion = cmbClasificacion.getValue();
            
            List<Actividad> actividades;
            
            if (!clasificacion.equals("Todas las clasificaciones") && !criterio.isEmpty()) {
                // Filtrar por ambos criterios
                actividades = actividadDAO.buscar(criterio);
                actividades.removeIf(a -> !a.getClasificacion().equals(clasificacion));
            } else if (!clasificacion.equals("Todas las clasificaciones")) {
                // Solo por clasificación
                actividades = actividadDAO.listarPorClasificacion(clasificacion);
            } else if (!criterio.isEmpty()) {
                // Solo por búsqueda
                actividades = actividadDAO.buscar(criterio);
            } else {
                // Todas
                actividades = actividadDAO.listarTodos();
            }
            
            for (Actividad actividad : actividades) {
                Actividad completa = actividadDAO.buscarPorId(actividad.getIdActividad());
                listaActividades.add(completa);
            }
            
            actualizarBotonReporte();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al filtrar", 
                         "No se pudo realizar el filtrado: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }
    
    private void limpiar() {
        txtBuscar.clear();
        cmbClasificacion.setValue("Todas las clasificaciones");
        cargarDatos();
    }
    
    private void agregarActividad() {
        PantallaPrincipalController.abrirVentanaModal(
            "Agregar Actividad", 
            "/fxml/ActividadForm.fxml", 
            null
        );
        cargarDatos();
    }
    
    /*private void mostrarDetalles(Actividad actividad) {
        try {
            Actividad actividadCompleta = actividadDAO.buscarPorId(actividad.getIdActividad());
            
            PantallaPrincipalController.abrirVentanaModal(
                "Detalles de Actividad", 
                "/fxml/ActividadDetalles.fxml", 
                actividadCompleta
            );
            cargarDatos();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar detalles", 
                         "No se pudieron cargar los detalles de la actividad: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }*/
    
   private void actualizarBotonReporte() {
        btnGenerarReporte.setDisable(actividadesSeleccionadas.isEmpty());
    }
   /* 
    private void generarReporte() {
        if (actividadesSeleccionadas.isEmpty()) {
            mostrarAlerta("Información", "Sin selección", 
                         "Seleccione al menos una actividad para generar el reporte", 
                         Alert.AlertType.INFORMATION);
            return;
        }
        
        try {
            String archivo = PDFGenerator.generarReporteActividades(actividadesSeleccionadas);
            mostrarAlerta("Éxito", "Reporte generado", 
                         "El reporte se ha guardado en: " + archivo, 
                         Alert.AlertType.INFORMATION);
            
            actividadesSeleccionadas.clear();
            cargarDatos();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al generar reporte", 
                         "No se pudo generar el reporte: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }*/
}