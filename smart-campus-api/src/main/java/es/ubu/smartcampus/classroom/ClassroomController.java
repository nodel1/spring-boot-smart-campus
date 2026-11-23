package es.ubu.smartcampus.classroom;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@Tag(name = "Aulas")
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    @Operation(summary = "Lista todas las aulas")
    public List<ClassroomResponse> findAll() {
        return classroomService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Devuelve un aula (404 si no existe)")
    public ClassroomResponse findById(@PathVariable Long id) {
        return classroomService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Registra un aula (409 si el código ya existe)")
    public ResponseEntity<ClassroomResponse> create(@Valid @RequestBody ClassroomRequest request) {
        ClassroomResponse created = classroomService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifica un aula entera")
    public ClassroomResponse update(@PathVariable Long id, @Valid @RequestBody ClassroomRequest request) {
        return classroomService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Borra un aula (204)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classroomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
