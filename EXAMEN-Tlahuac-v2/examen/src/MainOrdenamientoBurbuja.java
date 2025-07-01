import java.util.Scanner;

public class MainOrdenamientoBurbuja {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrdenamientoBurbuja bubbleSort = new OrdenamientoBurbuja();
        int opcion;
        
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║           ALGORITMO DE ORDENAMIENTO BURBUJA             ║");
        System.out.println("║                                                          ║");
        System.out.println("║ Este programa implementa el algoritmo de ordenamiento   ║");
        System.out.println("║ burbuja (bubble sort) para ordenar números enteros.     ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println();
        
        do {
            System.out.println("🔹 Menú Principal - Algoritmo Burbuja 🔹");
            System.out.println("┌─────────────────────────────────────────────────────────┐");
            System.out.println("│ 1. Ordenar números enteros (Algoritmo Burbuja)         │");
            System.out.println("│ 2. Salir                                               │");
            System.out.println("└─────────────────────────────────────────────────────────┘");
            
            System.out.print("Elige una opción (1-2): ");
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                switch (opcion) {
                    case 1:
                        System.out.println("\n🔄 ORDENAMIENTO BURBUJA - NÚMEROS ENTEROS 🔄");
                        System.out.println("═══════════════════════════════════════════════");
                        bubbleSort.ordenamientoBurbujaEnteros();
                        break;
                    case 2:
                        System.out.println("\n🎉 ¡Gracias por usar el programa!");
                        System.out.println("👋 ¡Hasta la próxima!");
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("\n❌ Opción no válida. Por favor, elige un número del 1 al 2.");
                }
                
                if (opcion == 1) {
                    System.out.println("\n" + "─".repeat(60));
                    System.out.println("Presiona Enter para continuar...");
                    scanner.nextLine();
                    System.out.println();
                }
                
            } catch (Exception e) {
                System.out.println("\n❌ Error: Por favor ingresa un número válido.");
                scanner.nextLine(); // Limpiar buffer en caso de error
                opcion = 0; // Para que no salga del bucle
            }
            
        } while (opcion != 2);
        
        scanner.close();
    }
}