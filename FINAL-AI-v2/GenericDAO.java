//package com.centrocultural.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
    // Crear un nuevo registro
    int crear(T objeto) throws SQLException;
    
    // Actualizar un registro existente
    boolean actualizar(T objeto) throws SQLException;
    
    // Eliminar un registro por ID
    boolean eliminar(int id) throws SQLException;
    
    // Buscar un registro por ID
    T buscarPorId(int id) throws SQLException;
    
    // Listar todos los registros
    List<T> listarTodos() throws SQLException;
    
    // Buscar registros por criterio
    List<T> buscar(String criterio) throws SQLException;
}