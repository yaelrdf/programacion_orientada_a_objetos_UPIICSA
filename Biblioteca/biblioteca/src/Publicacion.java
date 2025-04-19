import java.time.LocalDate;
import java.util.List;

class Publicacion {
    protected String ISBN;
    protected String editorial;
    protected List<String> autores;
    protected String ubicacion;
    protected LocalDate fechaPublicacion;
    protected Integer noPaginas;
    protected String categoria;
    protected Integer claveInterna;
    protected String tirulo;
    protected Integer piezasDisponibles;

    //Constructor
    public Publicacion(String ISBN, String editorial, List<String> autores, String ubicacion, LocalDate fechaPublicacion, Integer noPaginas, String categoria, Integer claveInterna, String tirulo, Integer piezasDisponibles){
        this.ISBN=ISBN;
        this.editorial=editorial;
        this.autores=autores;
        this.ubicacion=ubicacion;
        this.fechaPublicacion=fechaPublicacion;
        this.noPaginas=noPaginas;
        this.categoria=categoria;
        this.claveInterna=claveInterna;
        this.tirulo=tirulo;
        this.piezasDisponibles=piezasDisponibles;
    }

    //Getters y setters
    public String getISBN(){return ISBN;}
    public void setISBN(String ISBN){this.ISBN=ISBN;}

    public String getEditorial(){return editorial;}
    public void setEditorial(String editorial){this.editorial=editorial;}

    public List getAutores(){return autores;}
    public void setAutores(List<String> autores){this.autores=autores;}

    public String getUbicacion(){return ubicacion;}
    public void setUbicacion(String ubicacion){this.ubicacion=ubicacion;}

    public LocalDate getFechaPublicacion(){return fechaPublicacion;}
    public void setFechaPublicacion(LocalDate fechaPublicacion){this.fechaPublicacion=fechaPublicacion;}

    public Integer getNoPaginas(){return noPaginas;}
    public void setNoPaginas(Integer noPaginas){this.noPaginas=noPaginas;}
    
    public String getCategoria(){return categoria;}
    public void setCategoria(String categoria){this.categoria=categoria;}

    public Integer getClaveInterna(){return claveInterna;}
    public void setClaveInterna(Integer claveInterna){this.claveInterna=claveInterna;}

    public String getTirulo(){return tirulo;}
    public void setTirulo(String tirulo){this.tirulo=tirulo;}

    public Integer getPiezasDisponibles(){return piezasDisponibles;}
    public void setPiezasDisponibles(Integer piezasDisponibles){this.piezasDisponibles=piezasDisponibles;}

    //Metodo ejemplo
    public void printPublicacion(){
        System.out.println("Imprimiendo desde la clase padre Publicacion");
    }
}
