//package com.centrocultural.database;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private String url;
    private String username;
    private String password;
    
    // Constructor privado para patrón Singleton
    private DatabaseConnection() {
        loadConfiguration();
    }
    
    // Método para obtener la instancia única
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    // Cargar configuración desde archivo properties
    private void loadConfiguration() {
        Properties props = new Properties();
        try {
            // Intentar cargar desde archivo de configuración
            InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties");
            if (input == null) {
                // Si no existe el archivo, usar valores por defecto
                this.url = "jdbc:mariadb://localhost:3306/centro_cultural";
                this.username = "root";
                this.password = "";
            } else {
                props.load(input);
                this.url = props.getProperty("db.url", "jdbc:mariadb://localhost:3306/centro_cultural");
                this.username = props.getProperty("db.username", "root");
                this.password = props.getProperty("db.password", "");
                input.close();
            }
        } catch (IOException e) {
            // En caso de error, usar valores por defecto
            this.url = "jdbc:mariadb://localhost:3306/centro_cultural";
            this.username = "root";
            this.password = "";
            System.err.println("Error cargando configuración de BD: " + e.getMessage());
        }
    }
    
    // Obtener conexión
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver de MariaDB no encontrado: " + e.getMessage());
            }
        }
        return connection;
    }
    
    // Cerrar conexión
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    // Verificar conexión
    public boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error al probar conexión: " + e.getMessage());
            return false;
        }
    }
    
    // Método para reconectar
    public boolean reconnect() {
        closeConnection();
        try {
            getConnection();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    // Ejecutar query de consulta
    public ResultSet executeQuery(String query, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Establecer parámetros
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
        
        return pstmt.executeQuery();
    }
    
    // Ejecutar update/insert/delete
    public int executeUpdate(String query, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        
        // Establecer parámetros
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
        
        return pstmt.executeUpdate();
    }
    
    // Ejecutar insert y obtener ID generado
    public int executeInsert(String query, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        
        // Establecer parámetros
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
        
        int affectedRows = pstmt.executeUpdate();
        
        if (affectedRows > 0) {
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        }
        
        return -1;
    }
    
    // Iniciar transacción
    public void beginTransaction() throws SQLException {
        Connection conn = getConnection();
        conn.setAutoCommit(false);
    }
    
    // Confirmar transacción
    public void commitTransaction() throws SQLException {
        Connection conn = getConnection();
        conn.commit();
        conn.setAutoCommit(true);
    }
    
    // Revertir transacción
    public void rollbackTransaction() throws SQLException {
        Connection conn = getConnection();
        conn.rollback();
        conn.setAutoCommit(true);
    }
}