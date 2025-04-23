public class App{
    public static void main(String[] args) throws Exception {
        //Objetos
        Terreno terrenoOJB = new Terreno();
        Intermediarios intermediariosOBJ = new Intermediarios();
        Propios propiosOBJ = new Propios();
        Cliente clienteOBJ = new Cliente();
        Venta ventaOBJ = new Venta();
        Renta rentaOBJ = new Renta();

        //Main
        System.out.println("========Impresiones con el fin de demostrar las herencias=======\n");
        rentaOBJ.printRenta();
        System.out.println("\n");
        ventaOBJ.printVenta();
        System.out.println("\n");

        intermediariosOBJ.impresionIntermediarios();
        System.out.println("\n");
        propiosOBJ.printPropios();
    }
}
