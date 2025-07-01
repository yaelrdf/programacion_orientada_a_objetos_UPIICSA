package com.centrocultural.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import com.centrocultural.dao.*;
import com.centrocultural.models.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AlumnoDetallesController implements Initializable, BaseController {
    
    @FXML private Label lblExpediente;
    @FXML private Label lblFechaRegistro;
    @FXML private Label lblNombre;
    @FXML private Label lblFechaNacimiento;
    @FXML private Label lblEdad;
    
    @FXML private ComboBox<Actividad> cbActividades;
    @FXML private ComboBox<Grupo> cbGrupos;
    @FXML private TextField txtMonto;
    @FXML private TextField txtConcepto;
    
    @FXML private TableView<Inscripcion> tablaInscripciones;
    @FXML private TableColumn<Inscripcion, String> colActividad;
    @FXML private TableColumn<Inscripcion, String> colGrupo;
    @FXML private TableColumn<Inscripcion, String> colFechaInscripcion;
    @FXML private TableColumn<Inscripcion, String> colEstadoPago;
    @FXML private TableColumn<Inscripcion, String> colEstado;
    @FXML private TableColumn<Inscripcion, String> colAcciones;

    @FXML private DatePicker dpFechaVencimiento;
    @FXML private TableColumn<Inscripcion, String> colFechaVencimiento;
    
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private ActividadDAO actividadDAO = new ActividadDAO();
    private InscripcionDAO inscripcionDAO = new InscripcionDAO();
    private Alumno alumno;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        configurarComboboxes();
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

        colFechaVencimiento.setCellValueFactory(cellData -> 
            new SimpleStringProperty(
                cellData.getValue().getFechaVencimiento().format(formatter)
            )
        );
        
        colAcciones.setCellValueFactory(cellData -> new SimpleStringProperty(""));
        colAcciones.setCellFactory(column -> new TableCell<>() {
            private final Button btnDarBaja = new Button("Dar de baja");
            private final Button btnExtender = new Button("Extender");
            private final HBox hbox = new HBox(5, btnExtender, btnDarBaja);
            
            {
                btnDarBaja.setOnAction(event -> {
                    Inscripcion inscripcion = getTableView().getItems().get(getIndex());
                    darDeBajaInscripcion(inscripcion);
                });
                
                btnExtender.setOnAction(event -> {
                    Inscripcion inscripcion = getTableView().getItems().get(getIndex());
                    extenderInscripcion(inscripcion);
                });
                
                btnDarBaja.setDisable(false);
                btnDarBaja.getStyleClass().add("button-danger");
                btnExtender.getStyleClass().add("button-secondary");
            }
            
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Inscripcion inscripcion = getTableView().getItems().get(getIndex());
                    btnDarBaja.setDisable(!inscripcion.isActiva());
                    setGraphic(hbox);
                }
            }
        });
    }
    
    private void configurarComboboxes() {
        try {
            cbActividades.getItems().addAll(actividadDAO.listarActivas());
            cbActividades.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    cargarGruposActividad(newVal.getIdActividad());
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void cargarGruposActividad(int idActividad) {
        try {
            cbGrupos.getItems().clear();
            Actividad actividad = actividadDAO.buscarPorId(idActividad);
            if (actividad != null) {
                cbGrupos.getItems().addAll(actividad.getGruposActivos());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    private void inscribir() {
        if (validarCamposInscripcion()) {
            try {
                // Crear la inscripción
                Inscripcion nuevaInscripcion = new Inscripcion(
                    alumno,
                    cbGrupos.getSelectionModel().getSelectedItem(),
                    LocalDate.now(),
                    dpFechaVencimiento.getValue()
                );
                
                // Guardar en base de datos
                int idInscripcion = inscripcionDAO.crearInscripcion(nuevaInscripcion);
                
                // Registrar el pago
                double monto = Double.parseDouble(txtMonto.getText());
                inscripcionDAO.registrarPago(idInscripcion, monto);
                
                // Recargar datos del alumno
                alumno = alumnoDAO.buscarPorId(alumno.getNoExpediente());
                cargarDatosAlumno();
                
                // Limpiar campos
                limpiarCamposInscripcion();
                
                mostrarAlerta("Éxito", "Inscripción realizada", 
                            "El alumno ha sido inscrito exitosamente", 
                            Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "Error al inscribir", 
                            "No se pudo completar la inscripción: " + e.getMessage(), 
                            Alert.AlertType.ERROR);
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "Monto inválido", 
                            "Ingrese un monto válido", 
                            Alert.AlertType.ERROR);
            }
        }
    }
    
    private boolean validarCamposInscripcion() {
        if (dpFechaVencimiento.getValue() == null) {
            mostrarAlerta("Validación", "Seleccione fecha de vencimiento", 
                        "Debe seleccionar una fecha de vencimiento para la inscripción", 
                        Alert.AlertType.WARNING);
            return false;
        }
        
        if (dpFechaVencimiento.getValue().isBefore(LocalDate.now())) {
            mostrarAlerta("Validación", "Fecha inválida", 
                        "La fecha de vencimiento no puede ser anterior a hoy", 
                        Alert.AlertType.WARNING);
            return false;
        }
        
        return true;
    }
    
    private void limpiarCamposInscripcion() {
        cbActividades.getSelectionModel().clearSelection();
        cbGrupos.getItems().clear();
        txtMonto.clear();
        dpFechaVencimiento.setValue(null);
    }
    
    private void darDeBajaInscripcion(Inscripcion inscripcion) {
        if (!confirmarAccion("Confirmar baja", 
                           "¿Está seguro de dar de baja esta inscripción?",
                           "El alumno ya no aparecerá en los listados del grupo")) {
            return;
        }
        
        try {
            if (inscripcionDAO.darDeBajaInscripcion(inscripcion.getIdInscripcion())) {
                mostrarAlerta("Éxito", "Inscripción dada de baja", 
                            "La inscripción ha sido dada de baja exitosamente", 
                            Alert.AlertType.INFORMATION);
                // Recargar datos del alumno
                alumno = alumnoDAO.buscarPorId(alumno.getNoExpediente());
                cargarDatosAlumno();
            } else {
                mostrarAlerta("Error", "Error al dar de baja", 
                            "No se pudo dar de baja la inscripción", 
                            Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al dar de baja", 
                        "No se pudo dar de baja la inscripción: " + e.getMessage(), 
                        Alert.AlertType.ERROR);
        }
    }
    
    private void extenderInscripcion(Inscripcion inscripcion) {
        TextInputDialog dialog = new TextInputDialog(inscripcion.getFechaVencimiento().toString());
        dialog.setTitle("Extender inscripción");
        dialog.setHeaderText("Extender fecha de vencimiento");
        dialog.setContentText("Nueva fecha de vencimiento (YYYY-MM-DD):");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(fechaStr -> {
            try {
                LocalDate nuevaFecha = LocalDate.parse(fechaStr);
                if (nuevaFecha.isBefore(LocalDate.now())) {
                    mostrarAlerta("Error", "Fecha inválida", 
                                "La nueva fecha no puede ser anterior a hoy", 
                                Alert.AlertType.ERROR);
                    return;
                }
                
                if (inscripcionDAO.extenderInscripcion(inscripcion.getIdInscripcion(), nuevaFecha)) {
                    mostrarAlerta("Éxito", "Inscripción extendida", 
                                "La fecha de vencimiento ha sido actualizada", 
                                Alert.AlertType.INFORMATION);
                    // Recargar datos del alumno
                    alumno = alumnoDAO.buscarPorId(alumno.getNoExpediente());
                    cargarDatosAlumno();
                }
            } catch (DateTimeParseException e) {
                mostrarAlerta("Error", "Formato inválido", 
                            "Ingrese una fecha válida en formato YYYY-MM-DD", 
                            Alert.AlertType.ERROR);
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "Error al extender", 
                            "No se pudo extender la inscripción: " + e.getMessage(), 
                            Alert.AlertType.ERROR);
            }
        });
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