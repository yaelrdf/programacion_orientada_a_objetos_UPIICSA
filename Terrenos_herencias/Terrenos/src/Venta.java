import java.time.LocalDate;

public class Venta extends Cliente{
    protected float gananciaObtenida;
    protected LocalDate fechaVenta;
    protected LocalDate fechaCompra;
    
    //Getters y setters
    public float getGananciaObtenida(){return gananciaObtenida;}
    public void setGananciaObtenida(float gananciaObtenida){this.gananciaObtenida=gananciaObtenida;}
    
    public LocalDate getFechaVenta(){return fechaVenta;}
    public void setFechaVenta(LocalDate fechaVenta){this.fechaVenta=fechaVenta;}
    
    public LocalDate getFechaCompra(){return fechaCompra;}
    public void setFechaCompra(LocalDate fechaCompra){this.fechaCompra=fechaCompra;}

    //Metodo ejemplo
    public void printVenta(){
        System.out.println("=============Impresion directa clase Venta por invocacion directa==============");
        printCliente();
    }

    
}
