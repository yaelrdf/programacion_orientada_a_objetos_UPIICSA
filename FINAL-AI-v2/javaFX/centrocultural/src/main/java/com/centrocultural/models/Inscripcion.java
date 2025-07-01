package com.centrocultural.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Inscripcion {
    private int idInscripcion;
    private Alumno alumno;
    private Grupo grupo;
    private LocalDate fechaInscripcion;
    private boolean activa;
    private List<Pago> pagos;
    private LocalDate fechaVencimiento;

    // Constructor vacío
    public Inscripcion() {
        this.activa = true;
        this.pagos = new ArrayList<>();
        this.fechaVencimiento = LocalDate.now().plusMonths(1); // 1 mes por defecto
    }
    

    // Constructor completo
    public Inscripcion(int idInscripcion, Alumno alumno, Grupo grupo, LocalDate fechaInscripcion, LocalDate fechaVencimiento, boolean activa) {
        this.idInscripcion = idInscripcion;
        this.alumno = alumno;
        this.grupo = grupo;
        this.fechaInscripcion = fechaInscripcion;
        this.activa = activa;
        this.fechaVencimiento = fechaVencimiento;
        this.pagos = new ArrayList<>();
    }

    // Constructor sin ID (para nuevas inscripciones)
     public Inscripcion(Alumno alumno, Grupo grupo, LocalDate fechaInscripcion, LocalDate fechaVencimiento) {
        this.alumno = alumno;
        this.grupo = grupo;
        this.fechaInscripcion = fechaInscripcion;
        this.activa = true;
        this.pagos = new ArrayList<>();
        this.fechaVencimiento = fechaVencimiento;
    }

    // Getters y Setters
    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    // Métodos adicionales
    public void agregarPago(Pago pago) {
        pagos.add(pago);
    }


    public double getTotalPagado() {
        double total = 0;
        for (Pago pago : pagos) {
            //Refactor later
            //total += pago.getMonto();
        }
        return total;
    }

    public Pago getUltimoPago() {
        if (!pagos.isEmpty()) {
            return pagos.get(pagos.size() - 1);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "idInscripcion=" + idInscripcion +
                ", alumno=" + (alumno != null ? alumno.getNombreCompleto() : "null") +
                ", grupo=" + (grupo != null ? grupo.getNombreGrupo() : "null") +
                ", fechaInscripcion=" + fechaInscripcion +
                ", pagada=" + isPagada() +
                '}';
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    // Modificar isPagada para usar el checkbox
    public boolean isPagada() {
        return !pagos.isEmpty(); // O puedes cambiar esto según tu lógica
    }
    
}
