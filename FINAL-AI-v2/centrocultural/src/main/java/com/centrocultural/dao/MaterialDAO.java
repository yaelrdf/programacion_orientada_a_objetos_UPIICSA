package com.centrocultural.dao;

import com.centrocultural.database.DatabaseConnection;
import com.centrocultural.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO implements GenericDAO<Material> {
    private DatabaseConnection db = DatabaseConnection.getInstance();
    
    @Override
    public int crear(Material material) throws SQLException {
        String query = "INSERT INTO materiales (nombre, area_almacenamiento, condicion, disponible) " +
                      "VALUES (?, ?, ?, ?)";
        
        return db.executeInsert(query,
            material.getNombre(),
            material.getAreaAlmacenamiento(),
            material.getCondicion(),
            material.isDisponible()
        );
    }
    
    @Override
    public boolean actualizar(Material material) throws SQLException {
        String query = "UPDATE materiales SET nombre = ?, area_almacenamiento = ?, " +
                      "condicion = ?, disponible = ? WHERE id_material = ?";
        
        int result = db.executeUpdate(query,
            material.getNombre(),
            material.getAreaAlmacenamiento(),
            material.getCondicion(),
            material.isDisponible(),
            material.getIdMaterial()
        );
        
        return result > 0;
    }
    
    @Override
    public boolean eliminar(int id) throws SQLException {
        // Primero eliminar las asignaciones a grupos
        String deleteAsignaciones = "DELETE FROM material_grupo WHERE id_material = ?";
        db.executeUpdate(deleteAsignaciones, id);
        
        // Luego eliminar el material
        String query = "DELETE FROM materiales WHERE id_material = ?";
        int result = db.executeUpdate(query, id);
        return result > 0;
    }
    
    @Override
    public Material buscarPorId(int id) throws SQLException {
        String query = "SELECT * FROM materiales WHERE id_material = ?";
        ResultSet rs = db.executeQuery(query, id);
        
        if (rs.next()) {
            Material material = extraerMaterial(rs);
            // Cargar grupos asignados
            material.setGruposAsignados(obtenerGruposMaterial(id));
            return material;
        }
        return null;
    }
    
    @Override
    public List<Material> listarTodos() throws SQLException {
        List<Material> materiales = new ArrayList<>();
        String query = "SELECT * FROM materiales ORDER BY nombre";
        ResultSet rs = db.executeQuery(query);
        
        while (rs.next()) {
            materiales.add(extraerMaterial(rs));
        }
        
        return materiales;
    }
    
    @Override
    public List<Material> buscar(String criterio) throws SQLException {
        List<Material> materiales = new ArrayList<>();
        String query = "SELECT * FROM materiales WHERE nombre LIKE ? " +
                      "OR area_almacenamiento LIKE ? OR condicion LIKE ? " +
                      "ORDER BY nombre";
        String criterioLike = "%" + criterio + "%";
        
        ResultSet rs = db.executeQuery(query, criterioLike, criterioLike, criterioLike);
        
        while (rs.next()) {
            materiales.add(extraerMaterial(rs));
        }
        
        return materiales;
    }
    
    // Listar materiales disponibles
    public List<Material> listarDisponibles() throws SQLException {
        List<Material> materiales = new ArrayList<>();
        String query = "SELECT * FROM materiales WHERE disponible = TRUE ORDER BY nombre";
        ResultSet rs = db.executeQuery(query);
        
        while (rs.next()) {
            materiales.add(extraerMaterial(rs));
        }
        
        return materiales;
    }
    
    // Listar materiales por condición
    public List<Material> listarPorCondicion(String condicion) throws SQLException {
        List<Material> materiales = new ArrayList<>();
        String query = "SELECT * FROM materiales WHERE condicion = ? ORDER BY nombre";
        ResultSet rs = db.executeQuery(query, condicion);
        
        while (rs.next()) {
            materiales.add(extraerMaterial(rs));
        }
        
        return materiales;
    }
    
    // Asignar material a grupo
    public boolean asignarAGrupo(int idMaterial, int idGrupo) throws SQLException {
        String query = "INSERT INTO material_grupo (id_material, id_grupo) VALUES (?, ?)";
        
        try {
            int result = db.executeUpdate(query, idMaterial, idGrupo);
            
            if (result > 0) {
                // Actualizar disponibilidad
                actualizarDisponibilidad(idMaterial);
            }
            
            return result > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry
                return true; // Ya estaba asignado
            }
            throw e;
        }
    }
    
    // Desasignar material de grupo
    public boolean desasignarDeGrupo(int idMaterial, int idGrupo) throws SQLException {
        String query = "DELETE FROM material_grupo WHERE id_material = ? AND id_grupo = ?";
        int result = db.executeUpdate(query, idMaterial, idGrupo);
        
        if (result > 0) {
            // Actualizar disponibilidad
            actualizarDisponibilidad(idMaterial);
        }
        
        return result > 0;
    }
    
    // Listar materiales asignados a un grupo
    public List<Material> listarPorGrupo(int idGrupo) throws SQLException {
        List<Material> materiales = new ArrayList<>();
        String query = "SELECT m.* FROM materiales m " +
                      "INNER JOIN material_grupo mg ON m.id_material = mg.id_material " +
                      "WHERE mg.id_grupo = ? ORDER BY m.nombre";
        
        ResultSet rs = db.executeQuery(query, idGrupo);
        
        while (rs.next()) {
            materiales.add(extraerMaterial(rs));
        }
        
        return materiales;
    }
    
    // Método auxiliar para extraer material
    private Material extraerMaterial(ResultSet rs) throws SQLException {
        Material material = new Material();
        material.setIdMaterial(rs.getInt("id_material"));
        material.setNombre(rs.getString("nombre"));
        material.setAreaAlmacenamiento(rs.getString("area_almacenamiento"));
        material.setCondicion(rs.getString("condicion"));
        material.setDisponible(rs.getBoolean("disponible"));
        return material;
    }
    
    // Obtener grupos asignados al material
    private List<Grupo> obtenerGruposMaterial(int idMaterial) throws SQLException {
        List<Grupo> grupos = new ArrayList<>();
        String query = "SELECT g.*, a.nombre as nombre_actividad " +
                      "FROM grupos g " +
                      "INNER JOIN material_grupo mg ON g.id_grupo = mg.id_grupo " +
                      "INNER JOIN actividades a ON g.id_actividad = a.id_actividad " +
                      "WHERE mg.id_material = ? AND g.activo = TRUE";
        
        ResultSet rs = db.executeQuery(query, idMaterial);
        
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
            actividad.setNombre(rs.getString("nombre_actividad"));
            grupo.setActividad(actividad);
            
            grupos.add(grupo);
        }
        
        return grupos;
    }
    
    // Actualizar disponibilidad según asignaciones
    private void actualizarDisponibilidad(int idMaterial) throws SQLException {
        String query = "SELECT COUNT(*) FROM material_grupo WHERE id_material = ?";
        ResultSet rs = db.executeQuery(query, idMaterial);
        
        if (rs.next()) {
            boolean disponible = rs.getInt(1) == 0;
            String updateQuery = "UPDATE materiales SET disponible = ? WHERE id_material = ?";
            db.executeUpdate(updateQuery, disponible, idMaterial);
        }
    }
}