// GestorInstructores.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorInstructores {
    private final SistemaCentroCultural sistema;

    public GestorInstructores(SistemaCentroCultural sistema) {
        this.sistema = sistema;
    }

    public boolean agregarInstructor(Instructor instructor) {
        if (sistema.getUsuarioActual() == null || !sistema.getUsuarioActual().getAreaAsignada().equals("administracion")) {
            return false;
        }

        Connection conexion = sistema.getConexion();
        String sql = "INSERT INTO instructores (no_expediente, nombre_completo, fecha_nacimiento) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, instructor.getNoExpediente());
            stmt.setString(2, instructor.getNombreCompleto());
            stmt.setDate(3, new java.sql.Date(instructor.getFechaNacimiento().getTime()));
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar instructor: " + e.getMessage());
            return false;
        }
    }

    public boolean agregarActividadAutorizada(String noExpediente, String actividad) {
        Connection conexion = sistema.getConexion();
        String sql = "INSERT INTO actividades_instructores (no_expediente, actividad) VALUES (?, ?)";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, noExpediente);
            stmt.setString(2, actividad);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar actividad autorizada: " + e.getMessage());
            return false;
        }
    }

    // Métodos adicionales para editar, eliminar, buscar instructores...
}