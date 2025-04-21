import java.util.List;

public class Propios extends Terreno {
    protected float costoCompra;
    protected float costoEscrituracion;
    protected List<String> pagosPendientes;

    //Constructor
    public Propios(float costoCompra, float costoEscrituracion, List<String> pagosPendientes){
        this.costoCompra=costoCompra;
        this.costoEscrituracion=costoEscrituracion;
        this.pagosPendientes=pagosPendientes;
    }
    
    //Getters y setters
    public float getCostoCompra(){return costoCompra;}
    public void setCostoCompra(float costoCompra){this.costoCompra=costoCompra;}
    
    public float getCostoEscrituracion(){return costoEscrituracion;}
    public void setCostoEscrituracion(float costoEscrituracion){this.costoEscrituracion=costoEscrituracion;}
    
    public List<String> getPagosPendientes(){return pagosPendientes;}
    public void setPagosPendientes(List<String> pagosPendientes){this.pagosPendientes=pagosPendientes;}
    
    //metodo ejemplo
    public void printPropios(){
        System.out.println("==========Imprimiendo desde la clase Propios============");
        printTerrenos();
    }
}
