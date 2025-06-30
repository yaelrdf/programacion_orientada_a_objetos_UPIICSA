package com.centrocultural.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.centrocultural.dao.AlumnoDAO;
import com.centrocultural.models.Alumno;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AlumnoFormController implements Initializable, BaseController {
    
    @FXML private Label lblTitulo;
    @FXML private TextField txtNombreCompleto;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private Button btnGuardar;
    
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private Alumno alumnoEditar = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar fecha máxima (no futuras)
        dpFechaNacimiento.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isAfter(LocalDate.now()));
            }
        });
    }
    
    @Override
    public void setData(Object data) {
        if (data instanceof Alumno) {
            this.alumnoEditar = (Alumno) data;
            cargarDatosAlumno();
        }
    }
    
    private void cargarDatosAlumno() {
        lblTitulo.setText("Editar Alumno");
        txtNombreCompleto.setText(alumnoEditar.getNombreCompleto());
        dpFechaNacimiento.setValue(alumnoEditar.getFechaNacimiento());
    }
    
    @FXML
    private void guardar() {
        if (!validarFormulario()) {
            return;
        }
        
        try {
            if (alumnoEditar == null) {
                // Crear nuevo alumno
                Alumno nuevoAlumno = new Alumno(
                    txtNombreCompleto.getText().trim(),
                    dpFechaNacimiento.getValue()
                );
                
                int id = alumnoDAO.crear(nuevoAlumno);
                if (id > 0) {
                    mostrarAlerta("Éxito", "Alumno creado", 
                                "El alumno ha sido registrado exitosamente", 
                                Alert.AlertType.INFORMATION);
                    cerrarVentana();
                }
            } else {
                // Actualizar alumno existente
                alumnoEditar.setNombreCompleto(txtNombreCompleto.getText().trim());
                alumnoEditar.setFechaNacimiento(dpFechaNacimiento.getValue());
                
                if (alumnoDAO.actualizar(alumnoEditar)) {
                    mostrarAlerta("Éxito", "Alumno actualizado", 
                                "Los datos del alumno han sido actualizados", 
                                Alert.AlertType.INFORMATION);
                    cerrarVentana();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Error al guardar", 
                        "No se pudo guardar el alumno: " + e.getMessage(), 
                        Alert.AlertType.ERROR);
        }
    }
    
    private boolean validarFormulario() {
        String nombre = txtNombreCompleto.getText().trim();
        
        if (!validarCampoVacio(nombre, "Nombre completo")) {
            return false;
        }
        
        if (dpFechaNacimiento.getValue() == null) {
            mostrarAlerta("Validación", "Campo requerido", 
                        "Debe seleccionar una fecha de nacimiento", 
                        Alert.AlertType.WARNING);
            return false;
        }
        
        // Validar edad mínima (por ejemplo, 3 años)
        LocalDate hoy = LocalDate.now();
        LocalDate fechaNac = dpFechaNacimiento.getValue();
        if (fechaNac.plusYears(3).isAfter(hoy)) {
            mostrarAlerta("Validación", "Edad inválida", 
                        "El alumno debe tener al menos 3 años de edad", 
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