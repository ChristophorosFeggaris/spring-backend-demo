package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.BonusService;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class BonusController {

    private final BonusService bonusService;

    public BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @PostMapping("/bonus")
    public ResponseEntity<BonusDTO> createBonus(@RequestBody BonusDTO bonusDTO) {
        log.debug("Rest request to save Bonus : ", bonusDTO);
        BonusDTO result = bonusService.save(bonusDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/bonus/{id}")
    public ResponseEntity<BonusDTO> getBonus(@PathVariable Long id) {
        log.debug("Rest request to get Bonus by id : {}", id);
        BonusDTO result = bonusService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getallbonus")
    public List<BonusDTO> getAllBonus() {
        return bonusService.getAllBonus();
    }

    @PutMapping("/updatebonus/{id}")
    public BonusDTO updateBonus(@PathVariable Long id, @RequestBody BonusDTO bonusDTO){
        return bonusService.updateBonus(id, bonusDTO);
    }

    @DeleteMapping("/deletebonus/{id}")
    public void deleteBonus(@PathVariable Long id){
        bonusService.deleteBonus(id);
    }
}
