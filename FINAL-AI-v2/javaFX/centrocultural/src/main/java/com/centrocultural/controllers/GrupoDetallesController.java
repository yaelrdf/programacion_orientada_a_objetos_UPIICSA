package com.centrocultural.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import com.centrocultural.Commons;
import com.centrocultural.MainApp;
import com.centrocultural.dao.*;
import com.centrocultural.models.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class GrupoDetallesController implements Initializable, BaseController {
    
    @FXML private Label lblId;
    @FXML private Label lblNombre;
    @FXML private Label lblActividad;
    @FXML private Label lblInstructor;
    @FXML private Label lblHorario;
    @FXML private Label lblUbicacion;
    
    @FXML private TableView<Inscripcion> tablaAlumnos;
    @FXML private TableColumn<Inscripcion, Integer> colExpediente;
    @FXML private TableColumn<Inscripcion, String> colNombreAlumno;
    @FXML private TableColumn<Inscripcion, String> colFechaInscripcion;
    @FXML private TableColumn<Inscripcion, String> colAccionesAlumno;
    
    private GrupoDAO grupoDAO = new GrupoDAO();
    private InscripcionDAO inscripcionDAO = new InscripcionDAO();
    private Grupo grupo;

    @Override
    public void cargarDatos() {
        // Implementación requerida por BaseController
        // Ya tenemos esta lógica en cargarDatosGrupo()
        cargarDatosGrupo();
    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }
    
    @Override
    public void setData(Object data) {
        if (data instanceof Grupo) {
            this.grupo = (Grupo) data;
            cargarDatosGrupo();
        }
    }
    
    private void configurarTabla() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        colExpediente.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleIntegerProperty(
                cellData.getValue().getAlumno().getNoExpediente()
            ).asObject()
        );
        
        colNombreAlumno.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getAlumno().getNombreCompleto()
            )
        );
        
        colFechaInscripcion.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getFechaInscripcion().format(formatter)
            )
        );
        
        colAccionesAlumno.setCellFactory(column -> new TableCell<>() {
            private final Button btnEliminar = new Button("Eliminar");
            
            {
                btnEliminar.setOnAction(event -> {
                    Inscripcion inscripcion = getTableView().getItems().get(getIndex());
                    eliminarAlumno(inscripcion);
                });
                btnEliminar.getStyleClass().add("button-danger");
            }
            
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnEliminar);
                }
            }
        });
    }
    private void cargarDatosGrupo() {
        lblId.setText(String.valueOf(grupo.getIdGrupo()));
        lblNombre.setText(grupo.getNombreGrupo());
        lblActividad.setText(grupo.getActividad() != null ? grupo.getActividad().getNombre() : "-");
        lblInstructor.setText(grupo.getInstructor() != null ? grupo.getInstructor().getNombreCompleto() : "-");
        lblHorario.setText(grupo.getHorario());
        lblUbicacion.setText(grupo.getUbicacion()); // Ahora muestra el área de la actividad
    }
    
    private void eliminarAlumno(Inscripcion inscripcion) {
        if (!Commons.confirmarAccion("Confirmar eliminación", 
                                   "¿Está seguro de eliminar este alumno del grupo?",
                                   "El alumno será dado de baja de este grupo")) {
            return;
        }
        
        try {
            if (inscripcionDAO.darDeBajaInscripcion(inscripcion.getIdInscripcion())) {
                MainApp.getInstance().mostrarAlerta("Éxito", 
                                    "Alumno eliminado", 
                                    "El alumno ha sido eliminado del grupo", 
                                    Alert.AlertType.INFORMATION);
                // Recargar datos del grupo
                grupo = grupoDAO.buscarPorId(grupo.getIdGrupo());
                cargarDatosGrupo();
            } else {
                MainApp.getInstance().mostrarAlerta("Error", 
                                    "Error al eliminar", 
                                    "No se pudo eliminar al alumno del grupo", 
                                    Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            MainApp.getInstance().mostrarAlerta("Error", 
                                "Error al eliminar", 
                                "No se pudo eliminar al alumno: " + e.getMessage(), 
                                Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void editar() {
        PantallaPrincipalController.abrirVentanaModal(
            "Editar Grupo", 
            "/fxml/GrupoForm.fxml", 
            grupo
        );
        
        // Recargar datos después de editar
        try {
            grupo = grupoDAO.buscarPorId(grupo.getIdGrupo());
            cargarDatosGrupo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void eliminar() {
        if (!Commons.confirmarAccion("Confirmar eliminación", 
                                   "¿Está seguro de eliminar este grupo?",
                                   "Todos los alumnos serán dados de baja")) {
            return;
        }
        
        try {
            if (grupoDAO.eliminar(grupo.getIdGrupo())) {
                MainApp.mostrarAlerta("Éxito", 
                                    "Grupo eliminado", 
                                    "El grupo ha sido eliminado exitosamente", 
                                    Alert.AlertType.INFORMATION);
                cerrar();
            } else {
                MainApp.mostrarAlerta("Error", 
                                    "Error al eliminar", 
                                    "No se pudo eliminar el grupo", 
                                    Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            MainApp.mostrarAlerta("Error", 
                                "Error al eliminar", 
                                "No se pudo eliminar el grupo: " + e.getMessage(), 
                                Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void cerrar() {
        Stage stage = (Stage) lblId.getScene().getWindow();
        stage.close();
    }
}