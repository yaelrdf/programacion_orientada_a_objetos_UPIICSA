// Práctica 7

import java.util.Scanner;

class MayorDeTres {
    public static void ejecutar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPROGRAMA: Mayor de tres números");

        System.out.print("Ingrese el primer número: ");
        int a = scanner.nextInt();
        System.out.print("Ingrese el segundo número: ");
        int b = scanner.nextInt();
        System.out.print("Ingrese el tercer número: ");
        int c = scanner.nextInt();

        int mayor = Math.max(Math.max(a, b), c);

        System.out.println("El mayor de los tres números es: " + mayor);
    }
}