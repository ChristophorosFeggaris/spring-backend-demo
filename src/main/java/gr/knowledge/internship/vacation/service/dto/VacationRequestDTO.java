package gr.knowledge.internship.vacation.service.dto;

import gr.knowledge.internship.vacation.domain.Employee;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class VacationRequestDTO implements Serializable {

    Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Employee vacreqEmployee;

    LocalDate startDate;

    LocalDate endDate;

    Integer days;
}
