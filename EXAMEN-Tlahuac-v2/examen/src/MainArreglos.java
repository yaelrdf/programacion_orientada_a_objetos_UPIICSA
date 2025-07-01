import java.util.Scanner;

public class MainArreglos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Arreglos manager = new Arreglos(); 
        int opcion;

        do {
            System.out.println("\n Menú de Arreglos");
            System.out.println("1. Arreglo de enteros (while)");
            System.out.println("2. Arreglo de flotantes (for)");
            System.out.println("3. Arreglo de doubles (do-while)");
            System.out.println("4. Arreglo de strings (for)");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    manager.mostrarArregloEnteros();
                    break;
                case 2:
                    manager.mostrarArregloFlotantes();
                    break;
                case 3:
                    manager.mostrarArregloDoubles();
                    break;
                case 4:
                    manager.mostrarArregloStrings();
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        } while (opcion != 5);

        scanner.close();
    }
}