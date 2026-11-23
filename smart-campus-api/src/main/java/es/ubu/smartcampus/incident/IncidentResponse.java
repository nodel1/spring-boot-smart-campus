package es.ubu.smartcampus.incident;

import java.time.LocalDateTime;

public record IncidentResponse(Long id, String title, String description, IncidentStatus status,
                               LocalDateTime timestamp) {

    public static IncidentResponse from(Incident incident) {
        return new IncidentResponse(incident.getId(), incident.getTitle(), incident.getDescription(),
                incident.getStatus(), incident.getTimestamp());
    }
}
