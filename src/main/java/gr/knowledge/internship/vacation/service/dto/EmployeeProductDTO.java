package gr.knowledge.internship.vacation.service.dto;

import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.domain.Product;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
public class EmployeeProductDTO implements Serializable {

    Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Employee employee;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;
}
