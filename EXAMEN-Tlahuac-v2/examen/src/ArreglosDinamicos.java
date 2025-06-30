import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ArreglosDinamicos {
    
    // Arreglos dinámicos
    private ArrayList<Integer> numeros = new ArrayList<>();
    private ArrayList<String> palabras = new ArrayList<>();
    
    public void menuPrincipal() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        
        do {
            System.out.println("\n--- ARREGLOS DINÁMICOS ---");
            System.out.println("1. Agregar número");
            System.out.println("2. Agregar palabra");
            System.out.println("3. Mostrar números");
            System.out.println("4. Mostrar palabras");
            System.out.println("5. Ordenar números");
            System.out.println("6. Ordenar palabras");
            System.out.println("7. Tamaño de arreglos");
            System.out.println("8. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            
            switch(opcion) {
                case 1:
                    agregarNumero(sc);
                    break;
                case 2:
                    agregarPalabra(sc);
                    break;
                case 3:
                    mostrarNumeros();
                    break;
                case 4:
                    mostrarPalabras();
                    break;
                case 5:
                    ordenarNumeros();
                    break;
                case 6:
                    ordenarPalabras();
                    break;
                case 7:
                    mostrarTamanos();
                    break;
                case 8:
                    System.out.println("¡Adiós!");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while(opcion != 8);
        
        sc.close();
    }
    
    public void agregarNumero(Scanner sc) {
        System.out.print("Ingrese un número: ");
        int num = sc.nextInt();
        numeros.add(num);  // add()
        System.out.println("Número agregado!");
    }
    
    public void agregarPalabra(Scanner sc) {
        System.out.print("Ingrese una palabra: ");
        String palabra = sc.next();
        palabras.add(palabra);  // add()
        System.out.println("Palabra agregada!");
    }
    
    public void mostrarNumeros() {
        if(numeros.isEmpty()) {
            System.out.println("No hay números");
            return;
        }
        
        System.out.println("\nNúmeros:");
        for(int i = 0; i < numeros.size(); i++) {
            System.out.println("[" + i + "] = " + numeros.get(i));
        }
    }
    
    public void mostrarPalabras() {
        if(palabras.isEmpty()) {
            System.out.println("No hay palabras");
            return;
        }
        
        System.out.println("\nPalabras:");
        for(int i = 0; i < palabras.size(); i++) {
            System.out.println("[" + i + "] = " + palabras.get(i));
        }
    }
    
    public void ordenarNumeros() {
        if(numeros.isEmpty()) {
            System.out.println("No hay números para ordenar");
            return;
        }
        
        Collections.sort(numeros);  // sort()
        System.out.println("Números ordenados!");
        mostrarNumeros();
    }
    
    public void ordenarPalabras() {
        if(palabras.isEmpty()) {
            System.out.println("No hay palabras para ordenar");
            return;
        }
        
        Collections.sort(palabras);  // sort()
        System.out.println("Palabras ordenadas alfabéticamente!");
        mostrarPalabras();
    }
    
    public void mostrarTamanos() {
        System.out.println("\nTamaños de arreglos:");
        System.out.println("Números: " + numeros.size());  // length equivalente
        System.out.println("Palabras: " + palabras.size()); // length equivalente
    }
    
    public static void main(String[] args) {
        ArreglosDinamicos programa = new ArreglosDinamicos();
        programa.menuPrincipal();
    }
}