package gr.knowledge.internship.vacation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "bonus")
@AllArgsConstructor
@NoArgsConstructor
public class Bonus implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bonus_generator")
    @SequenceGenerator(name = "bonus_generator", sequenceName = "seq_bonus")
    @Column(name = "id" , nullable = false)
    Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee bonusEmployee;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Employee bonusCompany;

    @NotNull
    @Column(name="amount")
    Double amount;

}
