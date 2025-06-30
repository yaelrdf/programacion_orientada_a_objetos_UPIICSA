// ModeloUsuario.java
public class Usuario {
    private final String nombreUsuario;
    private String nombreEmpleado;
    private String areaAsignada;
    private String contrasena;

    public Usuario(String nombreUsuario, String nombreEmpleado, String areaAsignada, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.nombreEmpleado = nombreEmpleado;
        this.areaAsignada = areaAsignada;
        this.contrasena = contrasena;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public String getAreaAsignada() {
        return areaAsignada;
    }

    public boolean validarContrasena(String contrasena) {
        return this.contrasena.equals(contrasena);
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public void setAreaAsignada(String areaAsignada) {
        this.areaAsignada = areaAsignada;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}