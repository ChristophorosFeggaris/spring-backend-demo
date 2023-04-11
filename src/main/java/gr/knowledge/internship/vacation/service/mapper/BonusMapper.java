package gr.knowledge.internship.vacation.service.mapper;

import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BonusMapper extends ModelMapper {

    public BonusDTO toDTO(Bonus bonus){
        return this.map(bonus, BonusDTO.class);
    }

    public Bonus toEntity(BonusDTO bonusDTO){
        return this.map(bonusDTO, Bonus.class);
    }

    public List<BonusDTO> toDTOList(List<Bonus> allBonuses){
        List<BonusDTO> dtos = new ArrayList<>();
        for(Bonus bonus : allBonuses){
            dtos.add(toDTO(bonus));
        }
        return dtos;
    }
}
