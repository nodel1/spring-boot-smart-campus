package es.ubu.smartcampus.common;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

// recoge las excepciones de todos los controladores y las convierte en el código HTTP que toca
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex, HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), request, null);
    }

    // red de seguridad: si dos peticiones a la vez se saltan la comprobación del servicio, lo para la base de datos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrity(DataIntegrityViolationException ex,
                                                         HttpServletRequest request) {
        return build(HttpStatus.CONFLICT, "La operación choca con datos que ya existen", request, null);
    }

    // falla alguna anotación del DTO (@NotBlank, @Pattern...): se devuelve cada campo con su mensaje
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                          HttpServletRequest request) {
        Map<String, String> fields = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fields.putIfAbsent(error.getField(), error.getDefaultMessage());
        }
        return build(HttpStatus.BAD_REQUEST, "Hay campos con valores no válidos", request, fields);
    }

    // JSON mal escrito o un valor que no está en la lista (status = "ROTO"): Jackson falla antes de validar
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadable(HttpMessageNotReadableException ex,
                                                          HttpServletRequest request) {
        if (ex.getCause() instanceof InvalidFormatException invalid && invalid.getTargetType().isEnum()
                && !invalid.getPath().isEmpty()) {
            String field = invalid.getPath().get(0).getFieldName();
            String allowed = Arrays.toString(invalid.getTargetType().getEnumConstants());
            return build(HttpStatus.BAD_REQUEST, "Valor no válido en '" + field + "'", request,
                    Map.of(field, "valores permitidos: " + allowed));
        }
        return build(HttpStatus.BAD_REQUEST, "El cuerpo de la petición no es un JSON válido", request, null);
    }

    // id que no es un número, por ejemplo /api/sensors/abc
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                            HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "El parámetro '" + ex.getName() + "' no tiene un valor válido",
                request, null);
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message, HttpServletRequest request,
                                                Map<String, String> fields) {
        ErrorResponse body = new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(),
                message, request.getRequestURI(), fields);
        return ResponseEntity.status(status).body(body);
    }
}
