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
    public String getIDboleto(){return idBoleto;}
    public void setIDboleto(String idBoleto){this.idBoleto=idBoleto;}
    
    public String getPeliclula(){return pelicula;}
    public void setPelicula(String pelicula){this.pelicula=pelicula;}

    public int getSala(){return sala;}
    public void setSala(int sala){this.sala = sala;}
    
    public LocalDate getFechaHora(){return fechaHora;}
    public void setFechaHora(LocalDate fechaHora){this.fechaHora = fechaHora;}
    
    public double getPrecio(){return precio;}
    public void setPrecio(double precio){this.precio = precio;}
    
    public LocalDate getFechaHoraDeCompra(){return fechaHoraDeCompra;}
    public void setFechaHoraDeCompra(LocalDate fechaHoraDeCompra){this.fechaHoraDeCompra = fechaHoraDeCompra;}
    
    public double getPrecioUnitario(){return precioUnitario;}
    public void setPrecioUnitario(double precioUnitario){this.precioUnitario = precioUnitario;}
}






