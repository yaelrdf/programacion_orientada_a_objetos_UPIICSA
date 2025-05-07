import java.util.List;

public class Laboratorio extends Mobiliario implements {
    protected Type tipoLab;
    protected List<String> mobiliario;
    protected List<String> equiposComputo;

    //Getters y setters
    public Type getTipoLab(){return tipoLab;}
    public void setTipoLab(Type tipoLab){this.tipoLab=tipoLab;}

    public List<String> getMobiliario(){return mobiliario;}
    public void setMobiliario(List<String> mobiliario){this.mobiliario=mobiliario;}

    public List<Strinv> getEquiposComputo(){return equiposComputo;}
    public void setEquiposComputo(List<String> equiposComputo){this.equiposComputo=equiposComputo;}
}

//Enum para el tipo
enum Type{
    Fisica,Quimica,Computo
}