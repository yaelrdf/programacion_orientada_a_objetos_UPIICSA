package com.centrocultural.dao;

import com.centrocultural.database.DatabaseConnection;
import com.centrocultural.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadDAO implements GenericDAO<Actividad> {
    private DatabaseConnection db = DatabaseConnection.getInstance();
    
    @Override
    public int crear(Actividad actividad) throws SQLException {
        String query = "INSERT INTO actividades (nombre, clasificacion, area_asignada, activa) " +
                      "VALUES (?, ?, ?, ?)";
        
        return db.executeInsert(query,
            actividad.getNombre(),
            actividad.getClasificacion(),
            actividad.getAreaAsignada(),
            actividad.isActiva()
        );
    }
    
    @Override
    public boolean actualizar(Actividad actividad) throws SQLException {
        String query = "UPDATE actividades SET nombre = ?, clasificacion = ?, " +
                      "area_asignada = ?, activa = ? WHERE id_actividad = ?";
        
        int result = db.executeUpdate(query,
            actividad.getNombre(),
            actividad.getClasificacion(),
            actividad.getAreaAsignada(),
            actividad.isActiva(),
            actividad.getIdActividad()
        );
        
        return result > 0;
    }
    
    @Override
    public boolean eliminar(int id) throws SQLException {
        // No eliminar físicamente, solo desactivar
        String query = "UPDATE actividades SET activa = FALSE WHERE id_actividad = ?";
        int result = db.executeUpdate(query, id);
        return result > 0;
    }
    
    @Override
    public Actividad buscarPorId(int id) throws SQLException {
        String query = "SELECT * FROM actividades WHERE id_actividad = ?";
        ResultSet rs = db.executeQuery(query, id);
        
        if (rs.next()) {
            Actividad actividad = extraerActividad(rs);
            // Cargar instructores autorizados
            actividad.setInstructoresAutorizados(obtenerInstructoresActividad(id));
            // Cargar grupos
            actividad.setGrupos(obtenerGruposActividad(id));
            return actividad;
        }
        return null;
    }
    
    @Override
    public List<Actividad> listarTodos() throws SQLException {
        List<Actividad> actividades = new ArrayList<>();
        String query = "SELECT * FROM actividades ORDER BY nombre";
        ResultSet rs = db.executeQuery(query);
        
        while (rs.next()) {
            actividades.add(extraerActividad(rs));
        }
        
        return actividades;
    }
    
    @Override
    public List<Actividad> buscar(String criterio) throws SQLException {
        List<Actividad> actividades = new ArrayList<>();
        String query = "SELECT * FROM actividades WHERE nombre LIKE ? " +
                      "OR clasificacion LIKE ? OR area_asignada LIKE ? " +
                      "ORDER BY nombre";
        String criterioLike = "%" + criterio + "%";
        
        ResultSet rs = db.executeQuery(query, criterioLike, criterioLike, criterioLike);
        
        while (rs.next()) {
            actividades.add(extraerActividad(rs));
        }
        
        return actividades;
    }
    
    // Listar solo actividades activas
    public List<Actividad> listarActivas() throws SQLException {
    List<Actividad> actividades = new ArrayList<>();
    String query = "SELECT * FROM actividades WHERE activa = TRUE ORDER BY nombre";
    ResultSet rs = db.executeQuery(query);
    
    while (rs.next()) {
        actividades.add(extraerActividad(rs));
    }
    
    return actividades;
}
    
    // Listar actividades por clasificación
    public List<Actividad> listarPorClasificacion(String clasificacion) throws SQLException {
        List<Actividad> actividades = new ArrayList<>();
        String query = "SELECT * FROM actividades WHERE clasificacion = ? AND activa = TRUE ORDER BY nombre";
        ResultSet rs = db.executeQuery(query, clasificacion);
        
        while (rs.next()) {
            actividades.add(extraerActividad(rs));
        }
        
        return actividades;
    }
    
    // Método auxiliar para extraer actividad
    private Actividad extraerActividad(ResultSet rs) throws SQLException {
        Actividad actividad = new Actividad();
        actividad.setIdActividad(rs.getInt("id_actividad"));
        actividad.setNombre(rs.getString("nombre"));
        actividad.setClasificacion(rs.getString("clasificacion"));
        actividad.setAreaAsignada(rs.getString("area_asignada"));
        actividad.setActiva(rs.getBoolean("activa"));
        return actividad;
    }
    
    // Obtener instructores autorizados para la actividad
    private List<Instructor> obtenerInstructoresActividad(int idActividad) throws SQLException {
        List<Instructor> instructores = new ArrayList<>();
        String query = "SELECT i.* FROM instructores i " +
                      "INNER JOIN instructor_actividades ia ON i.no_expediente = ia.no_expediente_instructor " +
                      "WHERE ia.id_actividad = ?";
        
        ResultSet rs = db.executeQuery(query, idActividad);
        
        while (rs.next()) {
            Instructor instructor = new Instructor();
            instructor.setNoExpediente(rs.getInt("no_expediente"));
            instructor.setNombreCompleto(rs.getString("nombre_completo"));
            instructor.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
            instructor.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
            instructores.add(instructor);
        }
        
        return instructores;
    }
    
    // Obtener grupos de la actividad
    private List<Grupo> obtenerGruposActividad(int idActividad) throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String query = "SELECT g.*, i.nombre_completo as nombre_instructor " +
                      "FROM grupos g " +
                      "INNER JOIN instructores i ON g.id_instructor = i.no_expediente " +
                      "WHERE g.id_actividad = ? AND g.activo = TRUE";
        
        ResultSet rs = db.executeQuery(query, idActividad);
        
        while (rs.next()) {
            Grupo grupo = new Grupo();
            grupo.setIdGrupo(rs.getInt("id_grupo"));
            grupo.setNombreGrupo(rs.getString("nombre_grupo"));
            grupo.setSalon(rs.getString("salon"));
            grupo.setEdificio(rs.getString("edificio"));
            grupo.setHorario(rs.getString("horario"));
            grupo.setActivo(rs.getBoolean("activo"));
            
            // Crear instructor básico
            Instructor instructor = new Instructor();
            instructor.setNoExpediente(rs.getInt("id_instructor"));
            instructor.setNombreCompleto(rs.getString("nombre_instructor"));
            grupo.setInstructor(instructor);
            
            grupos.add(grupo);
        }
        
        return grupos;
    }

    
}