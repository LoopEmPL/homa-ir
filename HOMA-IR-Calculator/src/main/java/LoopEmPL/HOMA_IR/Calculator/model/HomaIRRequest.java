package LoopEmPL.HOMA_IR.Calculator.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class HomaIRRequest {
    @NotNull(message = "Fasting insulin is required")
    @DecimalMin(value = "0.0", message = "Fasting insulin must be greater than 0")
    private Double fastingInsulin;

    @NotNull(message = "Fasting glucose is required")
    @DecimalMin(value = "0.0", message = "Fasting glucose must be greater than 0")
    private Double fastingGlucose;

    public HomaIRRequest() {
    }

    public HomaIRRequest(Double fastingInsulin, Double fastingGlucose) {
        this.fastingInsulin = fastingInsulin;
        this.fastingGlucose = fastingGlucose;
    }

    public Double getFastingInsulin() {
        return fastingInsulin;
    }

    public void setFastingInsulin(Double fastingInsulin) {
        this.fastingInsulin = fastingInsulin;
    }

    public Double getFastingGlucose() {
        return fastingGlucose;
    }

    public void setFastingGlucose(Double fastingGlucose) {
        this.fastingGlucose = fastingGlucose;
    }

    // Calculation logic moved to HomaIRService
}