public class App{
    public static void main(String[] args) throws Exception {
        //Objetos
        LocalComercial localComercialOBJ = new LocalComercial();
        Casas casasOBJ = new Casas();
        Departamentos departamentosOBJ = new Departamentos();
        Terreno terrenoOBJ = new Terreno();
        Intermediarios intermediariosOBJ = new Intermediarios();
        Propios propiosOBJ = new Propios();
        Venta ventaOBJ = new Venta();
        Renta rentaOBJ = new Renta();

        //Main
        System.out.println("========Impresiones con el fin de demostrar las herencias=======");
        System.out.println("Se imprimira desde los ultimos hijos del arbol, cada impresion se diferenciara con u encabezado unico y un final, aparte de un espacio\n");
        
        //Impresion desde las ultimas clases de los arboles o los hijos
        rentaOBJ.printRenta();
        ventaOBJ.printVenta();
        intermediariosOBJ.printIntermediarios();
        propiosOBJ.printPropios();
        terrenoOBJ.printTerrenos();
        departamentosOBJ.printDepartamentos();
        casasOBJ.printCasas();
        localComercialOBJ.printComercial();

}
}