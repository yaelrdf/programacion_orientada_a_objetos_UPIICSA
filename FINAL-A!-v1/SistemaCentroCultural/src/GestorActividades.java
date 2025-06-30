// GestorActividades.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestorActividades {
    private final SistemaCentroCultural sistema;

    public GestorActividades(SistemaCentroCultural sistema) {
        this.sistema = sistema;
    }

    public boolean agregarActividad(Actividad actividad) {
        if (sistema.getUsuarioActual() == null || !sistema.getUsuarioActual().getAreaAsignada().equals("administracion")) {
            return false;
        }

        Connection conexion = sistema.getConexion();
        String sql = "INSERT INTO actividades (nombre, clasificacion, area_asignada) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, actividad.getNombre());
            stmt.setString(2, actividad.getClasificacion());
            stmt.setString(3, actividad.getAreaAsignada());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar actividad: " + e.getMessage());
            return false;
        }
    }

    public boolean asignarMaterialActividad(String actividad, String idMaterial) {
        Connection conexion = sistema.getConexion();
        String sql = "INSERT INTO materiales_actividades (actividad, id_material) VALUES (?, ?)";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, actividad);
            stmt.setString(2, idMaterial);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al asignar material a actividad: " + e.getMessage());
            return false;
        }
    }

    // Métodos adicionales para editar, eliminar, buscar actividades...
}