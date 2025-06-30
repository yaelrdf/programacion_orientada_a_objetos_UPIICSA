//package com.centrocultural.models;

import java.util.ArrayList;
import java.util.List;

public class Actividad {
    private int idActividad;
    private String nombre;
    private String clasificacion;
    private String areaAsignada;
    private boolean activa;
    private List<Instructor> instructoresAutorizados;
    private List<Grupo> grupos;

    // Constructor vacío
    public Actividad() {
        this.activa = true;
        this.instructoresAutorizados = new ArrayList<>();
        this.grupos = new ArrayList<>();
    }

    // Constructor completo
    public Actividad(int idActividad, String nombre, String clasificacion, 
                     String areaAsignada, boolean activa) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.clasificacion = clasificacion;
        this.areaAsignada = areaAsignada;
        this.activa = activa;
        this.instructoresAutorizados = new ArrayList<>();
        this.grupos = new ArrayList<>();
    }

    // Constructor sin ID (para nuevas actividades)
    public Actividad(String nombre, String clasificacion, String areaAsignada) {
        this.nombre = nombre;
        this.clasificacion = clasificacion;
        this.areaAsignada = areaAsignada;
        this.activa = true;
        this.instructoresAutorizados = new ArrayList<>();
        this.grupos = new ArrayList<>();
    }

    // Getters y Setters
    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getAreaAsignada() {
        return areaAsignada;
    }

    public void setAreaAsignada(String areaAsignada) {
        this.areaAsignada = areaAsignada;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public List<Instructor> getInstructoresAutorizados() {
        return instructoresAutorizados;
    }

    public void setInstructoresAutorizados(List<Instructor> instructoresAutorizados) {
        this.instructoresAutorizados = instructoresAutorizados;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    // Métodos adicionales
    public void agregarInstructor(Instructor instructor) {
        if (!instructoresAutorizados.contains(instructor)) {
            instructoresAutorizados.add(instructor);
        }
    }

    public void agregarGrupo(Grupo grupo) {
        if (!grupos.contains(grupo)) {
            grupos.add(grupo);
        }
    }

    public List<Grupo> getGruposActivos() {
        List<Grupo> activos = new ArrayList<>();
        for (Grupo grupo : grupos) {
            if (grupo.isActivo()) {
                activos.add(grupo);
            }
        }
        return activos;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "idActividad=" + idActividad +
                ", nombre='" + nombre + '\'' +
                ", clasificacion='" + clasificacion + '\'' +
                ", activa=" + activa +
                '}';
    }
}