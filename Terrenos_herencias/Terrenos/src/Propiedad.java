import java.util.List;

public interface Propiedad {
    // Getters y Setters
    String getDireccion();
    void setDireccion(String direccion);

    Float getMetrosCuadrados();
    void setMetrosCuadrados(Float metrosCuadrados);
    
    Float getMetrosFrente();
    void setMetrosFrente(Float metrosFrente);
    
    List<String> getServicios();
    void setServicios(List<String> servicios);
    
    String getStstusPapeles();
    void setStstusPapeles(String ststusPapeles);
    
    Float getPrecioPublico();
    void setPrecioPublico(Float precioPublico);
    
    Estado getDisponibilidad();
    void setDisponibilidad(Estado disponibilidad);
    
    default void printPropiedad(){
        System.out.println("Puede alcanzar la interface padre PROPIEDAD \n");
    }
}

//Enum para el estado
enum Estado {
    Rentado, Comprado, Disponible
}