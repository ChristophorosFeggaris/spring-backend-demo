package gr.knowledge.internship.vacation.repository;

import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface BonusRepository extends JpaRepository<Bonus, Long>, JpaSpecificationExecutor<Bonus> {


}
