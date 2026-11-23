package es.ubu.smartcampus.measurement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

// el sensor va en la ruta (/api/sensors/{id}/measurements); la fecha es opcional y, si no llega, se pone la actual
public record MeasurementRequest(
        @NotNull(message = "el valor es obligatorio")
        Double value,

        @NotBlank(message = "la unidad es obligatoria")
        @Size(max = 20, message = "la unidad no puede pasar de 20 caracteres")
        String unit,

        LocalDateTime timestamp) {
}
