public class LocalComercial extends Propiedad{
    protected type tipo;
    protected int ocupacionMax;
    protected Boolean puertaCarga;

    //Getters setters
    public type getTipo(){return tipo;}
    public void setTipo(type tipo){this.tipo=tipo;}
    
    public int getOcupacionMax(){return ocupacionMax;}
    public void setOcupacionMax(int ocupacionMax){this.ocupacionMax=ocupacionMax;}
    
    public Boolean getPuertaCarga(){return puertaCarga;}
    public void setPuertaCarga(Boolean puertaCarga){this.puertaCarga=puertaCarga;}

     //Metodo demostracion
    public void printComercial(){
        System.out.println("======Imprimiendo por invocacion desde LocalComercial=========");
        printPropiedad();
    }
}

enum type{
    tienda,oficina
}