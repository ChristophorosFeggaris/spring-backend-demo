package gr.knowledge.internship.vacation.repository;

import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("Select p from Product p inner join EmployeeProduct ep on p.id=ep.product.id inner join Employee e on e.id=ep.employee.id where e.id=:employeeId")
    List<Employee> findByEmployeeId(@Param("employeeId") Long employeeId);
}
