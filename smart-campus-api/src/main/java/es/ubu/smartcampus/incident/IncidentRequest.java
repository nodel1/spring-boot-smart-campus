package es.ubu.smartcampus.incident;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTO para registrar (POST): no lleva estado ni fecha porque una incidencia nueva siempre empieza abierta y ahora
public record IncidentRequest(
        @NotBlank(message = "el título es obligatorio")
        @Size(max = 150, message = "el título no puede pasar de 150 caracteres")
        String title,

        @NotBlank(message = "la descripción es obligatoria")
        @Size(max = 1000, message = "la descripción no puede pasar de 1000 caracteres")
        String description) {
}
