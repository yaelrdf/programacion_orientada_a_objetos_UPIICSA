import java.time.LocalDate;

public class Intermediarios extends Terreno{
    protected String nombre;
    protected String telefono;
    protected String direccion;
    protected String empresa;
    protected int costosDePromocion;
    protected float gananciaEsperada;
    protected LocalDate fechaTrato;

    //Constructor
    public Intermediarios(String nombre, String telefono, String direccion, String empresa, int costosDePromocion, int gananciaEsperada, LocalDate fechaTrato){
        this.nombre=nombre;
        this.telefono=telefono;
        this.direccion=direccion;
        this.empresa=empresa;
        this.costosDePromocion=costosDePromocion;
        this.gananciaEsperada=gananciaEsperada;
        this.fechaTrato=fechaTrato;
    }
    
    //Getters y setters
    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}
    
    public String getTelefono(){return telefono;}
    public void setTelefono(String telefono){this.telefono=telefono;}
    
    public String getDireccion(){return direccion;}
    public void setDireccion(String direccion){this.direccion=direccion;}
    
    public String getEmpresa(){return empresa;}
    public void setEmpresa(String empresa){this.empresa=empresa;}
    
    public int getCostosDePromocion(){return costosDePromocion;}
    public void setCostosDePromocion(int costosDePromocion){this.costosDePromocion=costosDePromocion;}
    
    public float getGananciaEsperada(){return gananciaEsperada;}
    public void setGananciaEsperada(int gananciaEsperada){this.gananciaEsperada=gananciaEsperada;}
    
    public LocalDate getFechaTrato(){return fechaTrato;}
    public void setFechaTrato(LocalDate fechaTrato){this.fechaTrato=fechaTrato;}

    //Metodo ejemplo
    public void impresionIntermediarios(){
    System.out.println("========================Imprimiendo desde la clase intemediarios=======================");
    printTerrenos();
    }

}
