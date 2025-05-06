public interface Departamentos extends Habitables {
    // Getters y setters
    boolean getElevador();
    void setElevador(boolean elevador);

    float getCuotaMantenimiento();
    void setCuotaMantenimiento(float cuotaMantenimiento);

    default void printDepartamentos(){
        System.out.println("====Imprimiendo desde la interface DEPARTAMENTOS por herencia ======");
        printHabitables();
    }
}