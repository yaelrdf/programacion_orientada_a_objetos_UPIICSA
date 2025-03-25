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
    }
}
