package es.ubu.smartcampus.common;

// se lanza en el servicio cuando el id no existe; el manejador global la convierte en un 404
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
