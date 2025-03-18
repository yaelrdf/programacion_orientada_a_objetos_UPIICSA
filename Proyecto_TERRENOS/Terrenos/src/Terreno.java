class Terreno {
    private String direccion;
    private double areaM2;
    private double metrosFrente;
    private String servicios;
    private boolean bardeado;
    private String statusPapeles;
    private boolean procesoCompra;
    private double costoCompra;
    private double gastosCompra;
    private String escrituracion;
    private boolean pagosPendientes;
    private boolean vendido;
    private double precioVentaRenta;
    private boolean rentado;
    private String fechaVenta;
    private Cliente cliente;
    private boolean tieneIntermediario;
    private Intermediario intermediario;
    private double ganancias;
    
    // Getters and Setters
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public double getAreaM2() {
        return areaM2;
    }
    
    public void setAreaM2(double areaM2) {
        this.areaM2 = areaM2;
    }
    
    public double getMetrosFrente() {
        return metrosFrente;
    }
    
    public void setMetrosFrente(double metrosFrente) {
        this.metrosFrente = metrosFrente;
    }
    
    public String getServicios() {
        return servicios;
    }
    
    public void setServicios(String servicios) {
        this.servicios = servicios;
    }
    
    public boolean isBardeado() {
        return bardeado;
    }
    
    public void setBardeado(boolean bardeado) {
        this.bardeado = bardeado;
    }
    
    public String getStatusPapeles() {
        return statusPapeles;
    }
    
    public void setStatusPapeles(String statusPapeles) {
        this.statusPapeles = statusPapeles;
    }
    
    public boolean isProcesoCompra() {
        return procesoCompra;
    }
    
    public void setProcesoCompra(boolean procesoCompra) {
        this.procesoCompra = procesoCompra;
    }
    
    public double getCostoCompra() {
        return costoCompra;
    }
    
    public void setCostoCompra(double costoCompra) {
        this.costoCompra = costoCompra;
    }
    
    public double getGastosCompra() {
        return gastosCompra;
    }
    
    public void setGastosCompra(double gastosCompra) {
        this.gastosCompra = gastosCompra;
    }
    
    public String getEscrituracion() {
        return escrituracion;
    }
    
    public void setEscrituracion(String escrituracion) {
        this.escrituracion = escrituracion;
    }
    
    public boolean isPagosPendientes() {
        return pagosPendientes;
    }
    
    public void setPagosPendientes(boolean pagosPendientes) {
        this.pagosPendientes = pagosPendientes;
    }
    
    public boolean isVendido() {
        return vendido;
    }
    
    public void setVendido(boolean vendido) {
        this.vendido = vendido;
    }
    
    public double getPrecioVentaRenta() {
        return precioVentaRenta;
    }
    
    public void setPrecioVentaRenta(double precioVentaRenta) {
        this.precioVentaRenta = precioVentaRenta;
    }
    
    public boolean isRentado() {
        return rentado;
    }
    
    public void setRentado(boolean rentado) {
        this.rentado = rentado;
    }
    
    public String getFechaVenta() {
        return fechaVenta;
    }
    
    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public boolean isTieneIntermediario() {
        return tieneIntermediario;
    }
    
    public void setTieneIntermediario(boolean tieneIntermediario) {
        this.tieneIntermediario = tieneIntermediario;
    }
    
    public Intermediario getIntermediario() {
        return intermediario;
    }
    
    public void setIntermediario(Intermediario intermediario) {
        this.intermediario = intermediario;
    }
    
    public double getGanancias() {
        return ganancias;
    }
    
    public void setGanancias(double ganancias) {
        this.ganancias = ganancias;
    }
}