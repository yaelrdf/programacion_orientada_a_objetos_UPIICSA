// Práctica 4

import java.util.Scanner;

class CalculadoraApp {
    public static void iniciar() {
        Scanner teclado = new Scanner(System.in);
        boolean activo = true;

        while (activo) {
            System.out.println("\nCALCULADORA");
            System.out.println("1) Sumar");
            System.out.println("2) Restar");
            System.out.println("3) Multiplicar");
            System.out.println("4) Dividir");
            System.out.println("5) Salir");
            System.out.print("Elige una opción: ");
            int eleccion = teclado.nextInt();

            if (eleccion == 5) {
                System.out.println("Saliendo de la calculadora...");
                break;
            }

            System.out.print("Número 1: ");
            double x = teclado.nextDouble();
            System.out.print("Número 2: ");
            double y = teclado.nextDouble();

            double resultado = 0;

            switch (eleccion) {
                case 1: resultado = x + y; break;
                case 2: resultado = x - y; break;
                case 3: resultado = x * y; break;
                case 4:
                    if (y != 0) resultado = x / y;
                    else {
                        System.out.println("Error: división entre cero.");
                        continue;
                    }
                    break;
                default:
                    System.out.println("Opción inválida.");
                    continue;
            }

            System.out.println("Resultado: " + resultado);
        }
    }
}
