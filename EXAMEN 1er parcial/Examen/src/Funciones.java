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

    //Getters
    public String getPelicula(){return pelicula;}
    public void setPelicula(String pelicula){this.pelicula = pelicula;}
    
    public int getSala(){return sala;}
    public void setSala(int sala){this.sala = sala;}

    public LocalDate getFechaHora(){return fechaHora;}
    public void setFechaHora(LocalDate fechaHora){this.fechaHora = fechaHora;}
    
    public int getBoletosDisponibles(){return boletosDisponibles;}
    public void setBoletosDisponibles(int boletosDisponibles){this.boletosDisponibles = boletosDisponibles;}

    public int getBoletosVendidos(){return boletosVendidos;}
    public void setBoletosVendidos(int boletosVendidos){this.boletosVendidos = boletosVendidos;}

    public boolean getEstaLLena(){return llena;}
    public void setEstaLLena(boolean llena){this.llena = llena;}
}

    
    
    