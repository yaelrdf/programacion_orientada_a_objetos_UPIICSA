public class Departamentos  extends Habitables{
    protected boolean elevador;
    protected float cuotaMantenimiento;

    //getters setters
    public boolean getElevador(){return elevador;}
    public void setElevador(boolean elevador){this.elevador=elevador;}

    public float getCuotaMantenimiento(){return cuotaMantenimiento;}
    public void setCuotaMantenimiento(float cuotaMantenimiento){this.cuotaMantenimiento=cuotaMantenimiento;}

    //Metodo demostracion
    public void printDepartamentos(){
        System.out.println("==========Imprimiendo desde la clase Departamentos por invocacion ==========");
        printHabitables();
    }
}

