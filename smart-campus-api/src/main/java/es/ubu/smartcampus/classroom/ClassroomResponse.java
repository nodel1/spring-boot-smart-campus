package es.ubu.smartcampus.classroom;

public record ClassroomResponse(Long id, String code, String building, Integer capacity, String responsible) {

    public static ClassroomResponse from(Classroom classroom) {
        return new ClassroomResponse(classroom.getId(), classroom.getCode(), classroom.getBuilding(),
                classroom.getCapacity(), classroom.getResponsible());
    }
}
