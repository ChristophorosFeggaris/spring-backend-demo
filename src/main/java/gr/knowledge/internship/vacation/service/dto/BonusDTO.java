package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
public class BonusDTO implements Serializable {

    Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmployeeDTO bonusEmployee;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CompanyDTO bonusCompany;

    Double amount;
}
