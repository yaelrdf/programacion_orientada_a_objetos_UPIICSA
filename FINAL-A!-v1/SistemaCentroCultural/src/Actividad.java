// ModeloActividad.java
import java.util.List;

public class Actividad {
    private final String nombre;
    private List<String> instructoresAutorizados;
    private String clasificacion;
    private String areaAsignada;
    private List<String> materialesAsignados;

    public Actividad(String nombre) {
        this.nombre = nombre;
    }

    // Getters
    public String getNombre() { return nombre; }
    public List<String> getInstructoresAutorizados() { return instructoresAutorizados; }
    public String getClasificacion() { return clasificacion; }
    public String getAreaAsignada() { return areaAsignada; }
    public List<String> getMaterialesAsignados() { return materialesAsignados; }

    // Setters
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }
    public void setAreaAsignada(String areaAsignada) { this.areaAsignada = areaAsignada; }

    public void agregarInstructorAutorizado(String instructor) {
        instructoresAutorizados.add(instructor);
    }

    public boolean removerInstructorAutorizado(String instructor) {
        return instructoresAutorizados.remove(instructor);
    }

    public void agregarMaterialAsignado(String material) {
        materialesAsignados.add(material);
    }

    public boolean removerMaterialAsignado(String material) {
        return materialesAsignados.remove(material);
    }
}