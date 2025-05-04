import java.util.List;

public class Propiedad {
    protected String direccion;
    protected Float metrosCuadrados;
    protected Float metrosFrente;
    protected List<String> servicios;
    protected String ststusPapeles;
    protected Float precioPublico;
    protected estado disponibilidad;

    //Getters Setters
    public String getDireccion(){return direccion;}
    public void setDireccion(String direccion){this.direccion=direccion;}

    public Float getMetrosCuadrados(){return metrosCuadrados;}
    public void setMetrosCuadrados(Float metrosCuadrados){this.metrosCuadrados=metrosCuadrados;}
    
    public Float getMetrosFrente(){return metrosFrente;}
    public void setMetrosFrente(Float metrosFrente){this.metrosFrente=metrosFrente;}
    
    public List<String> getServicios(){return servicios;}
    public void setServicios(List<String> servicios){this.servicios=servicios;}
    
    public String getStstusPapeles(){return ststusPapeles;}
    public void setStstusPapeles(String ststusPapeles){this.ststusPapeles=ststusPapeles;}
    
    public Float getPrecioPublico(){return precioPublico;}
    public void setPrecioPublico(Float precioPublico){this.precioPublico=precioPublico;}
    
    public estado getDisponibilidad(){return disponibilidad;}
    public void setDisponibilidad(estado disponibilidad){this.disponibilidad=disponibilidad;}

    //Metodo demostracion
    public void printPropiedad(){
        System.out.println("Imprimiendo desde la clase propiedad por herencia");
        System.out.println("===========Ultimo padre(PROPIEDAD) alzanzado===========\n");
    }

}


//Enum para el estado
enum estado{
    Rentado,Comprado,Disponible
}