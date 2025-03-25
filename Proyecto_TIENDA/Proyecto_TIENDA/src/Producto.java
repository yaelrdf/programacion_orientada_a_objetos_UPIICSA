import java.time.LocalDate;

class Producto {
    //Exposicion de atributos
    private double precioCompra;
    private String marca;
    private String nombre;
    private LocalDate caducidad;
    private double precioVenta;
    private double contenidoNeto;
    private String proveedor;
    private String cantidadAlmacen;
    private tipoEmpaque empaque;
    private int stockMinimo;
    private int codigoBarras;

    //Getters y setters
    public double getPrecioCompra(){return precioCompra;}
    public void setPrecioCopmpra(double precioCompra){ this.precioCompra = precioCompra;}

    public String getMarca(){return marca;}
    public void setMarca(String marca){this.marca=marca;}

    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}

    public LocalDate getCaducidad(){return caducidad;}
    public void setCaducudad(LocalDate  caducidad){this.caducidad=caducidad;}

    public double getPrecioVenta(){return precioVenta;}
    public void setPrecioVenta(double precioVenta){this.precioVenta=precioVenta;}

    public double getContenidoNeto(){return contenidoNeto;}
    public void setContenidoNetp(double contenidoNeto){this.contenidoNeto=contenidoNeto;}

    public String getProveedor(){return proveedor;}
    public void setProveedor(String proveedor){this.proveedor=proveedor;}

    //Enum tipo empaque
    public tipoEmpaque getTipoEmpaque(){return empaque;}
    public void setTipoEmpaque(tipoEmpaque empaque){this.empaque=empaque;}

}