
// ModeloAlumno.java
import java.util.Date;
import java.util.List;

public class Alumno {
    private final String noExpediente;
    private String nombreCompleto;
    private Date fechaNacimiento;
    private List<String> actividadesInscritas;
    private Date fechaInscripcion;
    private boolean pagoRealizado;
    private Date fechaPago;

    public Alumno(String noExpediente, String nombreCompleto, Date fechaNacimiento) {
        this.noExpediente = noExpediente;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.pagoRealizado = false;
    }

    // Getters
    public String getNoExpediente() { return noExpediente; }
    public String getNombreCompleto() { return nombreCompleto; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public List<String> getActividadesInscritas() { return actividadesInscritas; }
    public Date getFechaInscripcion() { return fechaInscripcion; }
    public boolean isPagoRealizado() { return pagoRealizado; }
    public Date getFechaPago() { return fechaPago; }

    // Setters
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public void registrarPago(Date fechaPago) {
        this.pagoRealizado = true;
        this.fechaPago = fechaPago;
    }

    public boolean inscribirActividad(String actividad, Date fechaInscripcion) {
        if (!pagoRealizado) return false;
        
        this.actividadesInscritas.add(actividad);
        this.fechaInscripcion = fechaInscripcion;
        return true;
    }

    public boolean darBajaActividad(String actividad) {
        return actividadesInscritas.remove(actividad);
    }
}