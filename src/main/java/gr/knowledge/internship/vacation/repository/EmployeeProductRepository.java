package gr.knowledge.internship.vacation.repository;

import gr.knowledge.internship.vacation.domain.EmployeeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface EmployeeProductRepository extends JpaRepository<EmployeeProduct, Long>, JpaSpecificationExecutor<EmployeeProduct> {
}
