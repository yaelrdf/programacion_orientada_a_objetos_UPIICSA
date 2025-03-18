class Cliente {
    private String fechaVenta;
    private String esquemaCompra;
    private double precioCompraRenta;
    private int mesesContrato;
    private Terreno terrenoComprado;
    private String nombre;
    private String curp;
    private String telefono;
    private String datos;
    
    // Getters and Setters
    public String getFechaVenta() {
        return fechaVenta;
    }
    
    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
    
    public String getEsquemaCompra() {
        return esquemaCompra;
    }
    
    public void setEsquemaCompra(String esquemaCompra) {
        this.esquemaCompra = esquemaCompra;
    }
    
    public double getPrecioCompraRenta() {
        return precioCompraRenta;
    }
    
    public void setPrecioCompraRenta(double precioCompraRenta) {
        this.precioCompraRenta = precioCompraRenta;
    }
    
    public int getMesesContrato() {
        return mesesContrato;
    }
    
    public void setMesesContrato(int mesesContrato) {
        this.mesesContrato = mesesContrato;
    }
    
    public Terreno getTerrenoComprado() {
        return terrenoComprado;
    }
    
    public void setTerrenoComprado(Terreno terrenoComprado) {
        this.terrenoComprado = terrenoComprado;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCurp() {
        return curp;
    }
    
    public void setCurp(String curp) {
        this.curp = curp;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getDatos() {
        return datos;
    }
    
    public void setDatos(String datos) {
        this.datos = datos;
    }
}