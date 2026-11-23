-- Datos de ejemplo 
-- Se carga después de smart_campus_schema.sql (el contenedor lo hace solo al arrancar con la base vacía)

SET NAMES utf8mb4;
USE smart_campus;

-- Sensores (el 5 no tiene medidas, para poder borrarlo en la demo)
INSERT INTO sensors (id, name, type, location, ip, status) VALUES
    (1, 'Temperatura aula A1.1', 'TEMPERATURE', 'Edificio A - Aula A1.1', '192.168.10.11', 'ACTIVE'),
    (2, 'Humedad aula A1.1',     'HUMIDITY',    'Edificio A - Aula A1.1', '192.168.10.12', 'ACTIVE'),
    (3, 'CO2 laboratorio B2.3',  'CO2',         'Edificio B - Lab. B2.3', '192.168.20.31', 'ACTIVE'),
    (4, 'Ocupación biblioteca',  'OCCUPANCY',   'Edificio C - Biblioteca', '192.168.30.41', 'ACTIVE'),
    (5, 'Luz pasillo B',         'LIGHT',       'Edificio B - Pasillo 2', '192.168.20.51', 'MAINTENANCE');

-- Aulas
INSERT INTO classrooms (id, code, building, capacity, responsible) VALUES
    (1, 'A1.1', 'Edificio A', 60,  'Laura Martín'),
    (2, 'A1.2', 'Edificio A', 40,  'Laura Martín'),
    (3, 'B2.3', 'Edificio B', 24,  'Javier Ortega'),
    (4, 'C0.1', 'Edificio C', 120, 'Marta Gil');

-- Medidas (alguna por encima de lo normal: 29 °C y 1250 ppm)
INSERT INTO measurements (timestamp, value, unit, sensor_id) VALUES
    ('2026-10-20 09:00:00', 21.5, '°C', 1),
    ('2026-10-20 10:00:00', 22.8, '°C', 1),
    ('2026-10-20 11:00:00', 24.1, '°C', 1),
    ('2026-10-20 12:00:00', 26.3, '°C', 1),
    ('2026-10-20 13:00:00', 29.0, '°C', 1),
    ('2026-10-20 09:00:00', 45.0, '%', 2),
    ('2026-10-20 10:00:00', 47.5, '%', 2),
    ('2026-10-20 11:00:00', 52.0, '%', 2),
    ('2026-10-20 12:00:00', 55.5, '%', 2),
    ('2026-10-20 13:00:00', 58.0, '%', 2),
    ('2026-10-20 09:00:00', 450,  'ppm', 3),
    ('2026-10-20 10:00:00', 620,  'ppm', 3),
    ('2026-10-20 11:00:00', 880,  'ppm', 3),
    ('2026-10-20 12:00:00', 1250, 'ppm', 3),
    ('2026-10-20 13:00:00', 700,  'ppm', 3),
    ('2026-10-20 09:00:00', 35,   'personas', 4),
    ('2026-10-20 10:00:00', 78,   'personas', 4),
    ('2026-10-20 11:00:00', 112,  'personas', 4),
    ('2026-10-20 12:00:00', 95,   'personas', 4),
    ('2026-10-20 13:00:00', 60,   'personas', 4);

-- Incidencias
INSERT INTO incidents (id, title, description, status, timestamp) VALUES
    (1, 'Temperatura alta en A1.1', 'El sensor 1 marca 29 °C a mediodía; revisar la climatización.', 'OPEN', '2026-10-20 13:05:00'),
    (2, 'Sensor de luz sin datos', 'El sensor del pasillo 2 del edificio B no envía medidas.', 'IN_PROGRESS', '2026-10-19 08:30:00'),
    (3, 'CO2 alto en el laboratorio B2.3', 'Se superaron 1200 ppm durante una práctica; se abrieron las ventanas.', 'CLOSED', '2026-10-16 12:10:00');
