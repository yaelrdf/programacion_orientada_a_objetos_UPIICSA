package com.centrocultural.dao;

import com.centrocultural.database.DatabaseConnection;
import com.centrocultural.models.Inscripcion;
import java.sql.*;
import java.time.LocalDate;

public class InscripcionDAO {
    private DatabaseConnection db = DatabaseConnection.getInstance();
    
    public int crearInscripcion(Inscripcion inscripcion) throws SQLException {
        String query = "INSERT INTO inscripciones (no_expediente_alumno, id_grupo, fecha_inscripcion, fecha_vencimiento) " +
                      "VALUES (?, ?, ?, ?)";
        
        return db.executeInsert(query,
            inscripcion.getAlumno().getNoExpediente(),
            inscripcion.getGrupo().getIdGrupo(),
            Date.valueOf(inscripcion.getFechaInscripcion()),
            Date.valueOf(inscripcion.getFechaVencimiento())
        );
    }
    
    public boolean registrarPago(int idInscripcion, double monto) throws SQLException {
        String query = "INSERT INTO pagos (id_inscripcion, monto, fecha_pago) " +
                      "VALUES (?, ?, CURRENT_DATE)";
        
        int result = db.executeUpdate(query,
            idInscripcion,
            monto
        );
        
        return result > 0;
    }
    
    public boolean darDeBajaInscripcion(int idInscripcion) throws SQLException {
        String query = "UPDATE inscripciones SET activa = FALSE WHERE id_inscripcion = ?";
        int result = db.executeUpdate(query, idInscripcion);
        return result > 0;
    }
    
    // Nuevo método para extender la inscripción
    public boolean extenderInscripcion(int idInscripcion, LocalDate nuevaFechaVencimiento) throws SQLException {
        String query = "UPDATE inscripciones SET fecha_vencimiento = ? WHERE id_inscripcion = ?";
        int result = db.executeUpdate(query, 
            Date.valueOf(nuevaFechaVencimiento),
            idInscripcion
        );
        return result > 0;
    }
}