import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Arreglos Dinámicos");
            System.out.println("2. Arreglos Estáticos");
            System.out.println("3. Ordenamiento Burbuja");
            System.out.println("4. Ordenamiento de Arreglos Numéricos");
            System.out.println("5. Salir");
            System.out.print("Seleccione un programa (1-5): ");
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                
                switch (choice) {
                    case 1:
                        System.out.println("\n=== ARREGLOS DINÁMICOS ===");
                        ArreglosDinamicos programa1 = new ArreglosDinamicos();
                        programa1.menuPrincipal();
                        break;
                    case 2:
                        System.out.println("\n=== ARREGLOS ESTÁTICOS ===");
                        MainArreglos.main(args);
                        break;
                    case 3:
                        System.out.println("\n=== ORDENAMIENTO BURBUJA ===");
                        MainOrdenamientoBurbuja.main(args);
                        break;
                    case 4:
                        System.out.println("\n=== ORDENAMIENTO DE ARREGLOS NUMÉRICOS ===");
                        ProgramaOrdenamientoArreglos.main(args);
                        break;
                    case 5:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, seleccione 1-5.");
                }
                
                if (choice >= 1 && choice <= 4) {
                    System.out.println("\nPresione Enter para volver al menú principal...");
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.out.println("Error: Por favor ingrese un número válido.");
                scanner.nextLine(); // Clear buffer
                choice = 0; // Reset choice to continue loop
            }
            
        } while (choice != 5);
        
        scanner.close();
    }
}