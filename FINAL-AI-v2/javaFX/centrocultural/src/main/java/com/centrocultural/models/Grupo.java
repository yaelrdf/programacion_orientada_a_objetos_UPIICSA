package com.centrocultural.models;

import java.util.ArrayList;
import java.util.List;

public class Grupo {
    private int idGrupo;
    private Actividad actividad;
    private Instructor instructor;
    private String nombreGrupo;
    private String salon;
    private String edificio;
    private String horario;
    private boolean activo;
    private List<Material> materiales;
    private List<Inscripcion> inscripciones;

    // Constructor vacío
    public Grupo() {
        this.activo = true;
        this.materiales = new ArrayList<>();
        this.inscripciones = new ArrayList<>();
    }

    // Constructor completo
    // Modificar constructores para eliminar salon y edificio
    public Grupo(int idGrupo, Actividad actividad, Instructor instructor, 
                String nombreGrupo, String horario, boolean activo) {
        this.idGrupo = idGrupo;
        this.actividad = actividad;
        this.instructor = instructor;
        this.nombreGrupo = nombreGrupo;
        this.horario = horario;
        this.activo = activo;
        this.materiales = new ArrayList<>();
        this.inscripciones = new ArrayList<>();
    }

    public Grupo(Actividad actividad, Instructor instructor, String nombreGrupo, 
                String horario) {
        this.actividad = actividad;
        this.instructor = instructor;
        this.nombreGrupo = nombreGrupo;
        this.horario = horario;
        this.activo = true;
        this.materiales = new ArrayList<>();
        this.inscripciones = new ArrayList<>();
    }

    // Getters y Setters
    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Material> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<Material> materiales) {
        this.materiales = materiales;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    // Métodos adicionales
    public void agregarMaterial(Material material) {
        if (!materiales.contains(material)) {
            materiales.add(material);
        }
    }

    public void agregarInscripcion(Inscripcion inscripcion) {
        if (!inscripciones.contains(inscripcion)) {
            inscripciones.add(inscripcion);
        }
    }

    public int getNumeroAlumnos() {
        int count = 0;
        for (Inscripcion inscripcion : inscripciones) {
            if (inscripcion.isActiva() && inscripcion.isPagada()) {
                count++;
            }
        }
        return count;
    }

    public String getUbicacion() {
    return actividad != null ? actividad.getAreaAsignada() : "No asignada";
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "idGrupo=" + idGrupo +
                ", nombreGrupo='" + nombreGrupo + '\'' +
                ", actividad=" + (actividad != null ? actividad.getNombre() : "null") +
                ", instructor=" + (instructor != null ? instructor.getNombreCompleto() : "null") +
                ", alumnos=" + getNumeroAlumnos() +
                '}';
    }
}