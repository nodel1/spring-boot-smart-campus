package es.ubu.smartcampus.sensor;

import jakarta.persistence.*;

// entidad: es la tabla sensors tal cual; a la API no sale nunca, sale SensorResponse
@Entity
@Table(name = "sensors")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SensorType type;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String ip;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SensorStatus status;

    // JPA necesita un constructor vacío
    protected Sensor() {
    }

    public Sensor(String name, SensorType type, String location, String ip, SensorStatus status) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.ip = ip;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public SensorType getType() { return type; }
    public void setType(SensorType type) { this.type = type; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public SensorStatus getStatus() { return status; }
    public void setStatus(SensorStatus status) { this.status = status; }
}
