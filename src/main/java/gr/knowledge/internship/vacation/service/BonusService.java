package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.BonusRepository;
import gr.knowledge.internship.vacation.repository.CompanyRepository;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private BonusMapper bonusMapper;

    private static final String NotFoundExceptionMessage = "Not Found";

    public BonusService(BonusRepository bonusRepository, BonusMapper bonusMapper) {
        this.bonusRepository = bonusRepository;
        this.bonusMapper = bonusMapper;
    }



    public enum BonusRate {
        winter("winter", 1.3),
        autumn("autumn", 0.4),
        spring("spring", 0.6),
        summer("summer", 0.7);

        private final String season;
        private final Double rate;

        BonusRate(String season, Double rate) {
            this.season = season;
            this.rate = rate;
        }

        public String getSeason() {
            return season;
        }

        public Double getRate() {
            return rate;
        }
    }

    public Double getBonus(Double salary, String season){
        Double rate = BonusRate.valueOf(season).getRate();
        return salary * rate;
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

    public List<BonusDTO> getBonusEmployeeByCompany(Long companyId, String season){
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("Invalid company ID"));
        List<Employee> employees = employeeRepository.montlyExpense(companyId);
        List<Bonus> bonuses  = new ArrayList<>();
        for(Employee employee : employees){
            Bonus bonus = new Bonus();
            bonus.setBonusEmployee(employee);
            bonus.setAmount(getBonus(employee.getSalary(), season));
            bonus.setBonusCompany(company);
            bonuses.add(bonus);
        }
        List<Bonus> allBonuses = bonusRepository.saveAll(bonuses);
        return  bonusMapper.toDTOList(allBonuses);
    }

    @Transactional
    public void deleteBonus(Long id){
        bonusRepository.deleteById(id);
    }
}
