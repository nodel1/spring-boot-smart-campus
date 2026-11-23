package es.ubu.smartcampus.sensor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// DTO de entrada (POST y PUT): sin id, porque lo pone la base de datos
public record SensorRequest(
        @NotBlank(message = "el nombre es obligatorio")
        @Size(max = 100, message = "el nombre no puede pasar de 100 caracteres")
        String name,

        @NotNull(message = "el tipo es obligatorio")
        SensorType type,

        @NotBlank(message = "la ubicación es obligatoria")
        @Size(max = 100, message = "la ubicación no puede pasar de 100 caracteres")
        String location,

        @NotBlank(message = "la IP es obligatoria")
        @Pattern(regexp = "^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)$",
                message = "la IP tiene que ser una IPv4, por ejemplo 192.168.10.11")
        String ip,

        @NotNull(message = "el estado es obligatorio")
        SensorStatus status) {
}
