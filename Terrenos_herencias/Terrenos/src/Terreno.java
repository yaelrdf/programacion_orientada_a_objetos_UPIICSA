public class Terreno extends Propiedad{
    protected boolean bardeado;

    //getters y setters
     public boolean getBardeado(){return bardeado;}
     public void setBardeado(){boolean bardeado;}{this.bardeado=bardeado;}

    //Metodo demostracion
    public void printTerrenos(){
        System.out.println("=====Imprimiendo desde la clcase Terrenos por invocacion========");
        printPropiedad();
    }
}
