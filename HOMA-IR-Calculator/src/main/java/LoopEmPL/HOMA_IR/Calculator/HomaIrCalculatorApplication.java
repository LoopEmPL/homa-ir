
package LoopEmPL.HOMA_IR.Calculator;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HomaIrCalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomaIrCalculatorApplication.class, args);
    }

    @PostMapping("/calculate")
    public HomaIRResponse calculateHomaIR(@RequestBody HomaIRRequest request) {
        double homaIRValue = request.calculateHomaIR();
        String interpretation = request.interpretHomaIR();
        return new HomaIRResponse(homaIRValue, interpretation);
    }

    public static class HomaIRResponse {
        private final double homaIRValue;
        private final String interpretation;

        public HomaIRResponse(double homaIRValue, String interpretation) {
            this.homaIRValue = homaIRValue;
            this.interpretation = interpretation;
        }

        public double getHomaIRValue() {
            return homaIRValue;
        }

        public String getInterpretation() {
            return interpretation;
        }
    }

    public static class HomaIRRequest {
        @NotNull(message = "Fasting insulin is required")
        @DecimalMin(value = "0.1", message = "Fasting insulin must be greater than 0.1")
        private double fastingInsulin;

        @NotNull(message = "Fasting glucose is required")
        @DecimalMin(value = "0.1", message = "Fasting glucose must be greater than 0.1")
        private double fastingGlucose;

        public HomaIRRequest() {}

        public HomaIRRequest(double fastingInsulin, double fastingGlucose) {
            this.fastingInsulin = fastingInsulin;
            this.fastingGlucose = fastingGlucose;
        }

        public double getFastingInsulin() {
            return fastingInsulin;
        }

        public void setFastingInsulin(double fastingInsulin) {
            this.fastingInsulin = fastingInsulin;
        }

        public double getFastingGlucose() {
            return fastingGlucose;
        }

        public void setFastingGlucose(double fastingGlucose) {
            this.fastingGlucose = fastingGlucose;
        }

        public double calculateHomaIR() {
            return (fastingInsulin * fastingGlucose) / 22.5;
        }

        public String interpretHomaIR() {
            double homaIR = calculateHomaIR();
            if (homaIR < 1.0) {
                return "Normal insulin sensitivity";
            } else if (homaIR <= 2.0) {
                return "Early insulin resistance";
            } else {
                return "Significant insulin resistance";
            }
        }
    }
}
