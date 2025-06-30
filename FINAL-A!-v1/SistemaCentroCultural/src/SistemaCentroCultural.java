// SistemaCentroCultural.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class SistemaCentroCultural {
    private Connection conexion;
    private Usuario usuarioActual;
    private final GestorUsuarios gestorUsuarios;
    private final GestorAlumnos gestorAlumnos;
    private final GestorInstructores gestorInstructores;
    private final GestorActividades gestorActividades;
    private final GestorMateriales gestorMateriales;

    public SistemaCentroCultural() {
        this.gestorUsuarios = new GestorUsuarios(this);
        this.gestorAlumnos = new GestorAlumnos(this);
        this.gestorInstructores = new GestorInstructores(this);
        this.gestorActividades = new GestorActividades(this);
        this.gestorMateriales = new GestorMateriales(this);
    }

    public boolean conectarBD(String url, String usuario, String contrasena) {
        try {
            conexion = DriverManager.getConnection(url, usuario, contrasena);
            return true;
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            return false;
        }
    }

    public Usuario iniciarSesion(String nombreUsuario, String contrasena) {
        usuarioActual = gestorUsuarios.validarCredenciales(nombreUsuario, contrasena);
        return usuarioActual;
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }

    public Connection getConexion() {
        return conexion;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public GestorUsuarios getGestorUsuarios() {
        return gestorUsuarios;
    }

    public GestorAlumnos getGestorAlumnos() {
        return gestorAlumnos;
    }

    public GestorInstructores getGestorInstructores() {
        return gestorInstructores;
    }

    public GestorActividades getGestorActividades() {
        return gestorActividades;
    }

    public GestorMateriales getGestorMateriales() {
        return gestorMateriales;
    }
}