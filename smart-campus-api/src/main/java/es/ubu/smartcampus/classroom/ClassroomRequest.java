package es.ubu.smartcampus.classroom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ClassroomRequest(
        @NotBlank(message = "el código es obligatorio")
        @Size(max = 20, message = "el código no puede pasar de 20 caracteres")
        String code,

        @NotBlank(message = "el edificio es obligatorio")
        @Size(max = 100, message = "el edificio no puede pasar de 100 caracteres")
        String building,

        @NotNull(message = "la capacidad es obligatoria")
        @Positive(message = "la capacidad tiene que ser mayor que 0")
        Integer capacity,

        @NotBlank(message = "el responsable es obligatorio")
        @Size(max = 100, message = "el responsable no puede pasar de 100 caracteres")
        String responsible) {
}
