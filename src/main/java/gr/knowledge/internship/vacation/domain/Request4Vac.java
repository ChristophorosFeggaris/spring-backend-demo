package gr.knowledge.internship.vacation.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Request4Vac {
    Long employeeId;
    LocalDate startDate;
    LocalDate endDate;
    Integer holiday;
}


