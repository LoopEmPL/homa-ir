
package LoopEmPL.HOMA_IR.Calculator.controller;

import LoopEmPL.HOMA_IR.Calculator.HomaIrCalculatorApplication.HomaIRRequest;
import LoopEmPL.HOMA_IR.Calculator.HomaIrCalculatorApplication.HomaIRResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class HomaIRController {
    
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
}
