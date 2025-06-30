package com.centrocultural.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pago {
    private int idPago;
    private Inscripcion inscripcion;
    private BigDecimal monto;
    private LocalDate fechaPago;
    private String concepto;
    private Usuario registradoPor;

    // Constructor vacío
    public Pago() {}

    // Constructor completo
    public Pago(int idPago, Inscripcion inscripcion, BigDecimal monto, 
                LocalDate fechaPago, String concepto, Usuario registradoPor) {
        this.idPago = idPago;
        this.inscripcion = inscripcion;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.concepto = concepto;
        this.registradoPor = registradoPor;
    }

    // Constructor sin ID (para nuevos pagos)
    public Pago(Inscripcion inscripcion, BigDecimal monto, LocalDate fechaPago, 
                String concepto, Usuario registradoPor) {
        this.inscripcion = inscripcion;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.concepto = concepto;
        this.registradoPor = registradoPor;
    }

    // Getters y Setters
    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }

    public BigDecimal getMonto() {
        return monto;
    }
    
    public double getMontoDouble() {
        return monto != null ? monto.doubleValue() : 0.0;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Usuario getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(Usuario registradoPor) {
        this.registradoPor = registradoPor;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "idPago=" + idPago +
                ", monto=" + monto +
                ", fechaPago=" + fechaPago +
                ", concepto='" + concepto + '\'' +
                ", registradoPor=" + (registradoPor != null ? registradoPor.getNombreUsuario() : "null") +
                '}';
    }
}