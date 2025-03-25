import java.util.List;

class Sala {
    private List<String> funciones;
    private int totalBoletosVendidos;   

    //Constructor
    public Sala(List<String> Funciones, int totalBoletosVendidos){
        this.funciones=funciones;
        this.totalBoletosVendidos=totalBoletosVendidos;
    }

    //Getters y setters
    public List getFunciones(){return funciones;}
    public void setFunciones(List<String> funciones){this.funciones=funciones;}

    public int getBoletosVendidos(){return totalBoletosVendidos;}
    public void setBoletosVendidos(int totalBoletosVendidos){this.totalBoletosVendidos=totalBoletosVendidos;}
}
