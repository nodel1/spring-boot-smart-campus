package es.ubu.smartcampus.sensor;

import es.ubu.smartcampus.common.ConflictException;
import es.ubu.smartcampus.common.NotFoundException;
import es.ubu.smartcampus.measurement.MeasurementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// la lógica del recurso: el controlador no sabe nada de la base de datos
@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private final MeasurementRepository measurementRepository;

    public SensorService(SensorRepository sensorRepository, MeasurementRepository measurementRepository) {
        this.sensorRepository = sensorRepository;
        this.measurementRepository = measurementRepository;
    }

    @Transactional(readOnly = true)
    public List<SensorResponse> findAll() {
        return sensorRepository.findAll().stream().map(SensorResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public SensorResponse findById(Long id) {
        return SensorResponse.from(getSensor(id));
    }

    @Transactional
    public SensorResponse create(SensorRequest request) {
        Sensor sensor = new Sensor(request.name(), request.type(), request.location(), request.ip(),
                request.status());
        return SensorResponse.from(sensorRepository.save(sensor));
    }

    // PUT reemplaza el sensor entero, por eso se piden todos los campos
    @Transactional
    public SensorResponse update(Long id, SensorRequest request) {
        Sensor sensor = getSensor(id);
        sensor.setName(request.name());
        sensor.setType(request.type());
        sensor.setLocation(request.location());
        sensor.setIp(request.ip());
        sensor.setStatus(request.status());
        return SensorResponse.from(sensor);
    }

    @Transactional
    public void delete(Long id) {
        Sensor sensor = getSensor(id);
        // no se borran sus medidas por detrás: se avisa y que decida el cliente (por ejemplo, pasarlo a INACTIVE)
        if (measurementRepository.existsBySensorId(id)) {
            throw new ConflictException("El sensor " + id + " tiene medidas guardadas y no se puede borrar");
        }
        sensorRepository.delete(sensor);
    }

    // también lo usa el servicio de medidas para comprobar que el sensor existe
    public Sensor getSensor(Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe el sensor " + id));
    }
}
