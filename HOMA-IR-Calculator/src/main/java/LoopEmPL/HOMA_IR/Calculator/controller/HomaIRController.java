
package LoopEmPL.HOMA_IR.Calculator.controller;

import LoopEmPL.HOMA_IR.Calculator.entity.HomaIRResult;
import LoopEmPL.HOMA_IR.Calculator.model.HomaIRRequest;
import LoopEmPL.HOMA_IR.Calculator.model.HomaIRResponse;
import LoopEmPL.HOMA_IR.Calculator.service.HomaIRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

import java.util.List;

@Controller
public class HomaIRController {
    
    private final HomaIRService homaIRService;
    
    @Autowired
    public HomaIRController(HomaIRService homaIRService) {
        this.homaIRService = homaIRService;
    }
    
    @GetMapping("/")
    public String showCalculator(Model model) {
        model.addAttribute("request", new HomaIRRequest());
        return "calculator";
    }

    @PostMapping("/calculate")
    public String calculateHomaIR(@Valid HomaIRRequest request, Model model) {
        try {
            double homaIRValue = request.calculateHomaIR();
            String interpretation = request.interpretHomaIR();
            
            // Save the result to the database
            HomaIRResult savedResult = homaIRService.saveResult(
                request.getFastingInsulin(),
                request.getFastingGlucose(),
                homaIRValue,
                interpretation
            );
            
            model.addAttribute("result", new HomaIRResponse(homaIRValue, interpretation, savedResult.getId()));
        } catch (Exception e) {
            model.addAttribute("error", "Error calculating HOMA-IR: " + e.getMessage());
        }
        
        // Add the request back to the model for form re-population
        model.addAttribute("request", request);
        return "calculator";
    }
    
    @GetMapping("/history")
    public String showHistory(Model model) {
        List<HomaIRResult> results = homaIRService.getAllResults();
        model.addAttribute("results", results);
        return "history";
    }
    
    @GetMapping("/result/{id}")
    public String showResult(@PathVariable Long id, Model model) {
        HomaIRResult result = homaIRService.getResultById(id);
        if (result != null) {
            model.addAttribute("result", result);
            return "result";
        } else {
            return "redirect:/";
        }
    }
}
