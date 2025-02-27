
package LoopEmPL.HOMA_IR.Calculator.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "homa_ir_results")
public class HomaIRResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private double fastingInsulin;
    
    @Column(nullable = false)
    private double fastingGlucose;
    
    @Column(nullable = false)
    private double homaIRValue;
    
    @Column(nullable = false)
    private String interpretation;
    
    @Column(nullable = false)
    private LocalDateTime calculatedAt;
    
    // Default constructor required by JPA
    public HomaIRResult() {
        this.calculatedAt = LocalDateTime.now();
    }
    
    public HomaIRResult(double fastingInsulin, double fastingGlucose, 
                         double homaIRValue, String interpretation) {
        this.fastingInsulin = fastingInsulin;
        this.fastingGlucose = fastingGlucose;
        this.homaIRValue = homaIRValue;
        this.interpretation = interpretation;
        this.calculatedAt = LocalDateTime.now();
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public double getHomaIRValue() {
        return homaIRValue;
    }
    
    public void setHomaIRValue(double homaIRValue) {
        this.homaIRValue = homaIRValue;
    }
    
    public String getInterpretation() {
        return interpretation;
    }
    
    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }
    
    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }
    
    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
    }
    
    @Override
    public String toString() {
        return "HomaIRResult{" +
                "id=" + id +
                ", fastingInsulin=" + fastingInsulin +
                ", fastingGlucose=" + fastingGlucose +
                ", homaIRValue=" + homaIRValue +
                ", interpretation='" + interpretation + '\'' +
                ", calculatedAt=" + calculatedAt +
                '}';
    }
}
