import java.time.LocalDate;

public class Renta extends Cliente{
    protected int anticipo;
    protected int tiempoRenta;
    protected LocalDate fechaTrato;

    
    //Getters y setters
    public int getAnticipo(){return anticipo;}
    public void setAnticipo(int anticipo){this.anticipo=anticipo;}
    
    public int getTiempoRenta(){return tiempoRenta;}
    public void setTiempoRenta(int tiempoRenta){this.tiempoRenta=tiempoRenta;}
    
    public LocalDate getFechaTrato(){return fechaTrato;}
    public void setFechaTrato(LocalDate fechaTrato){this.fechaTrato=fechaTrato;}
    
    //Metodo ejemplo
    public void printRenta(){
        System.out.println("==========Impresion directa desde la clase Renta por invocacion===========");
        printCliente();
    }

}
