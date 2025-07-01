package com.centrocultural.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.centrocultural.dao.MaterialDAO;
import com.centrocultural.models.Material;
//import com.centrocultural.utils.PDFGenerator;

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

public class MaterialesViewController implements Initializable, BaseController {
    
    @FXML private ComboBox<String> cmbCondicion;
    @FXML private CheckBox chkSoloDisponibles;
    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnAgregar;
    @FXML private Button btnGenerarReporte;
    
    @FXML private TableView<Material> tablaMateriales;
    @FXML private TableColumn<Material, CheckBox> colSeleccion;
    @FXML private TableColumn<Material, Integer> colId;
    @FXML private TableColumn<Material, String> colNombre;
    @FXML private TableColumn<Material, String> colArea;
    @FXML private TableColumn<Material, String> colCondicion;
    @FXML private TableColumn<Material, String> colGrupos;
    @FXML private TableColumn<Material, String> colDisponible;
    @FXML private TableColumn<Material, Button> colDetalles;
    
    private MaterialDAO materialDAO = new MaterialDAO();
    private ObservableList<Material> listaMateriales = FXCollections.observableArrayList();
    private List<Material> materialesSeleccionados = new ArrayList<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarComboBox();
        configurarTabla();
        configurarEventos();
        cargarDatos();
    }
    
    private void configurarComboBox() {
        cmbCondicion.getItems().addAll(
            "Todas las condiciones",
            "bueno",
            "regular",
            "malo"
        );
        cmbCondicion.setValue("Todas las condiciones");
    }
    
    private void configurarTabla() {
        colSeleccion.setCellFactory(col -> new TableCell<Material, CheckBox>() {
            @Override
            protected void updateItem(CheckBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    CheckBox checkBox = new CheckBox();
                    Material material = getTableView().getItems().get(getIndex());
                    
                    checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                        if (isSelected) {
                            materialesSeleccionados.add(material);
                        } else {
                            materialesSeleccionados.remove(material);
                        }
                        actualizarBotonReporte();
                    });
                    
                    setGraphic(checkBox);
                }
            }
        });
        
        colId.setCellValueFactory(new PropertyValueFactory<>("idMaterial"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colArea.setCellValueFactory(new PropertyValueFactory<>("areaAlmacenamiento"));
        colCondicion.setCellValueFactory(new PropertyValueFactory<>("condicion"));
        
        colGrupos.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getGruposAsignadosString())
        );
        
        colDisponible.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().isDisponible() ? "Sí" : "No")
        );
        
        colDetalles.setCellFactory(col -> new TableCell<Material, Button>() {
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Button btnDetalles = new Button("Detalles");
                    btnDetalles.getStyleClass().add("button-detalles");
                    btnDetalles.setOnAction(e -> {
                        Material material = getTableView().getItems().get(getIndex());
                        mostrarDetalles(material);
                    });
                    setGraphic(btnDetalles);
                }
            }
        });
        
        // Ajustar anchos
        colSeleccion.setPrefWidth(50);
        colId.setPrefWidth(60);
        colNombre.setPrefWidth(200);
        colArea.setPrefWidth(180);
        colCondicion.setPrefWidth(100);
        colGrupos.setPrefWidth(200);
        colDisponible.setPrefWidth(80);
        colDetalles.setPrefWidth(100);
        
        tablaMateriales.setItems(listaMateriales);
    }
    
    private void configurarEventos() {
        btnBuscar.setOnAction(e -> buscar());
        btnLimpiar.setOnAction(e -> limpiar());
        btnAgregar.setOnAction(e -> agregarMaterial());
        btnGenerarReporte.setOnAction(e -> generarReporte());
        
        cmbCondicion.setOnAction(e -> filtrar());
        chkSoloDisponibles.setOnAction(e -> filtrar());
        
        txtBuscar.setOnKeyPressed(event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.ENTER) {
                buscar();
            }
        });
    }
    
    @Override
    public void cargarDatos() {
        try {
            listaMateriales.clear();
            materialesSeleccionados.clear();
            List<Material> materiales = materialDAO.listarTodos();
            
            // Cargar datos completos
            for (Material material : materiales) {
                Material completo = materialDAO.buscarPorId(material.getIdMaterial());
                listaMateriales.add(completo);
            }
            
            actualizarBotonReporte();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar datos", 
                         "No se pudieron cargar los materiales: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }
    
    private void buscar() {
        filtrar();
    }
    
    private void filtrar() {
        try {
            listaMateriales.clear();
            materialesSeleccionados.clear();
            
            String criterio = txtBuscar.getText().trim();
            String condicion = cmbCondicion.getValue();
            boolean soloDisponibles = chkSoloDisponibles.isSelected();
            
            List<Material> materiales;
            
            // Obtener lista base
            if (!criterio.isEmpty()) {
                materiales = materialDAO.buscar(criterio);
            } else if (soloDisponibles) {
                materiales = materialDAO.listarDisponibles();
            } else {
                materiales = materialDAO.listarTodos();
            }
            
            // Aplicar filtros adicionales
            if (!condicion.equals("Todas las condiciones")) {
                materiales.removeIf(m -> !m.getCondicion().equals(condicion));
            }
            
            if (soloDisponibles && !criterio.isEmpty()) {
                materiales.removeIf(m -> !m.isDisponible());
            }
            
            // Cargar datos completos
            for (Material material : materiales) {
                Material completo = materialDAO.buscarPorId(material.getIdMaterial());
                listaMateriales.add(completo);
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
        cmbCondicion.setValue("Todas las condiciones");
        chkSoloDisponibles.setSelected(false);
        cargarDatos();
    }
    
    // Reemplaza el método agregarMaterial() con este:
private void agregarMaterial() {
    try {
        // Asegúrate de que la ruta del FXML sea correcta
        PantallaPrincipalController.abrirVentanaModal(
            "Agregar Material", 
            "/fxml/MaterialesForm.fxml",  // Ajusta esta ruta según tu estructura de paquetes
            null
        );
        // Actualizar la tabla después de cerrar el formulario
        cargarDatos();
    } catch (Exception e) {
        e.printStackTrace();
        mostrarAlerta("Error", "Error al abrir formulario", 
                     "No se pudo abrir el formulario de material: " + e.getMessage(), 
                     Alert.AlertType.ERROR);
    }
}
    
    private void mostrarDetalles(Material material) {
        try {
            Material materialCompleto = materialDAO.buscarPorId(material.getIdMaterial());
            
            PantallaPrincipalController.abrirVentanaModal(
                "Detalles de Material", 
                "/fxml/MaterialDetalles.fxml", 
                materialCompleto
            );
            cargarDatos();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar detalles", 
                         "No se pudieron cargar los detalles del material: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }
    
    private void actualizarBotonReporte() {
        btnGenerarReporte.setDisable(materialesSeleccionados.isEmpty());
    }
    
    private void generarReporte() {
        if (materialesSeleccionados.isEmpty()) {
            mostrarAlerta("Información", "Sin selección", 
                         "Seleccione al menos un material para generar el reporte", 
                         Alert.AlertType.INFORMATION);
            return;
        }
        
        try {
            /*String archivo = PDFGenerator.generarReporteMateriales(materialesSeleccionados);
            mostrarAlerta("Éxito", "Reporte generado", 
                         "El reporte se ha guardado en: " + archivo, 
                         Alert.AlertType.INFORMATION);
            
            materialesSeleccionados.clear();
            cargarDatos();*/
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al generar reporte", 
                         "No se pudo generar el reporte: " + e.getMessage(), 
                         Alert.AlertType.ERROR);
        }
    }
}