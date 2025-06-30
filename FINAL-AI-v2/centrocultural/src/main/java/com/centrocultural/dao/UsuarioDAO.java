package com.centrocultural.dao;

import com.centrocultural.database.DatabaseConnection;
import com.centrocultural.models.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements GenericDAO<Usuario> {
    private DatabaseConnection db = DatabaseConnection.getInstance();
    
    @Override
    public int crear(Usuario usuario) throws SQLException {
        String query = "INSERT INTO usuarios (nombre_usuario, contrasena, nombre_empleado, area_asignada) " +
                      "VALUES (?, SHA2(?, 256), ?, ?)";
        
        return db.executeInsert(query, 
            usuario.getNombreUsuario(),
            usuario.getContrasena(),
            usuario.getNombreEmpleado(),
            usuario.getAreaAsignada()
        );
    }
    
    @Override
    public boolean actualizar(Usuario usuario) throws SQLException {
        String query;
        int result;
        
        if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
            query = "UPDATE usuarios SET nombre_usuario = ?, contrasena = SHA2(?, 256), " +
                   "nombre_empleado = ?, area_asignada = ? WHERE id_usuario = ?";
            result = db.executeUpdate(query,
                usuario.getNombreUsuario(),
                usuario.getContrasena(),
                usuario.getNombreEmpleado(),
                usuario.getAreaAsignada(),
                usuario.getIdUsuario()
            );
        } else {
            query = "UPDATE usuarios SET nombre_usuario = ?, nombre_empleado = ?, " +
                   "area_asignada = ? WHERE id_usuario = ?";
            result = db.executeUpdate(query,
                usuario.getNombreUsuario(),
                usuario.getNombreEmpleado(),
                usuario.getAreaAsignada(),
                usuario.getIdUsuario()
            );
        }
        
        return result > 0;
    }
    
    @Override
    public boolean eliminar(int id) throws SQLException {
        String query = "DELETE FROM usuarios WHERE id_usuario = ?";
        int result = db.executeUpdate(query, id);
        return result > 0;
    }
    
    @Override
    public Usuario buscarPorId(int id) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE id_usuario = ?";
        ResultSet rs = db.executeQuery(query, id);
        
        if (rs.next()) {
            return extraerUsuario(rs);
        }
        return null;
    }
    
    @Override
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios ORDER BY nombre_empleado";
        ResultSet rs = db.executeQuery(query);
        
        while (rs.next()) {
            usuarios.add(extraerUsuario(rs));
        }
        
        return usuarios;
    }
    
    @Override
    public List<Usuario> buscar(String criterio) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios WHERE nombre_usuario LIKE ? " +
                      "OR nombre_empleado LIKE ? OR area_asignada LIKE ? " +
                      "ORDER BY nombre_empleado";
        String criterioLike = "%" + criterio + "%";
        
        ResultSet rs = db.executeQuery(query, criterioLike, criterioLike, criterioLike);
        
        while (rs.next()) {
            usuarios.add(extraerUsuario(rs));
        }
        
        return usuarios;
    }
    
    // Método específico para login
    public Usuario login(String nombreUsuario, String contrasena) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = SHA2(?, 256)";
        ResultSet rs = db.executeQuery(query, nombreUsuario, contrasena);
        
        if (rs.next()) {
            return extraerUsuario(rs);
        }
        return null;
    }
    
    // Método para verificar si existe un nombre de usuario
    public boolean existeNombreUsuario(String nombreUsuario) throws SQLException {
        String query = "SELECT COUNT(*) FROM usuarios WHERE nombre_usuario = ?";
        ResultSet rs = db.executeQuery(query, nombreUsuario);
        
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }
    
    // Método auxiliar para extraer un usuario del ResultSet
    private Usuario extraerUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setNombreUsuario(rs.getString("nombre_usuario"));
        usuario.setNombreEmpleado(rs.getString("nombre_empleado"));
        usuario.setAreaAsignada(rs.getString("area_asignada"));
        usuario.setFechaCreacion(rs.getTimestamp("fecha_creacion").toLocalDateTime());
        // No establecemos la contraseña por seguridad
        return usuario;
    }
}