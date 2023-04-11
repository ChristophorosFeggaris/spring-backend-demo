package gr.knowledge.internship.vacation.repository;

import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.domain.RequestByCompany;
import gr.knowledge.internship.vacation.domain.RequestStatus;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

}
