package es.ubu.smartcampus.incident;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@Tag(name = "Incidencias")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GetMapping
    @Operation(summary = "Lista todas las incidencias")
    public List<IncidentResponse> findAll() {
        return incidentService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Devuelve una incidencia (404 si no existe)")
    public IncidentResponse findById(@PathVariable Long id) {
        return incidentService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Registra una incidencia (sale con status OPEN y la fecha actual)")
    public ResponseEntity<IncidentResponse> create(@Valid @RequestBody IncidentRequest request) {
        IncidentResponse created = incidentService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifica una incidencia; para cerrarla, status = CLOSED")
    public IncidentResponse update(@PathVariable Long id, @Valid @RequestBody IncidentUpdateRequest request) {
        return incidentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra una incidencia (204)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        incidentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
