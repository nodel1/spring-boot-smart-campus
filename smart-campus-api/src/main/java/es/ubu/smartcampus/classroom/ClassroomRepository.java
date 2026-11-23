package es.ubu.smartcampus.classroom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    boolean existsByCode(String code);

    // al modificar, el código puede ser el suyo, pero no el de otra aula
    boolean existsByCodeAndIdNot(String code, Long id);
}
