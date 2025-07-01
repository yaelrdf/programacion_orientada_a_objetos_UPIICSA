package com.centrocultural.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import com.centrocultural.MainApp;
import com.centrocultural.dao.*;
import com.centrocultural.models.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GruposController implements Initializable, BaseController {
    
    @FXML private TextField txtBuscar;
    @FXML private TableView<Grupo> tablaGrupos;
    @FXML private TableColumn<Grupo, Integer> colId;
    @FXML private TableColumn<Grupo, String> colNombre;
    @FXML private TableColumn<Grupo, String> colActividad;
    @FXML private TableColumn<Grupo, String> colInstructor;
    @FXML private TableColumn<Grupo, String> colHorario;
    @FXML private TableColumn<Grupo, String> colAcciones;
    
    private GrupoDAO grupoDAO = new GrupoDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarDatos();
    }
    
    @Override
    public void setData(Object data) {
        // No se necesita para esta vista
    }
    
    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idGrupo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreGrupo"));
        colActividad.setCellValueFactory(cellData -> {
            if (cellData.getValue().getActividad() != null) {
                return new javafx.beans.property.SimpleStringProperty(
                    cellData.getValue().getActividad().getNombre()
                );
            }
            return new javafx.beans.property.SimpleStringProperty("-");
        });
        colInstructor.setCellValueFactory(cellData -> {
            if (cellData.getValue().getInstructor() != null) {
                return new javafx.beans.property.SimpleStringProperty(
                    cellData.getValue().getInstructor().getNombreCompleto()
                );
            }
            return new javafx.beans.property.SimpleStringProperty("-");
        });
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        
        colAcciones.setCellFactory(column -> new TableCell<>() {
            private final Button btnDetalles = new Button("Detalles");
            
            {
                btnDetalles.setOnAction(event -> {
                    Grupo grupo = getTableView().getItems().get(getIndex());
                    verDetalles(grupo);
                });
                btnDetalles.getStyleClass().add("button-secondary");
            }
            
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnDetalles);
                }
            }
        });
    }
    
    public void cargarDatos() {
        try {
            tablaGrupos.getItems().clear();
            tablaGrupos.getItems().addAll(grupoDAO.listarTodos());
        } catch (SQLException e) {
            e.printStackTrace();
            MainApp.mostrarAlerta("Error", 
                                 "Error al cargar grupos", 
                                 "No se pudieron cargar los grupos: " + e.getMessage(), 
                                 Alert.AlertType.ERROR);
        }
    }
    
    @FXML
     private void buscar() {
        String criterio = txtBuscar.getText().trim();
        try {
            tablaGrupos.getItems().clear();
            if (criterio.isEmpty()) {
                tablaGrupos.getItems().addAll(grupoDAO.listarTodos());
            } else {
                tablaGrupos.getItems().addAll(grupoDAO.buscar(criterio));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            MainApp.getInstance().mostrarAlerta("Error", 
                                 "Error al buscar", 
                                 "No se pudo realizar la búsqueda: " + e.getMessage(), 
                                 Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void agregar() {
        PantallaPrincipalController.abrirVentanaModal(
            "Agregar Grupo", 
            "/fxml/GrupoForm.fxml", 
            null
        );
        cargarDatos(); // Recargar datos después de agregar
    }
    
    private void verDetalles(Grupo grupo) {
        PantallaPrincipalController.abrirVentanaModal(
            "Detalles de Grupo", 
            "/fxml/GrupoDetalles.fxml", 
            grupo
        );
        cargarDatos(); // Recargar datos después de posibles cambios
    }
}