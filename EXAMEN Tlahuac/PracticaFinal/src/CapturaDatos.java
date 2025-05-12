// Práctica 3 (corregido)

import java.util.Scanner;

class CapturaDatos {
    public static void ejecutar() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite su nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Digite su boleta: ");
        int boleta = scanner.nextInt();

        System.out.print("Digite su grupo: ");
        char grupo = scanner.next().charAt(0);

        System.out.print("Digite su promedio: ");
        float promedio = scanner.nextFloat();

        System.out.println("\nDatos capturados:");
        System.out.println("Nombre: " + nombre);
        System.out.println("Boleta: " + boleta);
        System.out.println("Grupo: " + grupo);
        System.out.println("Promedio: " + promedio);
    }
}