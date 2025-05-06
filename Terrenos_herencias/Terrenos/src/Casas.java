public interface Casas extends Habitables {
    // Getters y Setters
    int getPisos();
    void setPisos(int pisos);
    
    boolean getPatio();
    void setPatio(boolean patio);

    //Metodo demostracion
    default void printCasas(){
        System.out.println("====Imprimiendo desde la interface CASAS por herencia ====");
        printHabitables();
    }
}
