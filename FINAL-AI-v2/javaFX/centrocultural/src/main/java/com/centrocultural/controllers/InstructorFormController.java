package com.centrocultural.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.centrocultural.dao.ActividadDAO;
import com.centrocultural.dao.InstructorDAO;
import com.centrocultural.models.Actividad;
import com.centrocultural.models.Instructor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
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
    try {
        // Filtrar actividades que no están ya asignadas
        ObservableList<Actividad> disponibles = FXCollections.observableArrayList();
        for (Actividad a : todasLasActividades) {
            if (!actividadesAsignadas.contains(a)) {
                disponibles.add(a);
            }
        }

        // Crear y configurar el diálogo
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Seleccionar Actividad");
        dialog.setHeaderText("Seleccione una actividad para autorizar");

        // Cargar el FXML
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/SeleccionarActividadDialog.fxml"));
        dialog.getDialogPane().setContent(loader.load());

        // Obtener el controlador y configurarlo
        SeleccionarActividadDialogController controller = loader.getController();
        controller.setActividadesDisponibles(disponibles);

        // Agregar botones
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Mostrar diálogo y procesar resultado
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Actividad seleccionada = controller.getActividadSeleccionada();
            if (seleccionada != null) {
                actividadesAsignadas.add(seleccionada);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        mostrarAlerta("Error", "Error al cargar actividades", 
                    "No se pudo cargar el diálogo de selección: " + e.getMessage(), 
                    Alert.AlertType.ERROR);
    }
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
            
            int id = instructorDAO.crear(nuevoInstructor);
            if (id > 0) {
                // Guardar actividades autorizadas
                for (Actividad actividad : actividadesAsignadas) {
                    instructorDAO.autorizarActividad(id, actividad.getIdActividad());
                }
                
                mostrarAlerta("Éxito", "Instructor creado", 
                            "El instructor ha sido registrado exitosamente", 
                            Alert.AlertType.INFORMATION);
                cerrarVentana();
            }
        } else {
            // Actualizar instructor existente
            instructorEditar.setNombreCompleto(nombreCompleto);
            instructorEditar.setFechaNacimiento(fechaNacimiento);
            
            if (instructorDAO.actualizar(instructorEditar)) {
                // Sincronizar actividades autorizadas
                List<Actividad> actividadesActuales = instructorEditar.getActividadesAutorizadas();
                
                // Agregar nuevas autorizaciones
                for (Actividad nueva : actividadesAsignadas) {
                    if (!actividadesActuales.contains(nueva)) {
                        instructorDAO.autorizarActividad(instructorEditar.getNoExpediente(), nueva.getIdActividad());
                    }
                }
                
                // Eliminar autorizaciones removidas
                for (Actividad existente : actividadesActuales) {
                    if (!actividadesAsignadas.contains(existente)) {
                        instructorDAO.desautorizarActividad(instructorEditar.getNoExpediente(), existente.getIdActividad());
                    }
                }
                
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