package es.ubu.smartcampus.incident;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IncidentStatus status;

    // fecha en la que se registró la incidencia
    @Column(nullable = false)
    private LocalDateTime timestamp;

    protected Incident() {
    }

    public Incident(String title, String description, IncidentStatus status, LocalDateTime timestamp) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public IncidentStatus getStatus() { return status; }
    public void setStatus(IncidentStatus status) { this.status = status; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
