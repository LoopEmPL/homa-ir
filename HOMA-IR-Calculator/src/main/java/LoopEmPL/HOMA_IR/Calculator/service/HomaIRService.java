
package LoopEmPL.HOMA_IR.Calculator.service;

import LoopEmPL.HOMA_IR.Calculator.entity.HomaIRResult;
import LoopEmPL.HOMA_IR.Calculator.repository.HomaIRResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomaIRService {

    private final HomaIRResultRepository repository;

    @Autowired
    public HomaIRService(HomaIRResultRepository repository) {
        this.repository = repository;
    }

    public HomaIRResult saveResult(double fastingInsulin, double fastingGlucose, 
                                 double homaIRValue, String interpretation) {
        HomaIRResult result = new HomaIRResult(fastingInsulin, fastingGlucose, homaIRValue, interpretation);
        return repository.save(result);
    }

    public List<HomaIRResult> getAllResults() {
        return repository.findAll();
    }

    public HomaIRResult getResultById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
