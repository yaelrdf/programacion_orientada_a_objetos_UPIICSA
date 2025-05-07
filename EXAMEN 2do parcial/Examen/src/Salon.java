import java.util.List;

public class Salon extends Mobiliario{
    protected List<String> mobiliario;
    protected Type tipo;
    protected String edificio;

    //Getters y setters
    public List<String> getMobiliario(){return mobiliario;}
    public void setMobiliario(List<String> mobiliario){this.mobiliario=mobiliario;}
    
    public Type getTipo(){return tipo;}
    public void setTipo(Type tipo){this.tipo=tipo;}
    
    public String getEdificio(){return edificio;}
    public void setEdificio(String edificio){this.edificio=edificio;}
}

//Enum para el tipo
enum Type{
    Arte, Musica, General, Audiovisual
}