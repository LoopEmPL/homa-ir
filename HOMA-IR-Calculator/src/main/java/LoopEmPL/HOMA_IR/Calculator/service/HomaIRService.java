
package LoopEmPL.HOMA_IR.Calculator.service;

import LoopEmPL.HOMA_IR.Calculator.entity.HomaIRResult;
import LoopEmPL.HOMA_IR.Calculator.model.HomaIRRequest;
import LoopEmPL.HOMA_IR.Calculator.model.HomaIRResponse;
import LoopEmPL.HOMA_IR.Calculator.repository.HomaIRResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HomaIRService {

    private final HomaIRResultRepository repository;

    @Autowired
    public HomaIRService(HomaIRResultRepository repository) {
        this.repository = repository;
    }

    public HomaIRResponse calculateAndSaveResult(HomaIRRequest request) {
        // Calculate HOMA-IR value
        double homaIRValue = calculateHomaIR(request.getFastingInsulin(), request.getFastingGlucose());
        
        // Determine interpretation
        String interpretation = interpretHomaIR(homaIRValue);
        
        // Save to database
        HomaIRResult result = new HomaIRResult(
            request.getFastingInsulin(),
            request.getFastingGlucose(),
            homaIRValue,
            interpretation
        );
        
        // Ensure timestamp is set
        result.setCalculatedAt(LocalDateTime.now());
        
        // Save and get the ID
        HomaIRResult savedResult = repository.save(result);
        
        // Return response with ID
        return new HomaIRResponse(homaIRValue, interpretation, savedResult.getId());
    }
    
    public double calculateHomaIR(double fastingInsulin, double fastingGlucose) {
        // HOMA-IR = (fasting insulin × fasting glucose) / 22.5
        // where insulin is in μU/mL and glucose is in mg/dL
        return (fastingInsulin * fastingGlucose) / 22.5;
    }
    
    public String interpretHomaIR(double homaIRValue) {
        if (homaIRValue < 1.0) {
            return "Normal insulin sensitivity";
        } else if (homaIRValue >= 1.0 && homaIRValue <= 1.9) {
            return "Moderate insulin sensitivity";
        } else if (homaIRValue > 1.9 && homaIRValue < 2.9) {
            return "Moderate insulin resistance";
        } else {
            return "Significant insulin resistance";
        }
    }

    public HomaIRResult saveResult(double fastingInsulin, double fastingGlucose, 
                                 double homaIRValue, String interpretation) {
        HomaIRResult result = new HomaIRResult(fastingInsulin, fastingGlucose, homaIRValue, interpretation);
        result.setCalculatedAt(LocalDateTime.now());
        return repository.save(result);
    }

    public List<HomaIRResult> getAllResults() {
        return repository.findAll();
    }

    public HomaIRResult getResultById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
