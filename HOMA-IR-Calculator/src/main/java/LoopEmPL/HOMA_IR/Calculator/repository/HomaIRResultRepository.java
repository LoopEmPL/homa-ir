
package LoopEmPL.HOMA_IR.Calculator.repository;

import LoopEmPL.HOMA_IR.Calculator.entity.HomaIRResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomaIRResultRepository extends JpaRepository<HomaIRResult, Long> {
    // You can add custom query methods here if needed
}
