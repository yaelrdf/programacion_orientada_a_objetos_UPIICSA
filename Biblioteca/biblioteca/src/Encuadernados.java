class Encuadernados extends Publicacion{
    
    //ENUM para la pasta
    enum tipoPasta{blanda,dura}
    protected String tamanoPagina;

    //Constructor
    public Encuadernacion(Enum tipoPasta, String tamanoPagina){
        this.tipoPasta=tipoPasta;
        this.tamanoPagina=tamanoPagina;
    }

    //Getters y setters
    public Enum getTipoPasta(){return tipoPasta;}
    public void setTipoPasta(enum tipoPasta){this.tipoPasta=tipoPasta;}

    public String getTamanoPagina(){return tamanoPagina;}
    public void setTamanoPagina(String tamanopagina){this.tamanoPagina=tamanopagina;}

    //Metodo ejemplo de llamada
    public void printEncuadernados(){
        System.err.println("==========================================");
        Encuadernados padre = new Encuadernados();
        padre.printPublicacion();

        System.out.println("Esta impresion fue hecha desde la clase Encuadernados");
    }
}
