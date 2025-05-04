public class Habitables extends Propiedad{
    protected int numeroHabitaciones;
    protected int numeroBaniosCompletos;
    protected int numeroMedioBanios;
    protected int espaciosEstacionamiento;
    protected int metrosConstruccion;

    //Getters Setters
    public int getNumeroHabitaciones(){return numeroHabitaciones;}
    public void setNumeroHabitaciones(int numeroHabitaciones){this.numeroHabitaciones=numeroHabitaciones;}
    
    public int getNumeroBaniosCompletos(){return numeroBaniosCompletos;}
    public void setNumeroBaniosCompletos(int numeroBaniosCompletos){this.numeroBaniosCompletos=numeroBaniosCompletos;}
    
    public int getNumeroMedioBanios(){return numeroMedioBanios;}
    public void setNumeroMedioBanios(int numeroMedioBanios){this.numeroMedioBanios=numeroMedioBanios;}
    
    public int getEspaciosEstacionamiento(){return espaciosEstacionamiento;}
    public void setEspaciosEstacionamiento(int espaciosEstacionamiento){this.espaciosEstacionamiento=espaciosEstacionamiento;}
    
    public int getMetrosConstruccion(){return metrosConstruccion;}
    public void setMetrosConstruccion(int metrosConstruccion){this.metrosConstruccion=metrosConstruccion;}

    //Metodo demostracion
      public void printHabitables(){
        System.out.println("Imprimiendo desde la clase Habitables por herencia");
        printPropiedad();
    }
}


