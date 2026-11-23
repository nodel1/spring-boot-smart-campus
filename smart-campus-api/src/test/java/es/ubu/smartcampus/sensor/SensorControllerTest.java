package es.ubu.smartcampus.sensor;

import es.ubu.smartcampus.common.ConflictException;
import es.ubu.smartcampus.common.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// solo la capa web: el servicio es un simulacro, así que no hace falta la base de datos
@WebMvcTest(SensorController.class)
class SensorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SensorService sensorService;

    private static final String VALID_SENSOR = """
            {"name": "Temperatura A1.2", "type": "TEMPERATURE", "location": "Edificio A - Aula A1.2",
             "ip": "192.168.10.13", "status": "ACTIVE"}
            """;

    @Test
    void createReturns201WithLocation() throws Exception {
        when(sensorService.create(any())).thenReturn(new SensorResponse(6L, "Temperatura A1.2",
                SensorType.TEMPERATURE, "Edificio A - Aula A1.2", "192.168.10.13", SensorStatus.ACTIVE));

        mockMvc.perform(post("/api/sensors").contentType(MediaType.APPLICATION_JSON).content(VALID_SENSOR))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/sensors/6"))
                .andExpect(jsonPath("$.id").value(6));
    }

    @Test
    void createWithoutNameAndBadIpReturns400WithFields() throws Exception {
        String body = """
                {"type": "TEMPERATURE", "location": "Edificio A", "ip": "999.1.1.1", "status": "ACTIVE"}
                """;

        mockMvc.perform(post("/api/sensors").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields.name").exists())
                .andExpect(jsonPath("$.fields.ip").exists());
    }

    @Test
    void createWithUnknownStatusReturns400() throws Exception {
        String body = VALID_SENSOR.replace("ACTIVE", "ROTO");

        mockMvc.perform(post("/api/sensors").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields.status").exists());
    }

    @Test
    void findMissingSensorReturns404() throws Exception {
        when(sensorService.findById(99L)).thenThrow(new NotFoundException("No existe el sensor 99"));

        mockMvc.perform(get("/api/sensors/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No existe el sensor 99"));
    }

    @Test
    void deleteSensorWithMeasurementsReturns409() throws Exception {
        doThrow(new ConflictException("tiene medidas")).when(sensorService).delete(1L);

        mockMvc.perform(delete("/api/sensors/1")).andExpect(status().isConflict());
    }

    @Test
    void deleteSensorReturns204() throws Exception {
        mockMvc.perform(delete("/api/sensors/5")).andExpect(status().isNoContent());
    }
}
