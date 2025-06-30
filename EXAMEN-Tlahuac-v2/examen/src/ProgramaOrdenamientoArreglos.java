import java.util.Scanner;
import java.util.Arrays;

// Clase abstracta base para los arreglos
abstract class ArregloNumerico {
    protected int tamaño;
    protected boolean estaLleno;
    
    public ArregloNumerico(int tamaño) {
        this.tamaño = tamaño;
        this.estaLleno = false;
    }
    
    public abstract void leerDatos(Scanner scanner);
    public abstract void mostrarArreglo();
    public abstract void ordenar(boolean ascendente);
    public abstract String getTipo();
    
    public boolean estaLleno() {
        return estaLleno;
    }
}

// Clase para arreglos Float
class ArregloFloat extends ArregloNumerico {
    private Float[] arreglo;
    
    public ArregloFloat(int tamaño) {
        super(tamaño);
        this.arreglo = new Float[tamaño];
    }
    
    @Override
    public void leerDatos(Scanner scanner) {
        System.out.println("\nIngresando datos para arreglo Float:");
        for (int i = 0; i < tamaño; i++) {
            System.out.print("Ingrese el elemento " + (i + 1) + ": ");
            while (!scanner.hasNextFloat()) {
                System.out.println("Error: Debe ingresar un número válido.");
                System.out.print("Ingrese el elemento " + (i + 1) + ": ");
                scanner.next();
            }
            arreglo[i] = scanner.nextFloat();
        }
        estaLleno = true;
        System.out.println("Arreglo Float llenado exitosamente.");
    }
    
    @Override
    public void mostrarArreglo() {
        if (!estaLleno) {
            System.out.println("El arreglo Float está vacío.");
            return;
        }
        System.out.println("\nArreglo Float:");
        for (int i = 0; i < tamaño; i++) {
            System.out.println("Posición [" + i + "]: " + arreglo[i]);
        }
    }
    
    @Override
    public void ordenar(boolean ascendente) {
        if (!estaLleno) {
            System.out.println("El arreglo Float está vacío. No se puede ordenar.");
            return;
        }
        
        if (ascendente) {
            Arrays.sort(arreglo);
            System.out.println("Arreglo Float ordenado de forma ASCENDENTE.");
        } else {
            Arrays.sort(arreglo, (a, b) -> b.compareTo(a));
            System.out.println("Arreglo Float ordenado de forma DESCENDENTE.");
        }
    }
    
    @Override
    public String getTipo() {
        return "Float";
    }
}

// Clase para arreglos Double
class ArregloDouble extends ArregloNumerico {
    private Double[] arreglo;
    
    public ArregloDouble(int tamaño) {
        super(tamaño);
        this.arreglo = new Double[tamaño];
    }
    
    @Override
    public void leerDatos(Scanner scanner) {
        System.out.println("\nIngresando datos para arreglo Double:");
        for (int i = 0; i < tamaño; i++) {
            System.out.print("Ingrese el elemento " + (i + 1) + ": ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Error: Debe ingresar un número válido.");
                System.out.print("Ingrese el elemento " + (i + 1) + ": ");
                scanner.next();
            }
            arreglo[i] = scanner.nextDouble();
        }
        estaLleno = true;
        System.out.println("Arreglo Double llenado exitosamente.");
    }
    
    @Override
    public void mostrarArreglo() {
        if (!estaLleno) {
            System.out.println("El arreglo Double está vacío.");
            return;
        }
        System.out.println("\nArreglo Double:");
        for (int i = 0; i < tamaño; i++) {
            System.out.println("Posición [" + i + "]: " + arreglo[i]);
        }
    }
    
    @Override
    public void ordenar(boolean ascendente) {
        if (!estaLleno) {
            System.out.println("El arreglo Double está vacío. No se puede ordenar.");
            return;
        }
        
        if (ascendente) {
            Arrays.sort(arreglo);
            System.out.println("Arreglo Double ordenado de forma ASCENDENTE.");
        } else {
            Arrays.sort(arreglo, (a, b) -> b.compareTo(a));
            System.out.println("Arreglo Double ordenado de forma DESCENDENTE.");
        }
    }
    
    @Override
    public String getTipo() {
        return "Double";
    }
}

// Clase principal con el menú
public class ProgramaOrdenamientoArreglos {
    private static ArregloFloat arregloFloat;
    private static ArregloDouble arregloDouble;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        int opcion;
        
        do {
            mostrarMenu();
            opcion = leerOpcion();
            procesarOpcion(opcion);
        } while (opcion != 5);
        
        scanner.close();
        System.out.println("\n¡Gracias por usar el programa!");
    }
    
    private static void mostrarMenu() {
        System.out.println("\n============ MENÚ ============");
        System.out.println("1. Leer Arreglo");
        System.out.println("2. Realizar comparación (Float o Double)");
        System.out.println("3. Mostrar Arreglo");
        System.out.println("4. Ordenamiento (ASC o DESC)");
        System.out.println("5. Salir");
        System.out.println("==============================");
        System.out.print("Seleccione una opción: ");
    }
    
    private static int leerOpcion() {
        while (!scanner.hasNextInt()) {
            System.out.println("Error: Debe ingresar un número entero.");
            System.out.print("Seleccione una opción: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
    
    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                leerArreglo();
                break;
            case 2:
                realizarComparacion();
                break;
            case 3:
                mostrarArreglos();
                break;
            case 4:
                ordenarArreglo();
                break;
            case 5:
                break;
            default:
                System.out.println("Opción inválida. Por favor, intente de nuevo.");
        }
    }
    
    private static void leerArreglo() {
        System.out.println("\n¿Qué tipo de arreglo desea crear?");
        System.out.println("1. Float");
        System.out.println("2. Double");
        System.out.print("Seleccione el tipo: ");
        
        int tipo = leerOpcion();
        
        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo inválido.");
            return;
        }
        
        System.out.print("Ingrese el tamaño del arreglo: ");
        int tamaño = leerOpcion();
        
        if (tamaño <= 0) {
            System.out.println("El tamaño debe ser mayor que 0.");
            return;
        }
        
        if (tipo == 1) {
            arregloFloat = new ArregloFloat(tamaño);
            arregloFloat.leerDatos(scanner);
        } else {
            arregloDouble = new ArregloDouble(tamaño);
            arregloDouble.leerDatos(scanner);
        }
    }
    
    private static void realizarComparacion() {
        System.out.println("\n=== Comparación de tipos de arreglos ===");
        
        if (arregloFloat != null && arregloFloat.estaLleno()) {
            System.out.println("Arreglo Float: CREADO y LLENO");
        } else {
            System.out.println("Arreglo Float: NO CREADO o VACÍO");
        }
        
        if (arregloDouble != null && arregloDouble.estaLleno()) {
            System.out.println("Arreglo Double: CREADO y LLENO");
        } else {
            System.out.println("Arreglo Double: NO CREADO o VACÍO");
        }
        
        if ((arregloFloat == null || !arregloFloat.estaLleno()) && 
            (arregloDouble == null || !arregloDouble.estaLleno())) {
            System.out.println("\nNo hay arreglos creados para comparar.");
        }
    }
    
    private static void mostrarArreglos() {
        System.out.println("\n¿Qué arreglo desea mostrar?");
        System.out.println("1. Float");
        System.out.println("2. Double");
        System.out.println("3. Ambos");
        System.out.print("Seleccione una opción: ");
        
        int opcion = leerOpcion();
        
        switch (opcion) {
            case 1:
                if (arregloFloat != null) {
                    arregloFloat.mostrarArreglo();
                } else {
                    System.out.println("No hay arreglo Float creado.");
                }
                break;
            case 2:
                if (arregloDouble != null) {
                    arregloDouble.mostrarArreglo();
                } else {
                    System.out.println("No hay arreglo Double creado.");
                }
                break;
            case 3:
                if (arregloFloat != null) {
                    arregloFloat.mostrarArreglo();
                } else {
                    System.out.println("No hay arreglo Float creado.");
                }
                System.out.println();
                if (arregloDouble != null) {
                    arregloDouble.mostrarArreglo();
                } else {
                    System.out.println("No hay arreglo Double creado.");
                }
                break;
            default:
                System.out.println("Opción inválida.");
        }
    }
    
    private static void ordenarArreglo() {
        System.out.println("\n¿Qué arreglo desea ordenar?");
        System.out.println("1. Float");
        System.out.println("2. Double");
        System.out.print("Seleccione el tipo: ");
        
        int tipo = leerOpcion();
        ArregloNumerico arreglo = null;
        
        if (tipo == 1) {
            arreglo = arregloFloat;
        } else if (tipo == 2) {
            arreglo = arregloDouble;
        } else {
            System.out.println("Tipo inválido.");
            return;
        }
        
        if (arreglo == null) {
            System.out.println("No hay arreglo " + (tipo == 1 ? "Float" : "Double") + " creado.");
            return;
        }
        
        System.out.println("\n¿Cómo desea ordenar el arreglo?");
        System.out.println("1. Ascendente (menor a mayor)");
        System.out.println("2. Descendente (mayor a menor)");
        System.out.print("Seleccione el orden: ");
        
        int orden = leerOpcion();
        
        if (orden == 1) {
            arreglo.ordenar(true);
        } else if (orden == 2) {
            arreglo.ordenar(false);
        } else {
            System.out.println("Opción de ordenamiento inválida.");
        }
    }
}