package es.ubu.smartcampus.common;

// la petición es correcta pero choca con lo que ya hay (código repetido, sensor con medidas): 409
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
