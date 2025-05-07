import java.time.LocalTime;
import java.util.List;

public class AreaEstudio extends Mobiliario{
    protected Typo tipo;
    protected List<String> mobiliario;
    protected LocalTime horario;

    //Getters y setters
    public Typo getTipo(){return tipo;}
    public void setTipo(Typo tipo){this.tipo=tipo;}

    public List<String> getMobiliario(){return mobiliario;}
    public void setMobiliario(List<String> mobiliario){this.mobiliario=mobiliario;}
    
    public LocalTime getHorario(){return horario;}
    public void setHorario(LocalTime horario){this.horario=horario;}
}

//Enum para el tipo
enum Typo{
    Publica, privada
}