package LoopEmPL.HOMA_IR.Calculator;

import jakarta.validation.constraints.DecimaMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.SpringApplication;


@SpringBootApplication

	public class HomaIRRequest{
		@NotNull(message = "Fasting insulin is required")
		@DecimaMin(value = 0.1, message = "Fasting insulin must be greater than 0.1")
		private double fastingInsulin;
		@NotNull(message = "Fasting glucose is required")
		@DecimaMin(value = 0.1, message = "Fasting glucose must be greater than 0.)

		private double fastingGlucose;

		public HomaIRRequest() {}

		public HomaIRRequest(double fastingInsulin, double fastingGlucose){
			this.fastingInsulin = fastingInsulin;
			this.fastingGlucose = fastingGlucose;
		}

		public void setFastingInsulin(double fastingInsulin){
			this.fastingInsulin = fastingInsulin;
			}
		public Double getFastingGlucose() {
			return fastingGlucose;
		}

		public void setFastingGlucose(double fastingGlucose) {
			this.fastingGlucose = fastingGlucose;
		}
	}

		
	} HomaIrCalculatorApplication	
public class HomaIrCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomaIrCalculatorApplication.class, args);

		
	}

}
