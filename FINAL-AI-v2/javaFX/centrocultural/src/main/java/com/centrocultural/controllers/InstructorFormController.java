package com.centrocultural.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.centrocultural.dao.ActividadDAO;
import com.centrocultural.dao.InstructorDAO;
import com.centrocultural.models.Actividad;
import com.centrocultural.models.Instructor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class InstructorFormController implements Initializable, BaseController {
    
    @FXML private Label lblTitulo;
    @FXML private TextField txtNombreCompleto;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private Button btnAgregarActividad;
    @FXML private Button btnRemoverActividad;
    @FXML private ListView<Actividad> lstActividades;
    @FXML private Button btnGuardar;
    
    private InstructorDAO instructorDAO = new InstructorDAO();
    private ActividadDAO actividadDAO = new ActividadDAO();
    private Instructor instructorEditar = null;
    private ObservableList<Actividad> actividadesAsignadas = FXCollections.observableArrayList();
    private ObservableList<Actividad> todasLasActividades = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar fecha máxima (no futuras)
        /*dpFechaNacimiento.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isAfter(LocalDate.now()));
            }
        });*/
        
        // Configurar ListView
        lstActividades.setItems(actividadesAsignadas);
        
        // Cargar todas las actividades disponibles
        try {
            todasLasActividades.addAll(actividadDAO.listarTodos());
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al cargar actividades", 
                        "No se pudieron cargar las actividades: " + e.getMessage(), 
                        Alert.AlertType.ERROR);
        }
        
        // Configurar botones de actividades
        btnAgregarActividad.setOnAction(e -> agregarActividad());
        btnRemoverActividad.setOnAction(e -> removerActividad());
    }
    
    @Override
    public void setData(Object data) {
        if (data instanceof Instructor) {
            this.instructorEditar = (Instructor) data;
            cargarDatosInstructor();
        }
    }
    
    private void cargarDatosInstructor() {
        lblTitulo.setText("Editar Instructor");
        txtNombreCompleto.setText(instructorEditar.getNombreCompleto());
        dpFechaNacimiento.setValue(instructorEditar.getFechaNacimiento());
        
        // Cargar actividades asignadas
        actividadesAsignadas.clear();
        actividadesAsignadas.addAll(instructorEditar.getActividadesAutorizadas());
    }
    
    private void agregarActividad() {
        // Implementar lógica para seleccionar actividad a agregar
        // Puede ser un diálogo de selección o una ventana modal
        PantallaPrincipalController.abrirVentanaModal(
            "Seleccionar Actividad",
            "/fxml/SeleccionarActividadDialog.fxml",
            todasLasActividades
        );
        
        // Aquí deberías obtener la actividad seleccionada y agregarla
        // actividadesAsignadas.add(actividadSeleccionada);
    }
    
    private void removerActividad() {
        Actividad seleccionada = lstActividades.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            actividadesAsignadas.remove(seleccionada);
        } else {
            mostrarAlerta("Información", "Sin selección", 
                        "Seleccione una actividad para remover", 
                        Alert.AlertType.INFORMATION);
        }
    }
    
    @FXML
    private void guardar() {
        if (!validarFormulario()) {
            return;
        }
        
        try {
            String nombreCompleto = txtNombreCompleto.getText().trim();
            LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
            
            if (instructorEditar == null) {
                // Crear nuevo instructor
                Instructor nuevoInstructor = new Instructor(
                    nombreCompleto,
                    fechaNacimiento
                );
                
                // Asignar actividades
                nuevoInstructor.setActividadesAutorizadas(actividadesAsignadas);
                
                int id = instructorDAO.crear(nuevoInstructor);
                if (id > 0) {
                    mostrarAlerta("Éxito", "Instructor creado", 
                                "El instructor ha sido registrado exitosamente", 
                                Alert.AlertType.INFORMATION);
                    cerrarVentana();
                }
            } else {
                // Actualizar instructor existente
                instructorEditar.setNombreCompleto(nombreCompleto);
                instructorEditar.setFechaNacimiento(fechaNacimiento);
                instructorEditar.setActividadesAutorizadas(actividadesAsignadas);
                
                if (instructorDAO.actualizar(instructorEditar)) {
                    mostrarAlerta("Éxito", "Instructor actualizado", 
                                "Los datos del instructor han sido actualizados", 
                                Alert.AlertType.INFORMATION);
                    cerrarVentana();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al guardar", 
                        "No se pudo guardar el instructor: " + e.getMessage(), 
                        Alert.AlertType.ERROR);
        }
    }
    
    // ... (resto de los métodos permanece igual)
    
    private boolean validarFormulario() {
        String nombreCompleto = txtNombreCompleto.getText().trim();
        
        if (nombreCompleto.isEmpty()) {
            mostrarAlerta("Validación", "Campo requerido", 
                         "El nombre completo es obligatorio", 
                         Alert.AlertType.WARNING);
            return false;
        }
        
        if (dpFechaNacimiento.getValue() == null) {
            mostrarAlerta("Validación", "Campo requerido", 
                         "Debe seleccionar una fecha de nacimiento", 
                         Alert.AlertType.WARNING);
            return false;
        }
        
        // Validar edad mínima (por ejemplo, 18 años)
        LocalDate hoy = LocalDate.now();
        LocalDate fechaNac = dpFechaNacimiento.getValue();
        if (fechaNac.plusYears(18).isAfter(hoy)) {
            mostrarAlerta("Validación", "Edad inválida", 
                        "El instructor debe tener al menos 18 años de edad", 
                        Alert.AlertType.WARNING);
            return false;
        }
        
        return true;
    }
    
    @FXML
    private void cancelar() {
        cerrarVentana();
    }
    
    private void cerrarVentana() {
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void cargarDatos() {
        // No se usa en este formulario
    }
}