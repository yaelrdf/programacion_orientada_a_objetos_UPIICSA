package com.centrocultural.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import com.centrocultural.MainApp;
import com.centrocultural.dao.*;
import com.centrocultural.models.*;
import java.sql.SQLException;
import java.util.List;

public class GrupoFormController implements BaseController {
    
    @FXML private Label lblTitulo;
    @FXML private TextField txtNombre;
    @FXML private ComboBox<Actividad> cbActividades;
    @FXML private ComboBox<Instructor> cbInstructores;
    @FXML private TextField txtHorario;
    
    private GrupoDAO grupoDAO = new GrupoDAO();
    private ActividadDAO actividadDAO = new ActividadDAO();
    private InstructorDAO instructorDAO = new InstructorDAO();
    private Grupo grupoEdicion;
    
    @FXML
    private void initialize() {
        cargarDatos();
    }
    
    @Override
    public void setData(Object data) {
        if (data instanceof Grupo) {
            this.grupoEdicion = (Grupo) data;
            lblTitulo.setText("Editar Grupo");
            cargarDatosExistente();
        }
    }
    
    @FXML
    private void guardar() {
        if (validarCampos()) {
            try {
                Grupo grupo = new Grupo();
                grupo.setNombreGrupo(txtNombre.getText());
                grupo.setActividad(cbActividades.getSelectionModel().getSelectedItem());
                grupo.setInstructor(cbInstructores.getSelectionModel().getSelectedItem());
                grupo.setHorario(txtHorario.getText());
                
                if (grupoEdicion != null) {
                    grupo.setIdGrupo(grupoEdicion.getIdGrupo());
                    grupoDAO.actualizar(grupo);
                } else {
                    grupoDAO.crear(grupo);
                }
                
                cerrarVentana();
            } catch (SQLException e) {
                e.printStackTrace();
                MainApp.getInstance().mostrarAlerta("Error", 
                    "Error al guardar", 
                    "No se pudo guardar el grupo: " + e.getMessage(), 
                    Alert.AlertType.ERROR);
            }
        }
    }
    
    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || 
            cbActividades.getSelectionModel().isEmpty() || 
            cbInstructores.getSelectionModel().isEmpty()) {
            
            MainApp.getInstance().mostrarAlerta("Validación", 
                "Campos requeridos", 
                "Nombre, Actividad e Instructor son campos obligatorios", 
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
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }
    
    private void configurarComboBoxActividades() {
        cbActividades.setCellFactory(param -> new ListCell<Actividad>() {
            @Override
            protected void updateItem(Actividad item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre() + " (" + item.getClasificacion() + ")");
            }
        });
        
        cbActividades.setButtonCell(new ListCell<Actividad>() {
            @Override
            protected void updateItem(Actividad item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre() + " (" + item.getClasificacion() + ")");
            }
        });
    }

    private void configurarComboBoxInstructores() {
        cbInstructores.setCellFactory(param -> new ListCell<Instructor>() {
            @Override
            protected void updateItem(Instructor item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombreCompleto() + " (#" + item.getNoExpediente() + ")");
            }
        });
        
        cbInstructores.setButtonCell(new ListCell<Instructor>() {
            @Override
            protected void updateItem(Instructor item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombreCompleto() + " (#" + item.getNoExpediente() + ")");
            }
        });
    }

    @Override
    public void cargarDatos() {
        try {
            // Cargar actividades activas
            List<Actividad> actividades = actividadDAO.listarActivas();
            cbActividades.getItems().setAll(actividades);
            configurarComboBoxActividades();
            
            // Cargar instructores activos
            List<Instructor> instructores = instructorDAO.listarActivos();
            cbInstructores.getItems().setAll(instructores);
            configurarComboBoxInstructores();
            
        } catch (SQLException e) {
            e.printStackTrace();
            MainApp.getInstance().mostrarAlerta("Error", 
                "Error al cargar datos", 
                "No se pudieron cargar actividades o instructores: " + e.getMessage(), 
                Alert.AlertType.ERROR);
        }
    }

    private void cargarDatosExistente() {
        if (grupoEdicion != null) {
            txtNombre.setText(grupoEdicion.getNombreGrupo());
            txtHorario.setText(grupoEdicion.getHorario());
            
            // Seleccionar la actividad correspondiente
            for (Actividad actividad : cbActividades.getItems()) {
                if (actividad.getIdActividad() == grupoEdicion.getActividad().getIdActividad()) {
                    cbActividades.getSelectionModel().select(actividad);
                    break;
                }
            }
            
            // Seleccionar el instructor correspondiente
            for (Instructor instructor : cbInstructores.getItems()) {
                if (instructor.getNoExpediente() == grupoEdicion.getInstructor().getNoExpediente()) {
                    cbInstructores.getSelectionModel().select(instructor);
                    break;
                }
            }
        }
    }
}