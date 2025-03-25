import java.util.List;

class Pelicula {
    private String nombre;
    private String director;
    private String clasificacion;
    private List<Integer> funcionesDisponibles;
    private int totalBoletosVendidos;

    //Constructor
    public Pelicula(String nombre, String director, String clasificacion, List<Integer> funcionesDisponibles, int totalBoletosVendidos){
        this.nombre=nombre;
        this.director=director;
        this.clasificacion=clasificacion;
        this.funcionesDisponibles=funcionesDisponibles;
        this.totalBoletosVendidos=totalBoletosVendidos;
    }

    //Getters y setters
    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}

    public String getDirector(){return director;}
    public void setDirector(String director){this.director=director;}

    public String getClasificacion(){return clasificacion;}
    public void setClasificacion(String clasificacion){this.clasificacion=clasificacion;}

    public List getFuncionesDisponibles(){return funcionesDisponibles;}
    public void setFuncionesDisponibles(List funcionesDisponibles){this.funcionesDisponibles= funcionesDisponibles;}

    public int getTotalBoletosVendidos(){return totalBoletosVendidos;}
    public void setTotalBoletosVendidos(int totalBoletosVendidos){this.totalBoletosVendidos = totalBoletosVendidos;}
}
