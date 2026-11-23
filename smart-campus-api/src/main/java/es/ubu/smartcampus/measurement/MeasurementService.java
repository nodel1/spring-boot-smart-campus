package es.ubu.smartcampus.measurement;

import es.ubu.smartcampus.sensor.Sensor;
import es.ubu.smartcampus.sensor.SensorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    // una medida siempre es de un sensor que existe: si no, 404 antes de guardar nada
    @Transactional
    public MeasurementResponse create(Long sensorId, MeasurementRequest request) {
        Sensor sensor = sensorService.getSensor(sensorId);
        LocalDateTime timestamp = request.timestamp() != null ? request.timestamp() : LocalDateTime.now();
        // la columna DATETIME no guarda fracciones de segundo: se quitan aquí para que el POST y el GET digan lo mismo
        timestamp = timestamp.truncatedTo(ChronoUnit.SECONDS);
        Measurement measurement = new Measurement(timestamp, request.value(), request.unit(), sensor);
        return MeasurementResponse.from(measurementRepository.save(measurement));
    }

    @Transactional(readOnly = true)
    public List<MeasurementResponse> findBySensor(Long sensorId) {
        sensorService.getSensor(sensorId);
        return measurementRepository.findBySensorIdOrderByTimestampDesc(sensorId).stream()
                .map(MeasurementResponse::from).toList();
    }

    // sin filtro devuelve todas; con sensorId, lo mismo que la ruta anidada
    @Transactional(readOnly = true)
    public List<MeasurementResponse> findAll(Long sensorId) {
        if (sensorId != null) {
            return findBySensor(sensorId);
        }
        return measurementRepository.findAllByOrderByTimestampDesc().stream()
                .map(MeasurementResponse::from).toList();
    }
}
