/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ventanas;

/**
 *
 * @author ilian
 */
public class Persona {
    private String nombre;
    private String aPaterno;
    private String aMaterno;
    private int edad;
    private boolean soltero;

    public Persona(String nombre, String aPaterno, String aMaterno) {
        this.nombre = nombre;
        this.aPaterno = aPaterno;
        this.aMaterno = aMaterno;
    }

    public String getNombre() {return nombre;}
    public String getaPaterno() {return aPaterno;}
    public String getaMaterno() {return aMaterno;}
    public int getEdad() {return edad;}
    public boolean isSoltero() {return soltero;}

    public void setEdad(int edad) 
        {this.edad = edad;}
    public void setSoltero(boolean soltero) 
        {this.soltero = soltero;}
    
    
    
}
