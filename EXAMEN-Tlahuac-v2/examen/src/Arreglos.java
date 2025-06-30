import java.util.Scanner;
class Arreglos {
    // Método con WHILE para mostrar arreglo de enteros
    public void mostrarArregloEnteros() {
        int[] numeros = {10, 20, 30, 40, 50};
        int tamaño = numeros.length;
        int i = 0;

        System.out.println("\nArreglo de Enteros (usando while):");
        while (i < tamaño) {
            System.out.println("Índice " + i + ": " + numeros[i]);
            i++;
        }
    }

    // Método con FOR para flotantes (se mantiene igual)
    public void mostrarArregloFlotantes() {
        float[] numeros = {1.1f, 2.2f, 3.3f, 4.4f, 5.5f};
        int tamaño = numeros.length;
        System.out.println("\nArreglo de Flotantes (usando for):");
        for (int i = 0; i < tamaño; i++) {
            System.out.println("Índice " + i + ": " + numeros[i]);
        }
    }

    // Método con DO-WHILE para doubles
    public void mostrarArregloDoubles() {
        double[] numeros = {1.11, 2.22, 3.33, 4.44, 5.55};
        int tamaño = numeros.length;
        int i = 0;

        System.out.println("\nArreglo de Doubles (usando do-while):");
        if (tamaño > 0) {  // Verificación para evitar bucle infinito si el arreglo está vacío
            do {
                System.out.println("Índice " + i + ": " + numeros[i]);
                i++;
            } while (i < tamaño);
        }
    }

    // Método con FOR para strings (se mantiene igual)
    public void mostrarArregloStrings() {
        String[] palabras = {"Java", "Python", "C++", "JavaScript", "Ruby"};
        int tamaño = palabras.length;
        System.out.println("\nArreglo de Strings (usando for):");
        for (int i = 0; i < tamaño; i++) {
            System.out.println("Índice " + i + ": " + palabras[i]);
        }
    }
}