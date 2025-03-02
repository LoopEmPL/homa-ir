package LoopEmPL.HOMA_IR.Calculator.controller;

import LoopEmPL.HOMA_IR.Calculator.entity.HomaIRResult;
import LoopEmPL.HOMA_IR.Calculator.model.HomaIRRequest;
import LoopEmPL.HOMA_IR.Calculator.model.HomaIRResponse;
import LoopEmPL.HOMA_IR.Calculator.service.HomaIRService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final HomaIRService homaIRService;

    @Autowired
    public ApiController(HomaIRService homaIRService) {
        this.homaIRService = homaIRService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateHomaIR(@Valid @RequestBody HomaIRRequest request) {
        try {
            // Use service to calculate and save in one operation
            HomaIRResponse response = homaIRService.calculateAndSaveResult(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error calculating HOMA-IR: " + e.getMessage()));
        }
    }

    @GetMapping("/results")
    public ResponseEntity<List<HomaIRResult>> getAllResults() {
        return ResponseEntity.ok(homaIRService.getAllResults());
    }
    
    @GetMapping("/homa-ir/history")
    public ResponseEntity<List<HomaIRResult>> getHomaIrHistory() {
        return ResponseEntity.ok(homaIRService.getAllResults());
    }

    @GetMapping("/results/{id}")
    public ResponseEntity<?> getResultById(@PathVariable Long id) {
        HomaIRResult result = homaIRService.getResultById(id);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
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
}