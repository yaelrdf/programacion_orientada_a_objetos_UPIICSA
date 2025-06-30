package com.centrocultural.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Instructor {
    private int noExpediente;
    private String nombreCompleto;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaRegistro;
    private List<Actividad> actividadesAutorizadas;
    private List<Certificado> certificados;
    private List<DocumentoIdentidad> documentos;

    // Constructor vacío
    public Instructor() {
        this.actividadesAutorizadas = new ArrayList<>();
        this.certificados = new ArrayList<>();
        this.documentos = new ArrayList<>();
    }

    // Constructor completo
    public Instructor(int noExpediente, String nombreCompleto, LocalDate fechaNacimiento, 
                      LocalDateTime fechaRegistro) {
        this.noExpediente = noExpediente;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.actividadesAutorizadas = new ArrayList<>();
        this.certificados = new ArrayList<>();
        this.documentos = new ArrayList<>();
    }

    // Constructor sin ID (para nuevos instructores)
    public Instructor(String nombreCompleto, LocalDate fechaNacimiento) {
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.actividadesAutorizadas = new ArrayList<>();
        this.certificados = new ArrayList<>();
        this.documentos = new ArrayList<>();
    }

    // Getters y Setters
    public int getNoExpediente() {
        return noExpediente;
    }

    public void setNoExpediente(int noExpediente) {
        this.noExpediente = noExpediente;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public List<Actividad> getActividadesAutorizadas() {
        return actividadesAutorizadas;
    }

    public void setActividadesAutorizadas(List<Actividad> actividadesAutorizadas) {
        this.actividadesAutorizadas = actividadesAutorizadas;
    }

    public List<Certificado> getCertificados() {
        return certificados;
    }

    public void setCertificados(List<Certificado> certificados) {
        this.certificados = certificados;
    }

    public List<DocumentoIdentidad> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoIdentidad> documentos) {
        this.documentos = documentos;
    }

    // Métodos adicionales
    public void agregarActividad(Actividad actividad) {
        if (!actividadesAutorizadas.contains(actividad)) {
            actividadesAutorizadas.add(actividad);
        }
    }

    public void agregarCertificado(Certificado certificado) {
        certificados.add(certificado);
    }

    public void agregarDocumento(DocumentoIdentidad documento) {
        documentos.add(documento);
    }

    public int getEdad() {
        if (fechaNacimiento != null) {
            return java.time.Period.between(fechaNacimiento, LocalDate.now()).getYears();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "noExpediente=" + noExpediente +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", actividadesAutorizadas=" + actividadesAutorizadas.size() +
                '}';
    }
}