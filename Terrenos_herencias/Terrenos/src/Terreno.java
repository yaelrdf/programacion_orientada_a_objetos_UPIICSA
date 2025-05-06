public interface Terreno extends Propiedad {
    // Getters y Setters
    boolean getBardeado();
    void setBardeado(boolean bardeado);

    //Metodo demostracion
    default void printTerrenos(){
        System.out.println("===Imprimiendo desde la interface Terrenos====");
        printPropiedad();
    }
}