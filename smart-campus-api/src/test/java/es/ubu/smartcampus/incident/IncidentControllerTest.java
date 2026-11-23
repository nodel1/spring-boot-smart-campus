package es.ubu.smartcampus.incident;

import es.ubu.smartcampus.common.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IncidentController.class)
class IncidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IncidentService incidentService;

    @Test
    void createReturns201() throws Exception {
        when(incidentService.create(any())).thenReturn(new IncidentResponse(4L, "Proyector roto", "No enciende",
                IncidentStatus.OPEN, LocalDateTime.of(2026, 11, 3, 10, 0)));

        mockMvc.perform(post("/api/incidents").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Proyector roto\", \"description\": \"No enciende\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @Test
    void closeReturns200() throws Exception {
        when(incidentService.update(eq(1L), any())).thenReturn(new IncidentResponse(1L, "Temperatura alta",
                "Revisada", IncidentStatus.CLOSED, LocalDateTime.of(2026, 11, 2, 13, 5)));

        mockMvc.perform(put("/api/incidents/1").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Temperatura alta\", \"description\": \"Revisada\", \"status\": \"CLOSED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CLOSED"));
    }

    @Test
    void updateWithoutStatusReturns400() throws Exception {
        mockMvc.perform(put("/api/incidents/1").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Temperatura alta\", \"description\": \"Revisada\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields.status").exists());
    }

    @Test
    void findMissingIncidentReturns404() throws Exception {
        when(incidentService.findById(99L)).thenThrow(new NotFoundException("No existe la incidencia 99"));

        mockMvc.perform(get("/api/incidents/99")).andExpect(status().isNotFound());
    }
}
