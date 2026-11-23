package es.ubu.smartcampus.measurement;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MeasurementController.class)
class MeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MeasurementService measurementService;

    @Test
    void createReturns201() throws Exception {
        when(measurementService.create(eq(1L), any())).thenReturn(
                new MeasurementResponse(21L, 1L, LocalDateTime.of(2026, 11, 2, 14, 0), 23.4, "°C"));

        mockMvc.perform(post("/api/sensors/1/measurements").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": 23.4, \"unit\": \"°C\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sensorId").value(1));
    }

    @Test
    void createWithoutValueReturns400() throws Exception {
        mockMvc.perform(post("/api/sensors/1/measurements").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"unit\": \"°C\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields.value").exists());
    }

    @Test
    void createForMissingSensorReturns404() throws Exception {
        when(measurementService.create(eq(99L), any())).thenThrow(new NotFoundException("No existe el sensor 99"));

        mockMvc.perform(post("/api/sensors/99/measurements").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": 23.4, \"unit\": \"°C\"}"))
                .andExpect(status().isNotFound());
    }
}
