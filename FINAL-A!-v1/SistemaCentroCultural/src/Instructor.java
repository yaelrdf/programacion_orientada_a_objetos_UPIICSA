// ModeloInstructor.java
import java.util.Date;
import java.util.List;

public class Instructor {
    private final String noExpediente;
    private String nombreCompleto;
    private Date fechaNacimiento;
    private List<String> actividadesAutorizadas;
    private List<String> certificados;
    private List<String> documentosIdentidad;

    public Instructor(String noExpediente, String nombreCompleto, Date fechaNacimiento) {
        this.noExpediente = noExpediente;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters
    public String getNoExpediente() { return noExpediente; }
    public String getNombreCompleto() { return nombreCompleto; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public List<String> getActividadesAutorizadas() { return actividadesAutorizadas; }
    public List<String> getCertificados() { return certificados; }
    public List<String> getDocumentosIdentidad() { return documentosIdentidad; }

    // Setters
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public void agregarActividadAutorizada(String actividad) {
        actividadesAutorizadas.add(actividad);
    }

    public boolean removerActividadAutorizada(String actividad) {
        return actividadesAutorizadas.remove(actividad);
    }

    public void agregarCertificado(String certificado) {
        certificados.add(certificado);
    }

    public void agregarDocumentoIdentidad(String documento) {
        documentosIdentidad.add(documento);
    }
}