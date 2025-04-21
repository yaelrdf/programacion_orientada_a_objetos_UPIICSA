public class Terreno {
    protected String direccion;
    protected int metrosDeFrente;
    protected int metrosCuadrados;
    protected boolean bardeado;
    protected String statusPapeles;
    protected float precioPublico;

    //Constructor
    public Terreno(String direccion, int metrosDeFrente, int metrosCuadrados, boolean bardeado, String statusPapeles, float precioPublico){
        this.direccion=direccion;
        this.metrosDeFrente=metrosDeFrente;
        this.metrosCuadrados=metrosCuadrados;
        this.bardeado=bardeado;
        this.statusPapeles=statusPapeles;
        this.precioPublico=precioPublico;
    }
    
    //getters y setters
    public String getDireccion(){return direccion;}
    public void setDireccion(String direccion){this.direccion=direccion;}
    
    public int getMetrosDeFrente(){return metrosDeFrente;}
    public void setMetrosDeFrente(int metrosDeFrente){this.metrosDeFrente=metrosDeFrente;}
    
    public int getMetrosCuadrados(){return metrosCuadrados;}
    public void setMetrosCuadrados(int metrosCuadrados){this.metrosCuadrados=metrosCuadrados;}
    
    public boolean getBardeado(){return bardeado;}
    public void setBardeado(boolean bardeado){this.bardeado=bardeado;}
    
    public String getStatusPapeles(){return statusPapeles;}
    public void setStatusPapeles(String statusPapeles){this.statusPapeles=statusPapeles;}

    public float getPrecioPublico(){return precioPublico;}
    public void setPrecioPublico(float precioPublico){this.precioPublico=precioPublico;}
    
    //Metodo ejemplo para demostracion
    public void printTerrenos(){
        System.out.println("Imprimiendo desde la clase Terrenos");
    }
}

//Opciones para los servicios
enum Servicios{
    agua,luz,drenaje
}