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
}

//Enum para status
enum status {
    RESERVED,
    PURCHASED,
    CANCELLED
}