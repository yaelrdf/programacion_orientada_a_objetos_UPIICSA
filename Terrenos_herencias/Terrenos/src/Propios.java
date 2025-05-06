import java.util.List;

public interface Propios extends Terreno, Departamentos, Casas, LocalComercial{
    // Getters y Setters
    float getCostoCompra();
    void setCostoCompra(float costoCompra);
    
    float getCostoEscrituracion();
    void setCostoEscrituracion(float costoEscrituracion);
    
    List<String> getPagosPendientes();
    void setPagosPendientes(List<String> pagosPendientes);
    
    default void printPropios(){
        System.out.println("====Imprimiendo desde la interface PROPIOS====");
        printTerrenos();
        printDepartamentos();
        printCasas();
        printComercial();
        System.out.println("\n");
    }
}