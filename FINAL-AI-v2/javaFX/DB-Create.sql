-- Script de inicialización para Base de Datos MariaDB
-- Centro Cultural

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS centro_cultural;
USE centro_cultural;

-- Tabla de usuarios del sistema
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(50) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    nombre_empleado VARCHAR(100) NOT NULL,
    area_asignada ENUM('administracion', 'contabilidad') NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de instructores
CREATE TABLE IF NOT EXISTS instructores (
    no_expediente INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(150) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de actividades
CREATE TABLE IF NOT EXISTS actividades (
    id_actividad INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    clasificacion ENUM('deportiva', 'cultural', 'oficio') NOT NULL,
    area_asignada VARCHAR(100),
    activa BOOLEAN DEFAULT TRUE
);

-- Tabla de alumnos
CREATE TABLE IF NOT EXISTS alumnos (
    no_expediente INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(150) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de grupos
CREATE TABLE IF NOT EXISTS grupos (
    id_grupo INT AUTO_INCREMENT PRIMARY KEY,
    id_actividad INT NOT NULL,
    id_instructor INT NOT NULL,
    nombre_grupo VARCHAR(100) NOT NULL,
    salon VARCHAR(50),
    edificio VARCHAR(50),
    horario VARCHAR(100),
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_actividad) REFERENCES actividades(id_actividad),
    FOREIGN KEY (id_instructor) REFERENCES instructores(no_expediente)
);

-- Tabla de materiales
CREATE TABLE IF NOT EXISTS materiales (
    id_material INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    area_almacenamiento VARCHAR(100),
    condicion ENUM('bueno', 'regular', 'malo') NOT NULL,
    disponible BOOLEAN DEFAULT TRUE
);

-- Tabla de inscripciones
CREATE TABLE IF NOT EXISTS inscripciones (
    id_inscripcion INT AUTO_INCREMENT PRIMARY KEY,
    no_expediente_alumno INT NOT NULL,
    id_grupo INT NOT NULL,
    fecha_inscripcion DATE NOT NULL,
    activa BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (no_expediente_alumno) REFERENCES alumnos(no_expediente),
    FOREIGN KEY (id_grupo) REFERENCES grupos(id_grupo),
    UNIQUE KEY unique_alumno_grupo (no_expediente_alumno, id_grupo)
);

-- Tabla de pagos
CREATE TABLE IF NOT EXISTS pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_inscripcion INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha_pago DATE NOT NULL,
    concepto VARCHAR(200),
    registrado_por INT,
    FOREIGN KEY (id_inscripcion) REFERENCES inscripciones(id_inscripcion),
    FOREIGN KEY (registrado_por) REFERENCES usuarios(id_usuario)
);

-- Tabla de certificados de instructores
CREATE TABLE IF NOT EXISTS certificados_instructor (
    id_certificado INT AUTO_INCREMENT PRIMARY KEY,
    no_expediente_instructor INT NOT NULL,
    nombre_certificado VARCHAR(200) NOT NULL,
    fecha_obtencion DATE,
    archivo_path VARCHAR(500),
    FOREIGN KEY (no_expediente_instructor) REFERENCES instructores(no_expediente)
);

-- Tabla de documentos de identidad de instructores
CREATE TABLE IF NOT EXISTS documentos_instructor (
    id_documento INT AUTO_INCREMENT PRIMARY KEY,
    no_expediente_instructor INT NOT NULL,
    tipo_documento VARCHAR(50) NOT NULL,
    numero_documento VARCHAR(50),
    archivo_path VARCHAR(500),
    FOREIGN KEY (no_expediente_instructor) REFERENCES instructores(no_expediente)
);

-- Tabla de relación actividades autorizadas para instructores
CREATE TABLE IF NOT EXISTS instructor_actividades (
    no_expediente_instructor INT NOT NULL,
    id_actividad INT NOT NULL,
    fecha_autorizacion DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY (no_expediente_instructor, id_actividad),
    FOREIGN KEY (no_expediente_instructor) REFERENCES instructores(no_expediente),
    FOREIGN KEY (id_actividad) REFERENCES actividades(id_actividad)
);

-- Tabla de relación materiales asignados a grupos
CREATE TABLE IF NOT EXISTS material_grupo (
    id_material INT NOT NULL,
    id_grupo INT NOT NULL,
    fecha_asignacion DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY (id_material, id_grupo),
    FOREIGN KEY (id_material) REFERENCES materiales(id_material),
    FOREIGN KEY (id_grupo) REFERENCES grupos(id_grupo)
);

-- Insertar usuario administrador por defecto
INSERT INTO usuarios (nombre_usuario, contrasena, nombre_empleado, area_asignada) 
VALUES ('admin', SHA2('admin123', 256), 'Administrador Sistema', 'administracion');

-- Insertar usuario de contabilidad por defecto
INSERT INTO usuarios (nombre_usuario, contrasena, nombre_empleado, area_asignada) 
VALUES ('contabilidad', SHA2('conta123', 256), 'Usuario Contabilidad', 'contabilidad');

-- Índices para mejorar rendimiento
CREATE INDEX idx_alumnos_nombre ON alumnos(nombre_completo);
CREATE INDEX idx_instructores_nombre ON instructores(nombre_completo);
CREATE INDEX idx_inscripciones_fecha ON inscripciones(fecha_inscripcion);
CREATE INDEX idx_pagos_fecha ON pagos(fecha_pago);

--Refactors
-- Modificar tabla inscripciones para agregar fecha_vencimiento
ALTER TABLE inscripciones ADD COLUMN fecha_vencimiento DATE AFTER fecha_inscripcion;