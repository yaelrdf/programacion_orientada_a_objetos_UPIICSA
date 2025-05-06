public interface LocalComercial extends Propiedad {
    // Getters y Setters
    Tipo getTipo();
    void setTipo(Tipo tipo);
    
    int getOcupacionMax();
    void setOcupacionMax(int ocupacionMax);
    
    Boolean getPuertaCarga();
    void setPuertaCarga(Boolean puertaCarga);

    default void printComercial(){
        System.out.println("====Imprimiendo por herencia desde LocalComercial====");
        printPropiedad();
    }
}

//Enum para tipo de propiedad
enum Tipo {
    tienda, oficina
}