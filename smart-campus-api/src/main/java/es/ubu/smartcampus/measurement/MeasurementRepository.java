package es.ubu.smartcampus.measurement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    // consultas derivadas del nombre del método: Spring Data escribe el SELECT ... WHERE sensor_id = ?
    boolean existsBySensorId(Long sensorId);

    List<Measurement> findBySensorIdOrderByTimestampDesc(Long sensorId);

    List<Measurement> findAllByOrderByTimestampDesc();
}
