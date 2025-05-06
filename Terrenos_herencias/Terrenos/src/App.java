public class App{
    public static void main(String[] args) throws Exception {
        //Obketos
        Venta ventaOBJ = new Venta();
        Renta rentaOBJ = new Renta();

        //Main
        System.out.println("========Impresiones con el fin de demostrar las herencias=======");
        System.out.println("Se imprimira desde los ultimos hijos del arbol, cada impresion se diferenciara con u encabezado unico y un final, aparte de un espacio\n");
        
        //Impresion desde las ultimas clases de los arboles o los hijos
        rentaOBJ.printRenta();
        System.out.println("\n\n");
        ventaOBJ.printVenta();
}
}