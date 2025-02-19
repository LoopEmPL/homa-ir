
package LoopEmPL.HOMA_IR.Calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HomaIrCalculatorApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private HomaIrCalculatorApplication.HomaIRRequest request;

    @BeforeEach
    void setUp() {
        request = new HomaIrCalculatorApplication.HomaIRRequest();
    }

    @Test
    void contextLoads() {
    }

    @ParameterizedTest
    @CsvSource({
        "10.5, 90.0, 42.0, Significant insulin resistance",
        "2.0, 80.0, 7.11, Early insulin resistance",
        "1.0, 70.0, 3.11, Early insulin resistance",
        "0.5, 60.0, 1.33, Normal insulin sensitivity"
    })
    void testHomaIRCalculationAndInterpretation(double insulin, double glucose, 
            double expectedHomaIR, String expectedInterpretation) {
        request.setFastingInsulin(insulin);
        request.setFastingGlucose(glucose);
        
        double actualHomaIR = request.calculateHomaIR();
        String actualInterpretation = request.interpretHomaIR();

        assertEquals(expectedHomaIR, actualHomaIR, 0.01);
        assertEquals(expectedInterpretation, actualInterpretation);
    }

    @Test
    void testCalculateEndpoint() throws Exception {
        HomaIrCalculatorApplication.HomaIRRequest testRequest = 
            new HomaIrCalculatorApplication.HomaIRRequest(10.5, 90.0);

        mockMvc.perform(post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.homaIRValue").value(42.0))
                .andExpect(jsonPath("$.interpretation").value("Significant insulin resistance"));
    }

    @Test
    void testInvalidInput() throws Exception {
        HomaIrCalculatorApplication.HomaIRRequest invalidRequest = 
            new HomaIrCalculatorApplication.HomaIRRequest(0.0, -1.0);

        mockMvc.perform(post("/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
