package es.ubu.smartcampus.measurement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// las medidas no vienen en la lista del enunciado: van anidadas en el sensor porque siempre son de uno
@RestController
@RequestMapping("/api")
@Tag(name = "Medidas")
public class MeasurementController {

    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    // 201 sin cabecera Location: una medida no tiene GET propio, se consulta con las del sensor
    @PostMapping("/sensors/{sensorId}/measurements")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registra una medida de un sensor (201; 404 si el sensor no existe)")
    public MeasurementResponse create(@PathVariable Long sensorId, @Valid @RequestBody MeasurementRequest request) {
        return measurementService.create(sensorId, request);
    }

    @GetMapping("/sensors/{sensorId}/measurements")
    @Operation(summary = "Medidas de un sensor, de la más reciente a la más antigua")
    public List<MeasurementResponse> findBySensor(@PathVariable Long sensorId) {
        return measurementService.findBySensor(sensorId);
    }

    @GetMapping("/measurements")
    @Operation(summary = "Todas las medidas, o las de un sensor con ?sensorId=")
    public List<MeasurementResponse> findAll(@RequestParam(required = false) Long sensorId) {
        return measurementService.findAll(sensorId);
    }
}
