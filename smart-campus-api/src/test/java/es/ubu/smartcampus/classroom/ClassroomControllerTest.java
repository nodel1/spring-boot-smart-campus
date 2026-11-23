package es.ubu.smartcampus.classroom;

import es.ubu.smartcampus.common.ConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClassroomController.class)
class ClassroomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClassroomService classroomService;

    private static final String VALID_CLASSROOM = """
            {"code": "A2.1", "building": "Edificio A", "capacity": 50, "responsible": "Laura Martín"}
            """;

    @Test
    void createReturns201() throws Exception {
        when(classroomService.create(any()))
                .thenReturn(new ClassroomResponse(5L, "A2.1", "Edificio A", 50, "Laura Martín"));

        mockMvc.perform(post("/api/classrooms").contentType(MediaType.APPLICATION_JSON).content(VALID_CLASSROOM))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/classrooms/5"));
    }

    @Test
    void createWithZeroCapacityReturns400() throws Exception {
        String body = VALID_CLASSROOM.replace("50", "0");

        mockMvc.perform(post("/api/classrooms").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields.capacity").exists());
    }

    @Test
    void createWithRepeatedCodeReturns409() throws Exception {
        when(classroomService.create(any())).thenThrow(new ConflictException("Ya existe un aula con el código A2.1"));

        mockMvc.perform(post("/api/classrooms").contentType(MediaType.APPLICATION_JSON).content(VALID_CLASSROOM))
                .andExpect(status().isConflict());
    }
}
