package gr.knowledge.internship.vacation.repository;

import gr.knowledge.internship.vacation.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    @Query("select e from Company c inner join Employee e on c.id=e.employeeCompany.id where e.employeeCompany.id=:companyId ")
    List<Employee> montlyExpense(@Param("companyId") Long companyId);

    @Query("select e from Company c inner join Bonus b on c.id=b.bonusCompany.id inner join Employee e on e.id=b.bonusEmployee.id where b.bonusCompany.id=:companyId ")
    List<Employee> bonusPerEmployeePerCompany(@Param("companyId") Long companyId);

    @Query("Select e from EmployeeProduct ep inner join Employee e on e.id=ep.employee.id inner join Company c on c.id = e.employeeCompany.id inner join Product p on p.id=ep.product.id where e.employeeCompany.id=:companyId")
    List<Employee> employeesByCompany(@Param("companyId") Long companyId);
}

