// GestorMateriales.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorMateriales {
    private final SistemaCentroCultural sistema;

    public GestorMateriales(SistemaCentroCultural sistema) {
        this.sistema = sistema;
    }

    public boolean agregarMaterial(Material material) {
        if (sistema.getUsuarioActual() == null || !sistema.getUsuarioActual().getAreaAsignada().equals("administracion")) {
            return false;
        }

        Connection conexion = sistema.getConexion();
        String sql = "INSERT INTO materiales (id_material, nombre, grupo_actividad_asignado, condicion) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, material.getIdMaterial());
            stmt.setString(2, material.getNombre());
            stmt.setString(3, material.getGrupoActividadAsignado());
            stmt.setString(4, material.getCondicion());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar material: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarCondicionMaterial(String idMaterial, String condicion) {
        Connection conexion = sistema.getConexion();
        String sql = "UPDATE materiales SET condicion = ? WHERE id_material = ?";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, condicion);
            stmt.setString(2, idMaterial);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar condición de material: " + e.getMessage());
            return false;
        }
    }

    // Métodos adicionales para editar, eliminar, buscar materiales...
}