package LoopEmPL.HOMA_IR.Calculator.model;

public class HomaIRResponse {
    private final double homaIRValue;
    private final String interpretation;
    private final Long resultId;

    public HomaIRResponse(double homaIRValue, String interpretation) {
        this(homaIRValue, interpretation, null);
    }

    public HomaIRResponse(double homaIRValue, String interpretation, Long resultId) {
        this.homaIRValue = homaIRValue;
        this.interpretation = interpretation;
        this.resultId = resultId;
    }

    public double getHomaIRValue() {
        return homaIRValue;
    }

    public String getInterpretation() {
        return interpretation;
    }

    public Long getResultId() {
        return resultId;
    }
}