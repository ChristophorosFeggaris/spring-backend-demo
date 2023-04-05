package gr.knowledge.internship.vacation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "vacation_request")
@AllArgsConstructor
@NoArgsConstructor
public class VacationRequest implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacation_request_generator")
    @SequenceGenerator(name = "vacation_request_generator", sequenceName = "vacation_request_seq")
    @Column(name = "id")
    Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee vacreqEmployee;

    @NotNull
    @Column(name="start_date")
    LocalDate startDate;

    @NotNull
    @Column(name="end_date")
    LocalDate endDate;

    @NotNull
    @Size(max = 20)
    @Column(name="status")
    String status;

    @NotNull
    @Column(name="days")
    Integer days;

}
