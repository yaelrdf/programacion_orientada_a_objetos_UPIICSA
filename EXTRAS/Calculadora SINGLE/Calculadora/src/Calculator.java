import java.util.Scanner;

public class Calculator {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getEleccion();
            
            if (choice == 5) {
                System.out.println("Gracias por ejecutar");
                running = false;
            } else if (choice >= 1 && choice <= 4) {
                operar(choice);
            }
        }
        
        scanner.close();
    }
    
    // Display the main menu
    private static void displayMenu() {
        System.out.println("\n===== Calculadora que calcula =====");
        System.out.println("1. Suma");
        System.out.println("2. Resta");
        System.out.println("3. Multiplicacion");
        System.out.println("4. Division");
        System.out.println("5. Salir");
        System.out.print("Capture la operacion a realizar: ");
    }
    
    // Get and validate the menu choice
    private static int getEleccion() {
        int choice = 0;
        boolean valido = false;
        
        while (!valido) {
            try {
                String input = scanner.nextLine().trim();
                choice = Integer.parseInt(input);
                
                if (choice >= 1 && choice <= 5) {
                    valido = true;
                } else {
                    System.out.print("Operacion invalida, por favor vuelva a internat: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Entrada invalida, utilice unicamente numeros");
            }
        }
        
        return choice;
    }
    
    // Perform the selected operation
    private static void operar(int operation) {
        String operationName = "";
        switch (operation) {
            case 1:
                operationName = "Suma";
                break;
            case 2:
                operationName = "Subtraction";
                break;
            case 3:
                operationName = "Multiplication";
                break;
            case 4:
                operationName = "Division";
                break;
        }
        
        System.out.println("\n" + operationName + " Operation");
        System.out.println("Capture los valores uno a uno. Capture 'F' para finalizar.");
        
        double resultado = getFirstnumero();
        boolean isPrimeraIteracion = true;
        
        while (true) {
            System.out.print("Capture el siguiente valor (o 'F' para finalizar): ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("F")) {
                break;
            }
            
            try {
                double numero = Double.parseDouble(input);
                
                //Para la primera iteracion solo se almacena el valor
                if (isPrimeraIteracion && operation == 2) {
                    resultado = numero;
                    isPrimeraIteracion = false;
                    continue;
                }
                
                //Manejar divisiones con 0
                if (operation == 4 && numero == 0) {
                    System.out.println("Error: No es posible dividir entre Zero. Intentelo nuevamente.");
                    continue;
                }
                
                //Switch para las operaciones
                switch (operation) {
                    case 1: // Suma
                        resultado += numero;
                        break;
                    case 2: // Subtraction
                        resultado -= numero;
                        break;
                    case 3: // Multiplication
                        resultado *= numero;
                        break;
                    case 4: // Division
                        resultado /= numero;
                        break;
                }
                
                System.out.println("Resultado actual: " + resultado);
                
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Capture un numero valido");
            }
        }
        
        System.out.println("\nResultado final: " + resultado);
    }
    
    // Get the first numero for the operation
    private static double getFirstnumero() {
        double numero = 0;
        boolean valido = false;
        
        while (!valido) {
            System.out.print("Capture el primer valor: ");
            String input = scanner.nextLine().trim();
            
            try {
                numero = Double.parseDouble(input);
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Capture un numero valido");
            }
        }
        
        return numero;
    }
}