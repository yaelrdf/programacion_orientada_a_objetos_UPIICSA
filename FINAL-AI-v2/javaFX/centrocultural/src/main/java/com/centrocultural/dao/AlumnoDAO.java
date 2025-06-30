package com.centrocultural.dao;

import com.centrocultural.database.DatabaseConnection;
import com.centrocultural.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO implements GenericDAO<Alumno> {
    private DatabaseConnection db = DatabaseConnection.getInstance();
    
    @Override
    public int crear(Alumno alumno) throws SQLException {
        String query = "INSERT INTO alumnos (nombre_completo, fecha_nacimiento) VALUES (?, ?)";
        
        return db.executeInsert(query,
            alumno.getNombreCompleto(),
            Date.valueOf(alumno.getFechaNacimiento())
        );
    }
    
    @Override
    public boolean actualizar(Alumno alumno) throws SQLException {
        String query = "UPDATE alumnos SET nombre_completo = ?, fecha_nacimiento = ? " +
                      "WHERE no_expediente = ?";
        
        int result = db.executeUpdate(query,
            alumno.getNombreCompleto(),
            Date.valueOf(alumno.getFechaNacimiento()),
            alumno.getNoExpediente()
        );
        
        return result > 0;
    }
    
    @Override
    public boolean eliminar(int id) throws SQLException {
        // Primero verificar si tiene inscripciones
        String checkQuery = "SELECT COUNT(*) FROM inscripciones WHERE no_expediente_alumno = ?";
        ResultSet rs = db.executeQuery(checkQuery, id);
        
        if (rs.next() && rs.getInt(1) > 0) {
            // Si tiene inscripciones, no eliminar físicamente
            return false;
        }
        
        String query = "DELETE FROM alumnos WHERE no_expediente = ?";
        int result = db.executeUpdate(query, id);
        return result > 0;
    }
    
    @Override
    public Alumno buscarPorId(int id) throws SQLException {
        String query = "SELECT * FROM alumnos WHERE no_expediente = ?";
        ResultSet rs = db.executeQuery(query, id);
        
        if (rs.next()) {
            Alumno alumno = extraerAlumno(rs);
            // Cargar inscripciones
            alumno.setInscripciones(obtenerInscripcionesAlumno(alumno.getNoExpediente()));
            return alumno;
        }
        return null;
    }
    
    @Override
    public List<Alumno> listarTodos() throws SQLException {
        List<Alumno> alumnos = new ArrayList<>();
        String query = "SELECT * FROM alumnos ORDER BY nombre_completo";
        ResultSet rs = db.executeQuery(query);
        
        while (rs.next()) {
            Alumno alumno = extraerAlumno(rs);
            // Para la lista general, no cargamos todas las inscripciones por rendimiento
            alumnos.add(alumno);
        }
        
        return alumnos;
    }
    
    @Override
    public List<Alumno> buscar(String criterio) throws SQLException {
        List<Alumno> alumnos = new ArrayList<>();
        String query = "SELECT * FROM alumnos WHERE nombre_completo LIKE ? " +
                      "OR CAST(no_expediente AS CHAR) LIKE ? ORDER BY nombre_completo";
        String criterioLike = "%" + criterio + "%";
        
        ResultSet rs = db.executeQuery(query, criterioLike, criterioLike);
        
        while (rs.next()) {
            alumnos.add(extraerAlumno(rs));
        }
        
        return alumnos;
    }
    
    // Método para obtener alumnos con inscripciones activas
    public List<Alumno> listarAlumnosActivos() throws SQLException {
        List<Alumno> alumnos = new ArrayList<>();
        String query = "SELECT DISTINCT a.* FROM alumnos a " +
                      "INNER JOIN inscripciones i ON a.no_expediente = i.no_expediente_alumno " +
                      "WHERE i.activa = TRUE ORDER BY a.nombre_completo";
        
        ResultSet rs = db.executeQuery(query);
        
        while (rs.next()) {
            alumnos.add(extraerAlumno(rs));
        }
        
        return alumnos;
    }
    
    // Método para obtener alumnos inscritos en un grupo específico
    public List<Alumno> listarAlumnosPorGrupo(int idGrupo) throws SQLException {
        List<Alumno> alumnos = new ArrayList<>();
        String query = "SELECT a.* FROM alumnos a " +
                      "INNER JOIN inscripciones i ON a.no_expediente = i.no_expediente_alumno " +
                      "INNER JOIN pagos p ON i.id_inscripcion = p.id_inscripcion " +
                      "WHERE i.id_grupo = ? AND i.activa = TRUE " +
                      "ORDER BY a.nombre_completo";
        
        ResultSet rs = db.executeQuery(query, idGrupo);
        
        while (rs.next()) {
            alumnos.add(extraerAlumno(rs));
        }
        
        return alumnos;
    }
    
    // Método auxiliar para extraer un alumno del ResultSet
    private Alumno extraerAlumno(ResultSet rs) throws SQLException {
        Alumno alumno = new Alumno();
        alumno.setNoExpediente(rs.getInt("no_expediente"));
        alumno.setNombreCompleto(rs.getString("nombre_completo"));
        alumno.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
        alumno.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        return alumno;
    }
    
    // Método para obtener las inscripciones de un alumno
    private List<Inscripcion> obtenerInscripcionesAlumno(int noExpediente) throws SQLException {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String query = "SELECT i.*, g.nombre_grupo, g.id_actividad, a.nombre as nombre_actividad " +
                      "FROM inscripciones i " +
                      "INNER JOIN grupos g ON i.id_grupo = g.id_grupo " +
                      "INNER JOIN actividades a ON g.id_actividad = a.id_actividad " +
                      "WHERE i.no_expediente_alumno = ? " +
                      "ORDER BY i.fecha_inscripcion DESC";
        
        ResultSet rs = db.executeQuery(query, noExpediente);
        
        while (rs.next()) {
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setIdInscripcion(rs.getInt("id_inscripcion"));
            inscripcion.setFechaInscripcion(rs.getDate("fecha_inscripcion").toLocalDate());
            inscripcion.setActiva(rs.getBoolean("activa"));
            
            // Crear grupo básico con información de actividad
            Grupo grupo = new Grupo();
            grupo.setIdGrupo(rs.getInt("id_grupo"));
            grupo.setNombreGrupo(rs.getString("nombre_grupo"));
            
            Actividad actividad = new Actividad();
            actividad.setIdActividad(rs.getInt("id_actividad"));
            actividad.setNombre(rs.getString("nombre_actividad"));
            grupo.setActividad(actividad);
            
            inscripcion.setGrupo(grupo);
            
            // Verificar si tiene pagos
            inscripcion.setPagos(obtenerPagosInscripcion(inscripcion.getIdInscripcion()));
            
            inscripciones.add(inscripcion);
        }
        
        return inscripciones;
    }
    
    // Método para obtener los pagos de una inscripción
    private List<Pago> obtenerPagosInscripcion(int idInscripcion) throws SQLException {
        List<Pago> pagos = new ArrayList<>();
        String query = "SELECT * FROM pagos WHERE id_inscripcion = ? ORDER BY fecha_pago DESC";
        
        ResultSet rs = db.executeQuery(query, idInscripcion);
        
        while (rs.next()) {
            Pago pago = new Pago();
            pago.setIdPago(rs.getInt("id_pago"));
            pago.setMonto(rs.getBigDecimal("monto"));
            pago.setFechaPago(rs.getDate("fecha_pago").toLocalDate());
            pago.setConcepto(rs.getString("concepto"));
            pagos.add(pago);
        }
        
        return pagos;
    }
}