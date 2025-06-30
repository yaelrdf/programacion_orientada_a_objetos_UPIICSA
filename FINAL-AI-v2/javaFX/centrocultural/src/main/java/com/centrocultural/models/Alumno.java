package com.centrocultural.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Alumno {
    private int noExpediente;
    private String nombreCompleto;
    private LocalDate fechaNacimiento;
    private LocalDateTime fechaRegistro;
    private List<Inscripcion> inscripciones;

    // Constructor vacío
    public Alumno() {
        this.inscripciones = new ArrayList<>();
    }

    // Constructor completo
    public Alumno(int noExpediente, String nombreCompleto, LocalDate fechaNacimiento, 
                  LocalDateTime fechaRegistro) {
        this.noExpediente = noExpediente;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.inscripciones = new ArrayList<>();
    }

    // Constructor sin ID (para nuevos alumnos)
    public Alumno(String nombreCompleto, LocalDate fechaNacimiento) {
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.inscripciones = new ArrayList<>();
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

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    // Métodos adicionales
    public void agregarInscripcion(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
    }

    public List<Inscripcion> getInscripcionesActivas() {
        List<Inscripcion> activas = new ArrayList<>();
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.isActiva()) {
                activas.add(inscripcion);
            }
        }
        return activas;
    }

    public List<Inscripcion> getInscripcionesPagadas() {
        List<Inscripcion> pagadas = new ArrayList<>();
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.isPagada()) {
                pagadas.add(inscripcion);
            }
        }
        return pagadas;
    }

    public int getEdad() {
        if (fechaNacimiento != null) {
            return java.time.Period.between(fechaNacimiento, LocalDate.now()).getYears();
        }
        return 0;
    }

    public String getActividadesInscritas() {
        StringBuilder sb = new StringBuilder();
        List<Inscripcion> activas = getInscripcionesActivas();
        for (int i = 0; i < activas.size(); i++) {
            if (activas.get(i).getGrupo() != null && activas.get(i).getGrupo().getActividad() != null) {
                sb.append(activas.get(i).getGrupo().getActividad().getNombre());
                if (i < activas.size() - 1) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "noExpediente=" + noExpediente +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", inscripcionesActivas=" + getInscripcionesActivas().size() +
                '}';
    }
}