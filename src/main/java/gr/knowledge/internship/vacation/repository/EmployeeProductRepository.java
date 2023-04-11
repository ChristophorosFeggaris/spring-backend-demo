package gr.knowledge.internship.vacation.repository;

import gr.knowledge.internship.vacation.domain.EmployeeProduct;
import gr.knowledge.internship.vacation.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface EmployeeProductRepository extends JpaRepository<EmployeeProduct, Long>, JpaSpecificationExecutor<EmployeeProduct> {
    @Query("Select ep from EmployeeProduct ep inner join Employee e on e.id=ep.employee.id where ep.employee.id=:employeeId")
    List<EmployeeProduct> employeeProductByEmployeeId(@Param("employeeId") Long employeeId);
}
