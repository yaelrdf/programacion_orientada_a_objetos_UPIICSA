package com.centrocultural.models;

public class DocumentoIdentidad {
    private int idDocumento;
    private String tipoDocumento;
    private String numeroDocumento;
    private String archivoPath;

    public DocumentoIdentidad() {}

    public DocumentoIdentidad(int idDocumento, String tipoDocumento, 
                            String numeroDocumento, String archivoPath) {
        this.idDocumento = idDocumento;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.archivoPath = archivoPath;
    }

    // Getters y Setters
    public int getIdDocumento() { 
        return idDocumento; 
    }
    
    public void setIdDocumento(int idDocumento) { 
        this.idDocumento = idDocumento; 
    }
    
    public String getTipoDocumento() { 
        return tipoDocumento; 
    }
    
    public void setTipoDocumento(String tipoDocumento) { 
        this.tipoDocumento = tipoDocumento; 
    }
    
    public String getNumeroDocumento() { 
        return numeroDocumento; 
    }
    
    public void setNumeroDocumento(String numeroDocumento) { 
        this.numeroDocumento = numeroDocumento; 
    }
    
    public String getArchivoPath() { 
        return archivoPath; 
    }
    
    public void setArchivoPath(String archivoPath) { 
        this.archivoPath = archivoPath; 
    }
    
    @Override
    public String toString() {
        return "DocumentoIdentidad{" +
                "idDocumento=" + idDocumento +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                '}';
    }
}