import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

	switch (opcion) {
		case 1:
			HolaMundo.mostrar();
		break;
		case 2:
			CapturaDatos.ejecutar();
        break;
		case 3:
			CalculadoraApp.iniciar();
        break;
		case 4:
			MayorDeTres.ejecutar();
        break;
		case 5:
			System.out.println("Saliendo del programa. ¡Hasta la proxima brother!");
        break;
    default:
        System.out.println("Opción no válida. Intente nuevamente.");
}


        } while (opcion != 5);

        scanner.close();
    }

    public static void mostrarMenu() {
		System.out.println("\nMENÚ PRINCIPAL");
		System.out.println("1. Hola Mundo");
		System.out.println("2. Capturar Datos con Scanner");
		System.out.println("3. Calculadora");
		System.out.println("4. Mayor de Tres Números");
		System.out.println("5. Salir");
	}

}