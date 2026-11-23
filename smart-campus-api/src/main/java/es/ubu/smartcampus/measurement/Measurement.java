package es.ubu.smartcampus.measurement;

import es.ubu.smartcampus.sensor.Sensor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private String unit;

    // la columna sensor_id es la clave ajena; LAZY para no cargar el sensor si no hace falta
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    protected Measurement() {
    }

    public Measurement(LocalDateTime timestamp, Double value, String unit, Sensor sensor) {
        this.timestamp = timestamp;
        this.value = value;
        this.unit = unit;
        this.sensor = sensor;
    }

    public Long getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Double getValue() { return value; }
    public String getUnit() { return unit; }
    public Sensor getSensor() { return sensor; }
}
