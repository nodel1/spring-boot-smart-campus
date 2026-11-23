package es.ubu.smartcampus.incident;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// DTO para modificar (PUT): aquí sí va el estado, porque cerrar una incidencia es mandarla con status = CLOSED
public record IncidentUpdateRequest(
        @NotBlank(message = "el título es obligatorio")
        @Size(max = 150, message = "el título no puede pasar de 150 caracteres")
        String title,

        @NotBlank(message = "la descripción es obligatoria")
        @Size(max = 1000, message = "la descripción no puede pasar de 1000 caracteres")
        String description,

        @NotNull(message = "el estado es obligatorio")
        IncidentStatus status) {
}
