package es.ubu.smartcampus;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// prueba las tres capas juntas (controlador, servicio y repositorio) contra H2 en memoria
@SpringBootTest
@AutoConfigureMockMvc
class SmartCampusIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sensorWithMeasurementsCannotBeDeleted() throws Exception {
        String created = mockMvc.perform(post("/api/sensors").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "CO2 aula A1.2", "type": "CO2", "location": "Edificio A - Aula A1.2",
                                 "ip": "192.168.10.14", "status": "ACTIVE"}
                                """))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.read(created, "$.id");

        mockMvc.perform(post("/api/sensors/" + id + "/measurements").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": 640, \"unit\": \"ppm\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.timestamp").exists());

        mockMvc.perform(get("/api/measurements").param("sensorId", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        mockMvc.perform(delete("/api/sensors/" + id)).andExpect(status().isConflict());
        mockMvc.perform(get("/api/sensors/" + id)).andExpect(status().isOk());
    }

    @Test
    void repeatedClassroomCodeReturns409() throws Exception {
        String body = """
                {"code": "D1.1", "building": "Edificio D", "capacity": 30, "responsible": "Marta Gil"}
                """;
        mockMvc.perform(post("/api/classrooms").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/api/classrooms").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isConflict());
    }

    @Test
    void incidentStartsOpenAndCanBeClosed() throws Exception {
        String created = mockMvc.perform(post("/api/incidents").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Puerta atascada\", \"description\": \"La puerta de C0.1 no cierra\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("OPEN"))
                .andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.read(created, "$.id");

        mockMvc.perform(put("/api/incidents/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title": "Puerta atascada", "description": "Arreglada por mantenimiento",
                                 "status": "CLOSED"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CLOSED"));
    }
}
