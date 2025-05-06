import java.time.LocalDate;

public interface Intermediarios extends Terreno, Departamentos, Casas, LocalComercial{
    // Getters y Setters
    String getNombre();
    void setNombre(String nombre);
    
    String getTelefono();
    void setTelefono(String telefono);
    
    String getDireccion();
    void setDireccion(String direccion);
    
    String getEmpresa();
    void setEmpresa(String empresa);
    
    int getCostosDePromocion();
    void setCostosDePromocion(int costosDePromocion);
    
    float getGananciaEsperada();
    void setGananciaEsperada(int gananciaEsperada);
    
    LocalDate getFechaTrato();
    void setFechaTrato(LocalDate fechaTrato);

    //Metodo ejemplo
    default void printIntermediarios(){
        //Impresion de solo el nombre de la interface con fines demostrativos
        System.out.println("Puede alcanzar la interface INTERMEDIARIOS\n");
}
}
