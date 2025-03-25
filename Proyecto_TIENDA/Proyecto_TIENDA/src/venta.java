import java.util.ArrayList;
import java.util.List;

class venta {
    //Atributos
    private List<Producto> lstProductos;
    private List<Integer> lstCantidades;
    private double pagoProducto;
    private List<Double> lstPrecioUnitario;
    private double montoTotal;
    private String formaDePago;

    //Getters y Setters
    public List<Integer> getListCantidades(){return lstCantidades;}
    public void setLstCantidades(List<Integer> lstCantidades){this.lstCantidades=lstCantidades;}
    
    public List<Double> getLstPrecioUnitario(){return lstPrecioUnitario;}
    public void setLstPrecioUnitario(List<Double> lstPrecioUnitatio){this.lstPrecioUnitario=lstPrecioUnitatio;}

    //Datos simples
    public double getPagoProducto(){return pagoProducto;}
    public void setPagoProdcto(double pagoProducto){this,pagoProducto = pagoProducto;}
    
    public double getMontoTotal(){return montoTotal;}
    public void setMontoTotal(double montoTotal){this.montoTotal=montoTotal;}

    public String getFormaDePago(){return formaDePago;}
    public void setFormaDePago(String formaDePago){this.formaDePago=formaDePago;}
}
