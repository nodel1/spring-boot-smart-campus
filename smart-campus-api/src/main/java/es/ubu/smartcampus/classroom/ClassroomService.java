package es.ubu.smartcampus.classroom;

import es.ubu.smartcampus.common.ConflictException;
import es.ubu.smartcampus.common.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Transactional(readOnly = true)
    public List<ClassroomResponse> findAll() {
        return classroomRepository.findAll().stream().map(ClassroomResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ClassroomResponse findById(Long id) {
        return ClassroomResponse.from(getClassroom(id));
    }

    // el código es único: se comprueba antes para dar un 409 claro en vez de un error de la base de datos
    @Transactional
    public ClassroomResponse create(ClassroomRequest request) {
        if (classroomRepository.existsByCode(request.code())) {
            throw new ConflictException("Ya existe un aula con el código " + request.code());
        }
        Classroom classroom = new Classroom(request.code(), request.building(), request.capacity(),
                request.responsible());
        return ClassroomResponse.from(classroomRepository.save(classroom));
    }

    @Transactional
    public ClassroomResponse update(Long id, ClassroomRequest request) {
        Classroom classroom = getClassroom(id);
        if (classroomRepository.existsByCodeAndIdNot(request.code(), id)) {
            throw new ConflictException("Ya existe otra aula con el código " + request.code());
        }
        classroom.setCode(request.code());
        classroom.setBuilding(request.building());
        classroom.setCapacity(request.capacity());
        classroom.setResponsible(request.responsible());
        return ClassroomResponse.from(classroom);
    }

    @Transactional
    public void delete(Long id) {
        classroomRepository.delete(getClassroom(id));
    }

    private Classroom getClassroom(Long id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe el aula " + id));
    }
}
