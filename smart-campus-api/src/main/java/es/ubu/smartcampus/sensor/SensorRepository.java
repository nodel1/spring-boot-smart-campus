package es.ubu.smartcampus.sensor;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data genera las consultas (findAll, findById, save, delete...) a partir de la interfaz
public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
