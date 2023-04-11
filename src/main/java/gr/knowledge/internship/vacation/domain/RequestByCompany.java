package gr.knowledge.internship.vacation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestByCompany {

    Long companyId;

    @Size(max=20)
    String status;

    LocalDate startDate;

    LocalDate endDate;
}
