// ModeloMaterial.java
public class Material {
    private final String idMaterial;
    private String nombre;
    private String grupoActividadAsignado;
    private String condicion;

    public Material(String idMaterial, String nombre) {
        this.idMaterial = idMaterial;
        this.nombre = nombre;
    }

    // Getters
    public String getIdMaterial() { return idMaterial; }
    public String getNombre() { return nombre; }
    public String getGrupoActividadAsignado() { return grupoActividadAsignado; }
    public String getCondicion() { return condicion; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setGrupoActividadAsignado(String grupo) { this.grupoActividadAsignado = grupo; }
    public void setCondicion(String condicion) { this.condicion = condicion; }
}