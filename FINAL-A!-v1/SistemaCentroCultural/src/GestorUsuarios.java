// GestorUsuarios.java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorUsuarios {
    private final SistemaCentroCultural sistema;

    public GestorUsuarios(SistemaCentroCultural sistema) {
        this.sistema = sistema;
    }

    public Usuario validarCredenciales(String nombreUsuario, String contrasena) {
        Connection conexion = sistema.getConexion();
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = ?";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombreUsuario);
            stmt.setString(2, contrasena);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                    rs.getString("nombre_usuario"),
                    rs.getString("nombre_empleado"),
                    rs.getString("area_asignada"),
                    rs.getString("contrasena")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al validar credenciales: " + e.getMessage());
        }
        return null;
    }

    public boolean agregarUsuario(Usuario usuario) {
        if (sistema.getUsuarioActual() == null || !sistema.getUsuarioActual().getAreaAsignada().equals("administracion")) {
            return false;
        }

        Connection conexion = sistema.getConexion();
        String sql = "INSERT INTO usuarios (nombre_usuario, nombre_empleado, area_asignada, contrasena) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getNombreEmpleado());
            stmt.setString(3, usuario.getAreaAsignada());
            stmt.setString(4, usuario.getContrasena());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
            return false;
        }
    }

    // Métodos adicionales para editar, eliminar, buscar usuarios...
}