package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.BonusRepository;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import gr.knowledge.internship.vacation.service.mapper.BonusMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class BonusService {

    @Autowired
    private BonusRepository bonusRepository;

    private BonusMapper bonusMapper;

    private static final String NotFoundExceptionMessage = "Not Found";

    public BonusService(BonusRepository bonusRepository, BonusMapper bonusMapper) {
        this.bonusRepository = bonusRepository;
        this.bonusMapper = bonusMapper;
    }

    @Transactional
    public BonusDTO save(BonusDTO bonusDTO){
        log.debug("Request to save Bonus : {}",bonusDTO);
        Bonus bonus = bonusMapper.toEntity(bonusDTO);
        bonus = bonusRepository.save(bonus);
        return bonusMapper.toDTO(bonus);
    }

    @Transactional(readOnly = true)
    public BonusDTO getById(Long id){
        BonusDTO result;
        log.debug("Request to get Bonus by id : {}",id);
        Optional<Bonus> bonus = bonusRepository.findById(id);
        if(bonus.isPresent()){
            result = bonusMapper.toDTO(bonus.get());
        }else {
            throw new NotFoundException(NotFoundExceptionMessage);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<BonusDTO> getAllBonus(){
        List<Bonus> allBonus = bonusRepository.findAll();
        List<BonusDTO> bonusDTOs = new ArrayList<>();
        for(Bonus bonus : allBonus){
            BonusDTO bonusDTO = bonusMapper.toDTO(bonus);
            bonusDTOs.add(bonusDTO);
        }
        return bonusDTOs;
    }

    @Transactional
    public BonusDTO updateBonus(Long id, BonusDTO bonusDTO){
        log.debug("Request to update Bonues: ", bonusDTO);
        Bonus bonus = bonusMapper.toEntity(bonusDTO);
        if(bonusRepository.existsById(id)){
            Bonus updatedBonus = bonusRepository.save(bonus);
        }else{
            throw new NotFoundException("The bonus "+bonus+"does not found");
        }
        return bonusMapper.toDTO(bonus);
    }

    @Transactional
    public void deleteBonus(Long id){
        bonusRepository.deleteById(id);
    }
}
