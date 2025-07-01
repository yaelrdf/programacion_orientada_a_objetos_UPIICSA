package com.centrocultural.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.centrocultural.database.DatabaseConnection;
import com.centrocultural.models.Actividad;
import com.centrocultural.models.Alumno;
import com.centrocultural.models.Grupo;
import com.centrocultural.models.Inscripcion;
import com.centrocultural.models.Instructor;

public class GrupoDAO implements GenericDAO<Grupo> {
    private DatabaseConnection db = DatabaseConnection.getInstance();
    
    @Override
    public int crear(Grupo grupo) throws SQLException {
        String query = "INSERT INTO grupos (id_actividad, id_instructor, nombre_grupo, salon, edificio, horario) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        
        return db.executeInsert(query,
            grupo.getActividad().getIdActividad(),
            grupo.getInstructor().getNoExpediente(),
            grupo.getNombreGrupo(),
            grupo.getSalon(),
            grupo.getEdificio(),
            grupo.getHorario()
        );
    }
    
    @Override
    public boolean actualizar(Grupo grupo) throws SQLException {
    String query = "UPDATE grupos SET id_actividad = ?, id_instructor = ?, nombre_grupo = ?, " +
                  "horario = ?, activo = ? WHERE id_grupo = ?";
    
    int result = db.executeUpdate(query,
        grupo.getActividad().getIdActividad(),
        grupo.getInstructor().getNoExpediente(),
        grupo.getNombreGrupo(),
        grupo.getHorario(),
        grupo.isActivo(),
        grupo.getIdGrupo()
    );
    
    return result > 0;
    }
    
    @Override
    public boolean eliminar(int id) throws SQLException {
        // No eliminamos físicamente, solo desactivamos
        String query = "UPDATE grupos SET activo = FALSE WHERE id_grupo = ?";
        int result = db.executeUpdate(query, id);
        return result > 0;
    }
    
    @Override
    public Grupo buscarPorId(int id) throws SQLException {
        String query = "SELECT g.*, a.nombre as nombre_actividad, i.nombre_completo as nombre_instructor " +
                      "FROM grupos g " +
                      "INNER JOIN actividades a ON g.id_actividad = a.id_actividad " +
                      "INNER JOIN instructores i ON g.id_instructor = i.no_expediente " +
                      "WHERE g.id_grupo = ?";
        
        ResultSet rs = db.executeQuery(query, id);
        
        if (rs.next()) {
            Grupo grupo = new Grupo();
            grupo.setIdGrupo(rs.getInt("id_grupo"));
            grupo.setNombreGrupo(rs.getString("nombre_grupo"));
            grupo.setSalon(rs.getString("salon"));
            grupo.setEdificio(rs.getString("edificio"));
            grupo.setHorario(rs.getString("horario"));
            grupo.setActivo(rs.getBoolean("activo"));
            
            // Crear actividad básica
            Actividad actividad = new Actividad();
            actividad.setIdActividad(rs.getInt("id_actividad"));
            actividad.setNombre(rs.getString("nombre_actividad"));
            grupo.setActividad(actividad);
            
            // Crear instructor básico
            Instructor instructor = new Instructor();
            instructor.setNoExpediente(rs.getInt("id_instructor"));
            instructor.setNombreCompleto(rs.getString("nombre_instructor"));
            grupo.setInstructor(instructor);
            
            // Cargar alumnos inscritos
            grupo.setInscripciones(obtenerInscripcionesGrupo(id));
            
            return grupo;
        }
        return null;
    }
    
    @Override
    public List<Grupo> listarTodos() throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String query = "SELECT g.*, a.nombre as nombre_actividad, i.nombre_completo as nombre_instructor " +
                      "FROM grupos g " +
                      "INNER JOIN actividades a ON g.id_actividad = a.id_actividad " +
                      "INNER JOIN instructores i ON g.id_instructor = i.no_expediente " +
                      "WHERE g.activo = TRUE " +
                      "ORDER BY g.nombre_grupo";
        
        ResultSet rs = db.executeQuery(query);
        
        while (rs.next()) {
            Grupo grupo = new Grupo();
            grupo.setIdGrupo(rs.getInt("id_grupo"));
            grupo.setNombreGrupo(rs.getString("nombre_grupo"));
            grupo.setSalon(rs.getString("salon"));
            grupo.setEdificio(rs.getString("edificio"));
            grupo.setHorario(rs.getString("horario"));
            grupo.setActivo(rs.getBoolean("activo"));
            
            // Crear actividad básica
            Actividad actividad = new Actividad();
            actividad.setIdActividad(rs.getInt("id_actividad"));
            actividad.setNombre(rs.getString("nombre_actividad"));
            grupo.setActividad(actividad);
            
            // Crear instructor básico
            Instructor instructor = new Instructor();
            instructor.setNoExpediente(rs.getInt("id_instructor"));
            instructor.setNombreCompleto(rs.getString("nombre_instructor"));
            grupo.setInstructor(instructor);
            
            grupos.add(grupo);
        }
        
        return grupos;
    }
    
    @Override
    public List<Grupo> buscar(String criterio) throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String query = "SELECT g.*, a.nombre as nombre_actividad, i.nombre_completo as nombre_instructor " +
                      "FROM grupos g " +
                      "INNER JOIN actividades a ON g.id_actividad = a.id_actividad " +
                      "INNER JOIN instructores i ON g.id_instructor = i.no_expediente " +
                      "WHERE (g.nombre_grupo LIKE ? OR a.nombre LIKE ? OR i.nombre_completo LIKE ?) " +
                      "AND g.activo = TRUE " +
                      "ORDER BY g.nombre_grupo";
        
        String criterioLike = "%" + criterio + "%";
        ResultSet rs = db.executeQuery(query, criterioLike, criterioLike, criterioLike);
        
        while (rs.next()) {
            Grupo grupo = new Grupo();
            grupo.setIdGrupo(rs.getInt("id_grupo"));
            grupo.setNombreGrupo(rs.getString("nombre_grupo"));
            grupo.setSalon(rs.getString("salon"));
            grupo.setEdificio(rs.getString("edificio"));
            grupo.setHorario(rs.getString("horario"));
            grupo.setActivo(rs.getBoolean("activo"));
            
            // Crear actividad básica
            Actividad actividad = new Actividad();
            actividad.setIdActividad(rs.getInt("id_actividad"));
            actividad.setNombre(rs.getString("nombre_actividad"));
            grupo.setActividad(actividad);
            
            // Crear instructor básico
            Instructor instructor = new Instructor();
            instructor.setNoExpediente(rs.getInt("id_instructor"));
            instructor.setNombreCompleto(rs.getString("nombre_instructor"));
            grupo.setInstructor(instructor);
            
            grupos.add(grupo);
        }
        
        return grupos;
    }
    
    // Método para obtener las inscripciones de un grupo
    private List<Inscripcion> obtenerInscripcionesGrupo(int idGrupo) throws SQLException {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String query = "SELECT i.*, a.nombre_completo " +
                      "FROM inscripciones i " +
                      "INNER JOIN alumnos a ON i.no_expediente_alumno = a.no_expediente " +
                      "WHERE i.id_grupo = ? AND i.activa = TRUE";
        
        ResultSet rs = db.executeQuery(query, idGrupo);
        
        while (rs.next()) {
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setIdInscripcion(rs.getInt("id_inscripcion"));
            inscripcion.setFechaInscripcion(rs.getDate("fecha_inscripcion").toLocalDate());
            inscripcion.setActiva(rs.getBoolean("activa"));
            
            // Crear alumno básico
            Alumno alumno = new Alumno();
            alumno.setNoExpediente(rs.getInt("no_expediente_alumno"));
            alumno.setNombreCompleto(rs.getString("nombre_completo"));
            inscripcion.setAlumno(alumno);
            
            inscripciones.add(inscripcion);
        }
        
        return inscripciones;
    }
    
    // Método para listar grupos por actividad
    public List<Grupo> listarPorActividad(int idActividad) throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String query = "SELECT g.*, i.nombre_completo as nombre_instructor " +
                      "FROM grupos g " +
                      "INNER JOIN instructores i ON g.id_instructor = i.no_expediente " +
                      "WHERE g.id_actividad = ? AND g.activo = TRUE " +
                      "ORDER BY g.nombre_grupo";
        
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