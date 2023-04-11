package gr.knowledge.internship.vacation.repository;

import gr.knowledge.internship.vacation.domain.RequestByCompany;
import gr.knowledge.internship.vacation.domain.RequestStatus;
import gr.knowledge.internship.vacation.domain.VacationRequest;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long>, JpaSpecificationExecutor<VacationRequest> {

    @Query("Select new gr.knowledge.internship.vacation.domain.RequestByCompany(comp.id, vr.status, vr.startDate,vr.endDate ) from VacationRequest vr  join vr.vacreqEmployee emp  join emp.employeeCompany comp  WHERE comp.id = :companyId AND vr.status = :status AND vr.startDate >= :startDate AND vr.endDate <= :endDate")
    List<VacationRequestDTO> vacRequestByCompany(@Param("companyId") Long companyId,
                                                 @Param("status") String status,
                                                 @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);


}
