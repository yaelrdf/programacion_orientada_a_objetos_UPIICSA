package com.centrocultural.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import com.centrocultural.dao.AlumnoDAO;
import com.centrocultural.models.Alumno;
import com.centrocultural.models.Inscripcion;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AlumnoDetallesController implements Initializable, BaseController {
    
    @FXML private Label lblExpediente;
    @FXML private Label lblFechaRegistro;
    @FXML private Label lblNombre;
    @FXML private Label lblFechaNacimiento;
    @FXML private Label lblEdad;
    
    @FXML private TableView<Inscripcion> tablaInscripciones;
    @FXML private TableColumn<Inscripcion, String> colActividad;
    @FXML private TableColumn<Inscripcion, String> colGrupo;
    @FXML private TableColumn<Inscripcion, String> colFechaInscripcion;
    @FXML private TableColumn<Inscripcion, String> colEstadoPago;
    @FXML private TableColumn<Inscripcion, String> colEstado;
    
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private Alumno alumno;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
    }
    
    @Override
    public void setData(Object data) {
        if (data instanceof Alumno) {
            this.alumno = (Alumno) data;
            cargarDatosAlumno();
        }
    }
    
    private void configurarTabla() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        colActividad.setCellValueFactory(cellData -> {
            if (cellData.getValue().getGrupo() != null && 
                cellData.getValue().getGrupo().getActividad() != null) {
                return new SimpleStringProperty(
                    cellData.getValue().getGrupo().getActividad().getNombre()
                );
            }
            return new SimpleStringProperty("-");
        });
        
        colGrupo.setCellValueFactory(cellData -> {
            if (cellData.getValue().getGrupo() != null) {
                return new SimpleStringProperty(
                    cellData.getValue().getGrupo().getNombreGrupo()
                );
            }
            return new SimpleStringProperty("-");
        });
        
        colFechaInscripcion.setCellValueFactory(cellData -> 
            new SimpleStringProperty(
                cellData.getValue().getFechaInscripcion().format(formatter)
            )
        );
        
        colEstadoPago.setCellValueFactory(cellData -> 
            new SimpleStringProperty(
                cellData.getValue().isPagada() ? "Pagado" : "Pendiente"
            )
        );
        
        colEstado.setCellValueFactory(cellData -> 
            new SimpleStringProperty(
                cellData.getValue().isActiva() ? "Activa" : "Inactiva"
            )
        );
    }
    
    private void cargarDatosAlumno() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        lblExpediente.setText(String.valueOf(alumno.getNoExpediente()));
        lblFechaRegistro.setText(alumno.getFechaRegistro().format(dateTimeFormatter));
        lblNombre.setText(alumno.getNombreCompleto());
        lblFechaNacimiento.setText(alumno.getFechaNacimiento().format(dateFormatter));
        lblEdad.setText(alumno.getEdad() + " años");
        
        // Cargar inscripciones
        tablaInscripciones.getItems().clear();
        tablaInscripciones.getItems().addAll(alumno.getInscripciones());
    }
    
    @FXML
    private void editar() {
        PantallaPrincipalController.abrirVentanaModal(
            "Editar Alumno", 
            "/fxml/AlumnoForm.fxml", 
            alumno
        );
        
        // Recargar datos después de editar
        try {
            alumno = alumnoDAO.buscarPorId(alumno.getNoExpediente());
            cargarDatosAlumno();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void eliminar() {
        if (!confirmarAccion("Confirmar eliminación", 
                           "¿Está seguro de eliminar este alumno?",
                           "Esta acción no se puede deshacer")) {
            return;
        }
        
        try {
            if (alumnoDAO.eliminar(alumno.getNoExpediente())) {
                mostrarAlerta("Éxito", "Alumno eliminado", 
                            "El alumno ha sido eliminado exitosamente", 
                            Alert.AlertType.INFORMATION);
                cerrar();
            } else {
                mostrarAlerta("Error", "No se puede eliminar", 
                            "El alumno tiene inscripciones registradas y no puede ser eliminado", 
                            Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al eliminar", 
                        "No se pudo eliminar el alumno: " + e.getMessage(), 
                        Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void cerrar() {
        Stage stage = (Stage) lblExpediente.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void cargarDatos() {
        // No se usa en esta vista
    }
}