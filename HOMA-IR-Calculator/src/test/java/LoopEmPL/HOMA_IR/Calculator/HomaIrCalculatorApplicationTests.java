package LoopEmPL.HOMA_IR.Calculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HomaIrCalculatorApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testHomaIRCalculation() {
		HomaIrCalculatorApplication.HomaIRRequest request = 
			new HomaIrCalculatorApplication.HomaIRRequest(10.5, 90.0);
		
		double expectedHomaIR = (10.5 * 90.0) / 22.5;
		assert Math.abs(request.calculateHomaIR() - expectedHomaIR) < 0.001;
		
		String interpretation = request.interpretHomaIR();
		System.out.println("HOMA-IR Value: " + request.calculateHomaIR());
		System.out.println("Interpretation: " + interpretation);
	}
}
