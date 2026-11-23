-- Script de creación de tablas 

SET NAMES utf8mb4;
CREATE DATABASE IF NOT EXISTS smart_campus CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE smart_campus;

DROP TABLE IF EXISTS measurements;
DROP TABLE IF EXISTS incidents;
DROP TABLE IF EXISTS classrooms;
DROP TABLE IF EXISTS sensors;

-- Sensores
CREATE TABLE sensors (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    name     VARCHAR(100) NOT NULL,
    type     ENUM('TEMPERATURE', 'HUMIDITY', 'CO2', 'OCCUPANCY', 'LIGHT') NOT NULL,
    location VARCHAR(100) NOT NULL,
    ip       VARCHAR(15)  NOT NULL,
    status   ENUM('ACTIVE', 'INACTIVE', 'MAINTENANCE') NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Aulas
CREATE TABLE classrooms (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    code        VARCHAR(20)  NOT NULL,
    building    VARCHAR(100) NOT NULL,
    capacity    INT          NOT NULL,
    responsible VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_classrooms_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Medidas
CREATE TABLE measurements (
    id        BIGINT      NOT NULL AUTO_INCREMENT,
    timestamp DATETIME    NOT NULL,
    value     DOUBLE      NOT NULL,
    unit      VARCHAR(20) NOT NULL,
    sensor_id BIGINT      NOT NULL,
    PRIMARY KEY (id),
    KEY idx_measurements_sensor_time (sensor_id, timestamp),
    CONSTRAINT fk_measurements_sensor FOREIGN KEY (sensor_id) REFERENCES sensors (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Incidencias
CREATE TABLE incidents (
    id          BIGINT        NOT NULL AUTO_INCREMENT,
    title       VARCHAR(150)  NOT NULL,
    description VARCHAR(1000) NOT NULL,
    status      ENUM('OPEN', 'IN_PROGRESS', 'CLOSED') NOT NULL,
    timestamp   DATETIME      NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
