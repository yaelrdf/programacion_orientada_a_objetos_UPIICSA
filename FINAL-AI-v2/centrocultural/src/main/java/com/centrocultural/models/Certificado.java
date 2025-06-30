package com.centrocultural.models;

import java.time.LocalDate;

public class Certificado {
    private int idCertificado;
    private String nombreCertificado;
    private LocalDate fechaObtencion;
    private String archivoPath;

    public Certificado() {}

    public Certificado(int idCertificado, String nombreCertificado, 
                      LocalDate fechaObtencion, String archivoPath) {
        this.idCertificado = idCertificado;
        this.nombreCertificado = nombreCertificado;
        this.fechaObtencion = fechaObtencion;
        this.archivoPath = archivoPath;
    }

    // Getters y Setters
    public int getIdCertificado() { 
        return idCertificado; 
    }
    
    public void setIdCertificado(int idCertificado) { 
        this.idCertificado = idCertificado; 
    }
    
    public String getNombreCertificado() { 
        return nombreCertificado; 
    }
    
    public void setNombreCertificado(String nombreCertificado) { 
        this.nombreCertificado = nombreCertificado; 
    }
    
    public LocalDate getFechaObtencion() { 
        return fechaObtencion; 
    }
    
    public void setFechaObtencion(LocalDate fechaObtencion) { 
        this.fechaObtencion = fechaObtencion; 
    }
    
    public String getArchivoPath() { 
        return archivoPath; 
    }
    
    public void setArchivoPath(String archivoPath) { 
        this.archivoPath = archivoPath; 
    }
    
    @Override
    public String toString() {
        return "Certificado{" +
                "idCertificado=" + idCertificado +
                ", nombreCertificado='" + nombreCertificado + '\'' +
                ", fechaObtencion=" + fechaObtencion +
                '}';
    }
}