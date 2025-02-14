
package LoopEmPL.HOMA_IR.Calculator;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomaIrCalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomaIrCalculatorApplication.class, args);
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
    }
}
