package com.centrocultural.dao;

import com.centrocultural.database.DatabaseConnection;
import com.centrocultural.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorDAO implements GenericDAO<Instructor> {
    private DatabaseConnection db = DatabaseConnection.getInstance();
    
    @Override
    public int crear(Instructor instructor) throws SQLException {
        String query = "INSERT INTO instructores (nombre_completo, fecha_nacimiento) VALUES (?, ?)";
        
        return db.executeInsert(query,
            instructor.getNombreCompleto(),
            Date.valueOf(instructor.getFechaNacimiento())
        );
    }
    
    @Override
    public boolean actualizar(Instructor instructor) throws SQLException {
        String query = "UPDATE instructores SET nombre_completo = ?, fecha_nacimiento = ? " +
                      "WHERE no_expediente = ?";
        
        int result = db.executeUpdate(query,
            instructor.getNombreCompleto(),
            Date.valueOf(instructor.getFechaNacimiento()),
            instructor.getNoExpediente()
        );
        
        return result > 0;
    }
    
    @Override
    public boolean eliminar(int id) throws SQLException {
        // Verificar si tiene grupos asignados
        String checkQuery = "SELECT COUNT(*) FROM grupos WHERE id_instructor = ?";
        ResultSet rs = db.executeQuery(checkQuery, id);
        
        if (rs.next() && rs.getInt(1) > 0) {
            return false; // No eliminar si tiene grupos
        }
        
        // Eliminar relaciones con actividades
        String deleteActividades = "DELETE FROM instructor_actividades WHERE no_expediente_instructor = ?";
        db.executeUpdate(deleteActividades, id);
        
        // Eliminar certificados
        String deleteCertificados = "DELETE FROM certificados_instructor WHERE no_expediente_instructor = ?";
        db.executeUpdate(deleteCertificados, id);
        
        // Eliminar documentos
        String deleteDocumentos = "DELETE FROM documentos_instructor WHERE no_expediente_instructor = ?";
        db.executeUpdate(deleteDocumentos, id);
        
        // Eliminar instructor
        String query = "DELETE FROM instructores WHERE no_expediente = ?";
        int result = db.executeUpdate(query, id);
        return result > 0;
    }
    
    @Override
    public Instructor buscarPorId(int id) throws SQLException {
        String query = "SELECT * FROM instructores WHERE no_expediente = ?";
        ResultSet rs = db.executeQuery(query, id);
        
        if (rs.next()) {
            Instructor instructor = extraerInstructor(rs);
            // Cargar datos relacionados
            instructor.setActividadesAutorizadas(obtenerActividadesInstructor(id));
            instructor.setCertificados(obtenerCertificadosInstructor(id));
            instructor.setDocumentos(obtenerDocumentosInstructor(id));
            return instructor;
        }
        return null;
    }
    
    @Override
    public List<Instructor> listarTodos() throws SQLException {
        List<Instructor> instructores = new ArrayList<>();
        String query = "SELECT * FROM instructores ORDER BY nombre_completo";
        ResultSet rs = db.executeQuery(query);
        
        while (rs.next()) {
            instructores.add(extraerInstructor(rs));
        }
        
        return instructores;
    }
    
    @Override
    public List<Instructor> buscar(String criterio) throws SQLException {
        List<Instructor> instructores = new ArrayList<>();
        String query = "SELECT * FROM instructores WHERE nombre_completo LIKE ? " +
                      "OR CAST(no_expediente AS CHAR) LIKE ? ORDER BY nombre_completo";
        String criterioLike = "%" + criterio + "%";
        
        ResultSet rs = db.executeQuery(query, criterioLike, criterioLike);
        
        while (rs.next()) {
            instructores.add(extraerInstructor(rs));
        }
        
        return instructores;
    }
    
    // Método para obtener instructores autorizados para una actividad
    public List<Instructor> listarPorActividad(int idActividad) throws SQLException {
        List<Instructor> instructores = new ArrayList<>();
        String query = "SELECT i.* FROM instructores i " +
                      "INNER JOIN instructor_actividades ia ON i.no_expediente = ia.no_expediente_instructor " +
                      "WHERE ia.id_actividad = ? ORDER BY i.nombre_completo";
        
        ResultSet rs = db.executeQuery(query, idActividad);
        
        while (rs.next()) {
            instructores.add(extraerInstructor(rs));
        }
        
        return instructores;
    }
    
    // Método para autorizar una actividad a un instructor
    public boolean autorizarActividad(int noExpediente, int idActividad) throws SQLException {
        String query = "INSERT INTO instructor_actividades (no_expediente_instructor, id_actividad) " +
                      "VALUES (?, ?)";
        
        try {
            int result = db.executeUpdate(query, noExpediente, idActividad);
            return result > 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry
                return true; // Ya estaba autorizado
            }
            throw e;
        }
    }
    
    // Método para desautorizar una actividad
    public boolean desautorizarActividad(int noExpediente, int idActividad) throws SQLException {
        String query = "DELETE FROM instructor_actividades " +
                      "WHERE no_expediente_instructor = ? AND id_actividad = ?";
        
        int result = db.executeUpdate(query, noExpediente, idActividad);
        return result > 0;
    }
    
    // Método para agregar certificado
    public int agregarCertificado(int noExpediente, Certificado certificado) throws SQLException {
        String query = "INSERT INTO certificados_instructor " +
                      "(no_expediente_instructor, nombre_certificado, fecha_obtencion, archivo_path) " +
                      "VALUES (?, ?, ?, ?)";
        
        return db.executeInsert(query,
            noExpediente,
            certificado.getNombreCertificado(),
            certificado.getFechaObtencion() != null ? Date.valueOf(certificado.getFechaObtencion()) : null,
            certificado.getArchivoPath()
        );
    }
    
    // Método para agregar documento
    public int agregarDocumento(int noExpediente, DocumentoIdentidad documento) throws SQLException {
        String query = "INSERT INTO documentos_instructor " +
                      "(no_expediente_instructor, tipo_documento, numero_documento, archivo_path) " +
                      "VALUES (?, ?, ?, ?)";
        
        return db.executeInsert(query,
            noExpediente,
            documento.getTipoDocumento(),
            documento.getNumeroDocumento(),
            documento.getArchivoPath()
        );
    }
    
    // Método auxiliar para extraer instructor
    private Instructor extraerInstructor(ResultSet rs) throws SQLException {
        Instructor instructor = new Instructor();
        instructor.setNoExpediente(rs.getInt("no_expediente"));
        instructor.setNombreCompleto(rs.getString("nombre_completo"));
        instructor.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
        instructor.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        return instructor;
    }
    
    // Obtener actividades autorizadas
    private List<Actividad> obtenerActividadesInstructor(int noExpediente) throws SQLException {
        List<Actividad> actividades = new ArrayList<>();
        String query = "SELECT a.* FROM actividades a " +
                      "INNER JOIN instructor_actividades ia ON a.id_actividad = ia.id_actividad " +
                      "WHERE ia.no_expediente_instructor = ? AND a.activa = TRUE";
        
        ResultSet rs = db.executeQuery(query, noExpediente);
        
        while (rs.next()) {
            Actividad actividad = new Actividad();
            actividad.setIdActividad(rs.getInt("id_actividad"));
            actividad.setNombre(rs.getString("nombre"));
            actividad.setClasificacion(rs.getString("clasificacion"));
            actividad.setAreaAsignada(rs.getString("area_asignada"));
            actividad.setActiva(rs.getBoolean("activa"));
            actividades.add(actividad);
        }
        
        return actividades;
    }
    
    // Obtener certificados
    private List<Certificado> obtenerCertificadosInstructor(int noExpediente) throws SQLException {
        List<Certificado> certificados = new ArrayList<>();
        String query = "SELECT * FROM certificados_instructor WHERE no_expediente_instructor = ?";
        
        ResultSet rs = db.executeQuery(query, noExpediente);
        
        while (rs.next()) {
            Certificado cert = new Certificado();
            cert.setIdCertificado(rs.getInt("id_certificado"));
            cert.setNombreCertificado(rs.getString("nombre_certificado"));
            if (rs.getDate("fecha_obtencion") != null) {
                cert.setFechaObtencion(rs.getDate("fecha_obtencion").toLocalDate());
            }
            cert.setArchivoPath(rs.getString("archivo_path"));
            certificados.add(cert);
        }
        
        return certificados;
    }
    
    // Obtener documentos
    private List<DocumentoIdentidad> obtenerDocumentosInstructor(int noExpediente) throws SQLException {
        List<DocumentoIdentidad> documentos = new ArrayList<>();
        String query = "SELECT * FROM documentos_instructor WHERE no_expediente_instructor = ?";
        
        ResultSet rs = db.executeQuery(query, noExpediente);
        
        while (rs.next()) {
            DocumentoIdentidad doc = new DocumentoIdentidad();
            doc.setIdDocumento(rs.getInt("id_documento"));
            doc.setTipoDocumento(rs.getString("tipo_documento"));
            doc.setNumeroDocumento(rs.getString("numero_documento"));
            doc.setArchivoPath(rs.getString("archivo_path"));
            documentos.add(doc);
        }
        
        return documentos;
    }
}