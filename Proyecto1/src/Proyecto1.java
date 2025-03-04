import java.util.ArrayList;
import java.util.Scanner;

public class Proyecto1 {
    public static void main(String[] args) {
        Persona objPersona1 = new Persona("Yael", "Saldana", "Flores");
        
        // Create ArrayList
        ArrayList<Persona> LstPersonas = new ArrayList<>();
        LstPersonas.add(objPersona1);
        
        for (int i = 0; i < 3; i++) {
            // Lectura de informacion del usuario
            Scanner entrada = new Scanner(System.in);
            System.out.println("Proporcione un nombre");
            String nombre = entrada.next();
            System.out.println("Proporcione un apellido Paterno");
            String aPaterno = entrada.next();
            System.out.println("Proporcione un apellido materno");
            String aMaterno = entrada.next();
            Persona objPersona3 = new Persona(nombre, aPaterno, aMaterno);
            LstPersonas.add(objPersona3);
        }
        
        for (int i = 0; i < LstPersonas.size(); i++) {
            Persona objPersona3 = LstPersonas.get(i);
            System.out.println("Nombres: " + objPersona3.getNombre());
            System.out.println("Apellido Paterno: " + objPersona3.getaPaterno());
            System.out.println("Apellido Materno: " + objPersona3.getaMaterno());
        }
    }  
}