import java.util.List;

public class Mobiliario implements Area{
    protected String codInventario;
    protected String locacion;
    protected String annio;
    protected boolean comodato;

    //Getters y setters
    public String getCodInventario(){return codInventario;}
    public void setCodInventario(String codInventario){this.codInventario=codInventario;}
    
    public String getLocacion(){return locacion;}
    public void setLocacion(String locacion){this.locacion=locacion;}
    
    public String getAnnio(){return annio;}
    public void setAnnio(String annio){this.annio=annio;}
    
    public boolean getComodato(){return comodato;}
    public void setComodato(boolean comodato){this.comodato=comodato;}
}
