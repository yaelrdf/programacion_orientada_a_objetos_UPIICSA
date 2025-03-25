import java.time.LocalDate;
import java.util.List;

class Boletos {
    private String idBoleto;
    private String pelicula;
    private int sala;
    private LocalDate fechaHora;
    private double precio;
    private LocalDate fechaHoraDeCompra;
    private double precioUnitario;

    //Constructor
    public Boletos(String idBoleto, String pelicula, int sala, LocalDate fechaHora, double precio, LocalDate fechaHoraDeCompra){
        this.idBoleto = idBoleto;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fechaHora = fechaHora;
        this.precio = precio;
        this.fechaHoraDeCompra = fechaHoraDeCompra;
    }

    //Getters y setters
    
}
