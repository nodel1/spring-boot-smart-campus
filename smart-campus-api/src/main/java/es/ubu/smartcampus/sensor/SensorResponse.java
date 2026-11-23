package es.ubu.smartcampus.sensor;

// DTO de salida: lo que ve el cliente, separado de la entidad
public record SensorResponse(Long id, String name, SensorType type, String location, String ip,
                             SensorStatus status) {

    public static SensorResponse from(Sensor sensor) {
        return new SensorResponse(sensor.getId(), sensor.getName(), sensor.getType(), sensor.getLocation(),
                sensor.getIp(), sensor.getStatus());
    }
}
