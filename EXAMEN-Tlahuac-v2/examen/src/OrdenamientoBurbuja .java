import java.util.ArrayList;
import java.util.Scanner;

class OrdenamientoBurbuja {
    private Scanner scanner = new Scanner(System.in);
    
    // Método para ordenamiento burbuja con ArrayList de enteros
    public void ordenamientoBurbujaEnteros() {
        ArrayList<Integer> numeros = new ArrayList<>();
        
        System.out.print("¿Cuántos números enteros deseas ingresar para ordenar? ");
        int cantidad = scanner.nextInt();
        
        // Validar que se ingrese al menos un número
        if (cantidad <= 0) {
            System.out.println("❌ Error: Debes ingresar al menos un número.");
            return;
        }
        
        // Solicitar datos al usuario con validación
        System.out.println("Ingresa los números enteros:");
        for (int j = 0; j < cantidad; j++) {
            boolean entradaValida = false;
            while (!entradaValida) {
                try {
                    System.out.print("Número " + (j + 1) + ": ");
                    int numero = scanner.nextInt();
                    numeros.add(numero);
                    entradaValida = true;
                } catch (Exception e) {
                    System.out.println("❌ Error: Debes ingresar un número entero válido. Inténtalo de nuevo.");
                    scanner.nextLine(); // Limpiar buffer
                }
            }
        }
        
        // Mostrar arreglo original
        System.out.println("\n📋 Arreglo Original:");
        System.out.println("Tamaño del arreglo: " + numeros.size());
        for (int i = 0; i < numeros.size(); i++) {
            System.out.print(numeros.get(i));
            if (i < numeros.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        
        // Aplicar algoritmo burbuja
        System.out.println("\n🔄 Aplicando algoritmo de ordenamiento burbuja...");
        ArrayList<Integer> numerosOrdenados = new ArrayList<>(numeros); // Copia para no modificar el original
        
        // Implementación del algoritmo burbuja
        int n = numerosOrdenados.size();
        boolean intercambio;
        int iteracion = 1;
        
        for (int i = 0; i < n - 1; i++) {
            intercambio = false;
            System.out.println("\n--- Iteración " + iteracion + " ---");
            
            for (int j = 0; j < n - i - 1; j++) {
                // Comparar elementos adyacentes
                if (numerosOrdenados.get(j) > numerosOrdenados.get(j + 1)) {
                    // Intercambiar elementos
                    int temp = numerosOrdenados.get(j);
                    numerosOrdenados.set(j, numerosOrdenados.get(j + 1));
                    numerosOrdenados.set(j + 1, temp);
                    intercambio = true;
                    
                    System.out.println("Intercambio: " + numerosOrdenados.get(j + 1) + " ↔ " + numerosOrdenados.get(j));
                }
            }
            
            // Mostrar estado actual del arreglo
            System.out.print("Estado actual: ");
            for (int k = 0; k < numerosOrdenados.size(); k++) {
                System.out.print(numerosOrdenados.get(k));
                if (k < numerosOrdenados.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
            
            // Si no hubo intercambios, el arreglo ya está ordenado
            if (!intercambio) {
                System.out.println("✅ No se realizaron más intercambios. El arreglo ya está ordenado.");
                break;
            }
            
            iteracion++;
        }
        
        // Mostrar resultado final
        System.out.println("\n✨ Arreglo Ordenado (Algoritmo Burbuja):");
        System.out.println("Tamaño del arreglo: " + numerosOrdenados.size());
        for (int i = 0; i < numerosOrdenados.size(); i++) {
            System.out.println("Posición " + i + ": " + numerosOrdenados.get(i));
        }
        
        // Mostrar estadísticas
        System.out.println("\n📊 Estadísticas del ordenamiento:");
        System.out.println("Número de elementos: " + n);
        System.out.println("Iteraciones realizadas: " + (iteracion - 1));
        System.out.println("Comparaciones máximas posibles: " + (n * (n - 1) / 2));
    }
    

}