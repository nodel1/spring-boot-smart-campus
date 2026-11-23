package es.ubu.smartcampus.measurement;

import java.time.LocalDateTime;

// se devuelve solo el id del sensor, no el sensor entero
public record MeasurementResponse(Long id, Long sensorId, LocalDateTime timestamp, Double value, String unit) {

    public static MeasurementResponse from(Measurement measurement) {
        return new MeasurementResponse(measurement.getId(), measurement.getSensor().getId(),
                measurement.getTimestamp(), measurement.getValue(), measurement.getUnit());
    }
}
