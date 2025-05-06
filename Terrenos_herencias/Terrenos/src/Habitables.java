public interface Habitables extends Propiedad {
  
  // Getters y Setters
  int getNumeroHabitaciones();
  void setNumeroHabitaciones(int numeroHabitaciones);
  
  int getNumeroBaniosCompletos();
  void setNumeroBaniosCompletos(int numeroBaniosCompletos);
  
  int getNumeroMedioBanios();
  void setNumeroMedioBanios(int numeroMedioBanios);
  
  int getEspaciosEstacionamiento();
  void setEspaciosEstacionamiento(int espaciosEstacionamiento);
  
  int getMetrosConstruccion();
  void setMetrosConstruccion(int metrosConstruccion);

  //Metodo demostracion
  default void printHabitables(){
    System.out.println("====Imprimiendo desde la clase Habitables por herencia====");
    printPropiedad();
  }
}



