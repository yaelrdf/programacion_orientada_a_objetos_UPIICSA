//package com.centrocultural.models;

import java.util.ArrayList;
import java.util.List;

public class Material {
    private int idMaterial;
    private String nombre;
    private String areaAlmacenamiento;
    private String condicion;
    private boolean disponible;
    private List<Grupo> gruposAsignados;

    // Constructor vacío
    public Material() {
        this.disponible = true;
        this.gruposAsignados = new ArrayList<>();
    }

    // Constructor completo
    public Material(int idMaterial, String nombre, String areaAlmacenamiento, 
                   String condicion, boolean disponible) {
        this.idMaterial = idMaterial;
        this.nombre = nombre;
        this.areaAlmacenamiento = areaAlmacenamiento;
        this.condicion = condicion;
        this.disponible = disponible;
        this.gruposAsignados = new ArrayList<>();
    }

    // Constructor sin ID (para nuevos materiales)
    public Material(String nombre, String areaAlmacenamiento, String condicion) {
        this.nombre = nombre;
        this.areaAlmacenamiento = areaAlmacenamiento;
        this.condicion = condicion;
        this.disponible = true;
        this.gruposAsignados = new ArrayList<>();
    }

    // Getters y Setters
    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAreaAlmacenamiento() {
        return areaAlmacenamiento;
    }

    public void setAreaAlmacenamiento(String areaAlmacenamiento) {
        this.areaAlmacenamiento = areaAlmacenamiento;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public List<Grupo> getGruposAsignados() {
        return gruposAsignados;
    }

    public void setGruposAsignados(List<Grupo> gruposAsignados) {
        this.gruposAsignados = gruposAsignados;
    }

    // Métodos adicionales
    public void asignarGrupo(Grupo grupo) {
        if (!gruposAsignados.contains(grupo)) {
            gruposAsignados.add(grupo);
        }
    }

    public void desasignarGrupo(Grupo grupo) {
        gruposAsignados.remove(grupo);
    }

    public String getGruposAsignadosString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gruposAsignados.size(); i++) {
            sb.append(gruposAsignados.get(i).getNombreGrupo());
            if (i < gruposAsignados.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Material{" +
                "idMaterial=" + idMaterial +
                ", nombre='" + nombre + '\'' +
                ", condicion='" + condicion + '\'' +
                ", disponible=" + disponible +
                ", gruposAsignados=" + gruposAsignados.size() +
                '}';
    }
}