package es.ubu.smartcampus.incident;

import es.ubu.smartcampus.common.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @Transactional(readOnly = true)
    public List<IncidentResponse> findAll() {
        return incidentRepository.findAll().stream().map(IncidentResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public IncidentResponse findById(Long id) {
        return IncidentResponse.from(getIncident(id));
    }

    // el estado y la fecha los pone el servidor, no el cliente (sin fracciones de segundo, como la columna DATETIME)
    @Transactional
    public IncidentResponse create(IncidentRequest request) {
        Incident incident = new Incident(request.title(), request.description(), IncidentStatus.OPEN,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return IncidentResponse.from(incidentRepository.save(incident));
    }

    // cerrar es un PUT con status = CLOSED: la incidencia se queda en el histórico, no se borra
    @Transactional
    public IncidentResponse update(Long id, IncidentUpdateRequest request) {
        Incident incident = getIncident(id);
        incident.setTitle(request.title());
        incident.setDescription(request.description());
        incident.setStatus(request.status());
        return IncidentResponse.from(incident);
    }

    @Transactional
    public void delete(Long id) {
        incidentRepository.delete(getIncident(id));
    }

    private Incident getIncident(Long id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe la incidencia " + id));
    }
}
