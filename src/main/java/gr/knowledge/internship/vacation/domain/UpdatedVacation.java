package gr.knowledge.internship.vacation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedVacation {

    Long vacationId;

    @Size(max=20)
    String status;
}
