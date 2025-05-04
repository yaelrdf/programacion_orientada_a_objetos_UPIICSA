public class Casas extends Habitables{
    protected int pisos;
    protected boolean patio;

    //Getters Setters
    public int getPisos(){return pisos;}
    public void setPisos(int pisos){this.pisos=pisos;}
    
    public boolean getPatio(){return patio;}
    public void setPatio(boolean patio){this.patio=patio;}

    //Metodo demostracion
    public void printCasas(){
        System.out.println("==========Imprimiendo desde la clase Casas por invocacion ==========");
        printHabitables();
    }
}

