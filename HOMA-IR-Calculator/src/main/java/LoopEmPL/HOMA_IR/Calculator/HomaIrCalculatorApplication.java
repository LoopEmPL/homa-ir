
package LoopEmPL.HOMA_IR.Calculator;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;

@SpringBootApplication
@Controller
public class HomaIrCalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomaIrCalculatorApplication.class, args);
    }

    @GetMapping("/")
    public String showCalculator() {
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculateHomaIR(@Valid HomaIRRequest request, Model model) {
        try {
            double homaIRValue = request.calculateHomaIR();
            String interpretation = request.interpretHomaIR();
            model.addAttribute("result", new HomaIRResponse(homaIRValue, interpretation));
        } catch (Exception e) {
            model.addAttribute("error", "Error calculating HOMA-IR: " + e.getMessage());
        }
        return "calculator";
    }

    @RestController
    @RequestMapping("/api")
    public class ApiController {
        @PostMapping("/calculate")
        public ResponseEntity<?> calculateHomaIR(@Valid @RequestBody HomaIRRequest request) {
            try {
                double homaIRValue = request.calculateHomaIR();
                String interpretation = request.interpretHomaIR();
                return ResponseEntity.ok(new HomaIRResponse(homaIRValue, interpretation));
            } catch (Exception e) {
                return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error calculating HOMA-IR: " + e.getMessage()));
            }
        }
    }

    public static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
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
