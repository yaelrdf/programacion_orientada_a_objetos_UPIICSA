package com.centrocultural.models;

import java.time.LocalDateTime;

public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String contrasena;
    private String nombreEmpleado;
    private String areaAsignada;
    private LocalDateTime fechaCreacion;

    // Constructor vacío
    public Usuario() {}

    // Constructor completo
    public Usuario(int idUsuario, String nombreUsuario, String contrasena, 
                   String nombreEmpleado, String areaAsignada, LocalDateTime fechaCreacion) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombreEmpleado = nombreEmpleado;
        this.areaAsignada = areaAsignada;
        this.fechaCreacion = fechaCreacion;
    }

    // Constructor sin ID (para nuevos usuarios)
    public Usuario(String nombreUsuario, String contrasena, 
                   String nombreEmpleado, String areaAsignada) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombreEmpleado = nombreEmpleado;
        this.areaAsignada = areaAsignada;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getAreaAsignada() {
        return areaAsignada;
    }

    public void setAreaAsignada(String areaAsignada) {
        this.areaAsignada = areaAsignada;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean esAdministrador() {
        return "administracion".equalsIgnoreCase(this.areaAsignada);
    }

    public boolean esContabilidad() {
        return "contabilidad".equalsIgnoreCase(this.areaAsignada);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", nombreEmpleado='" + nombreEmpleado + '\'' +
                ", areaAsignada='" + areaAsignada + '\'' +
                '}';
    }
}