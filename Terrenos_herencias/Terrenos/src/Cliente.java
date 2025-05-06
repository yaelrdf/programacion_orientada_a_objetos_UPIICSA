import java.time.LocalDate;
import java.util.List;

public class Cliente implements Propios, Intermediarios{
    
    protected String nombre;
    protected String telefono;
    protected String documentoIdentificacion;

    
    //Getters y setters
    public String getNombre(){return nombre;}
    public void setNombre(String nombre){this.nombre=nombre;}

    public String getTelefono(){return telefono;}
    public void setTelefono(String telefono){this.telefono=telefono;}
    
    public String getDocumentoIdentificacion(){return documentoIdentificacion;}
    public void setDocumentoIdentificacion(String documentoIdentificacion){this.documentoIdentificacion=documentoIdentificacion;}
    
    //Metodo demostracion
    public void printCliente(){
        System.out.println("====Imprimiendo desde la clase CLIENTE por herencia=====");
        printIntermediarios();
        printPropios();
    }
}
