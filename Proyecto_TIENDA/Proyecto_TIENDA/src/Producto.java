class Producto {
    //Exposicion de atributos
    private double precioCompra;
    private String marca;
    private String nombre;
    private String caducidad;
    private double precioVenta;
    private double contenidoNeto;
    private String proveedor;
    private String cantidadAlmacen;
    private tipoEmpaque empaque;
    private int stockMinimo;
    private int codigoBarras;

     //Constructor
    public Producto(double precioCompra, String marca, String nombre, String caducidad, double precioVenta, double contenidoNeto, String proveedor, String cantidadAlmacen, tipoEmpaque empaque, int stockMinimo, int codigoBarras){
        this.precioCompra = precioCompra;
        this.marca = marca;
        this.nombre = nombre;
        this.caducidad = caducidad;
        this.precioVenta = precioVenta;
        this.contenidoNeto = contenidoNeto;
        this.proveedor = proveedor;
        this.cantidadAlmacen = cantidadAlmacen;
        this.empaque = empaque;
        this.stockMinimo = stockMinimo;
        this.codigoBarras = codigoBarras;
    }
}