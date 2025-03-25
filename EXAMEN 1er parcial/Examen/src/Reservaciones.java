import java.util.List;

class Reservaciones {
    private String nombre;
    private String correoElectronico;
    private String numeroTelefono;
    private List<String> boletosComprados;
    private status estadoReservacion;

    //Constructor
    public Reservaciones(String nombre, String correoElectronico, String numeroTelefono, List<String> boletosComprados, status estadoReservacion){
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.numeroTelefono = numeroTelefono;
        this.boletosComprados = boletosComprados;
        this.estadoReservacion = estadoReservacion;
    }

    //Getters
    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre = nombre;}

    public String getCorreoElectronico(){return correoElectronico;}
    public void setCorreoElectronico(String correoElectronico){this.correoElectronico = correoElectronico;}

    public String getNumeroTelefonico(){return numeroTelefono;}
    public void setNumeroTelefonico(String numeroTelefono){this.numeroTelefono = numeroTelefono;}

    public List getLstComprados(){return boletosComprados;}
    public void setLstComprados(List boletosComprados){this.boletosComprados = boletosComprados;}

    public status geStatus(){return estadoReservacion;} 
    public void setStatus(status estadoReservacion){this.estadoReservacion = estadoReservacion;}
}

//Enum para status
enum status {
    RESERVADO,CONFIRMADO,CANCELADO
}


    