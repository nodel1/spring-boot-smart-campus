package es.ubu.smartcampus.classroom;

import jakarta.persistence.*;

@Entity
@Table(name = "classrooms")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String building;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private String responsible;

    protected Classroom() {
    }

    public Classroom(String code, String building, Integer capacity, String responsible) {
        this.code = code;
        this.building = building;
        this.capacity = capacity;
        this.responsible = responsible;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public String getResponsible() { return responsible; }
    public void setResponsible(String responsible) { this.responsible = responsible; }
}
