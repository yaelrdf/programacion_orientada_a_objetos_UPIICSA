// GestorAlumnos.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorAlumnos {
    private final SistemaCentroCultural sistema;

    public GestorAlumnos(SistemaCentroCultural sistema) {
        this.sistema = sistema;
    }

    public boolean agregarAlumno(Alumno alumno) {
        if (sistema.getUsuarioActual() == null || !sistema.getUsuarioActual().getAreaAsignada().equals("administracion")) {
            return false;
        }

        Connection conexion = sistema.getConexion();
        String sql = "INSERT INTO alumnos (no_expediente, nombre_completo, fecha_nacimiento) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, alumno.getNoExpediente());
            stmt.setString(2, alumno.getNombreCompleto());
            stmt.setDate(3, new java.sql.Date(alumno.getFechaNacimiento().getTime()));
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar alumno: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarPagoAlumno(String noExpediente, Date fechaPago) {
        Connection conexion = sistema.getConexion();
        String sql = "UPDATE alumnos SET pago_realizado = TRUE, fecha_pago = ? WHERE no_expediente = ?";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(fechaPago.getTime()));
            stmt.setString(2, noExpediente);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar pago: " + e.getMessage());
            return false;
        }
    }

    public boolean inscribirActividadAlumno(String noExpediente, String actividad, Date fechaInscripcion) {
        Connection conexion = sistema.getConexion();
        
        // Primero verificar si el alumno ha pagado
        String sqlVerificar = "SELECT pago_realizado FROM alumnos WHERE no_expediente = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sqlVerificar)) {
            stmt.setString(1, noExpediente);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next() && rs.getBoolean("pago_realizado")) {
                // Insertar en la tabla de actividades_alumnos
                String sqlInscribir = "INSERT INTO actividades_alumnos (no_expediente, actividad, fecha_inscripcion) VALUES (?, ?, ?)";
                try (PreparedStatement stmtInscribir = conexion.prepareStatement(sqlInscribir)) {
                    stmtInscribir.setString(1, noExpediente);
                    stmtInscribir.setString(2, actividad);
                    stmtInscribir.setDate(3, new java.sql.Date(fechaInscripcion.getTime()));
                    
                    return stmtInscribir.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al inscribir actividad: " + e.getMessage());
        }
        return false;
    }

    // Métodos adicionales para editar, eliminar, buscar alumnos...
}