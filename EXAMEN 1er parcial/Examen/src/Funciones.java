import java.time.LocalDate;

class Funciones {
    private String pelicula;
    private int sala;
    private LocalDate fechaHora;
    private int boletosDisponibles;
    private int boletosVendidos;
    private boolean llena;

    //Constructor
    public Funciones(String pelicula, int sala, LocalDate fechaHora, int boletosDisponibles, int boletosVendidos, boolean llena){
        this.pelicula=pelicula;
        this.sala=sala;
        this.fechaHora=fechaHora;
        this.boletosDisponibles=boletosDisponibles;
        this.boletosVendidos=boletosVendidos;
        this.llena= false;
    }
}
