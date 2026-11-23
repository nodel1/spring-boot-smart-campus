package es.ubu.smartcampus.sensor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

// recibe la petición, valida el DTO (@Valid) y pasa el trabajo al servicio
@RestController
@RequestMapping("/api/sensors")
@Tag(name = "Sensores")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    @Operation(summary = "Lista todos los sensores")
    public List<SensorResponse> findAll() {
        return sensorService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Devuelve un sensor (404 si no existe)")
    public SensorResponse findById(@PathVariable Long id) {
        return sensorService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Registra un sensor (201 y cabecera Location)")
    public ResponseEntity<SensorResponse> create(@Valid @RequestBody SensorRequest request) {
        SensorResponse created = sensorService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifica un sensor entero")
    public SensorResponse update(@PathVariable Long id, @Valid @RequestBody SensorRequest request) {
        return sensorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra un sensor (204; 409 si tiene medidas)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sensorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
