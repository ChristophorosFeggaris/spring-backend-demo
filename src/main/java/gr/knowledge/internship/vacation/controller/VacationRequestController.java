package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.VacationRequestService;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class VacationRequestController {


    private final VacationRequestService vacationRequestService;

    public VacationRequestController(VacationRequestService vacationRequestService) {
        this.vacationRequestService = vacationRequestService;
    }

    @PostMapping("/vacationrequest")
    public ResponseEntity<VacationRequestDTO> createRequest(@RequestBody VacationRequestDTO vacationRequestDTO) {
        log.debug("Rest request to save Vacation Request : ", vacationRequestDTO);
        VacationRequestDTO result = vacationRequestService.save(vacationRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/vacationrequest/{id}")
    public ResponseEntity<VacationRequestDTO> getVacRequest(@PathVariable Long id) {
        log.debug("Rest request to get Vacation Request by id : {}", id);
        VacationRequestDTO result = vacationRequestService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getallrequests")
    public List<VacationRequestDTO> getAllVacRequests() {
        return vacationRequestService.getAllRequests();
    }

    @PutMapping("/updaterequest/{id}")
    public VacationRequestDTO updateVacRequest(@PathVariable Long id, @RequestBody VacationRequestDTO vacationRequestDTO){
        return vacationRequestService.updateRequest(id, vacationRequestDTO);
    }

    @DeleteMapping("/deleterequest/{id}")
    public void deleteVacRequest(@PathVariable Long id){
        vacationRequestService.deleteRequest(id);
    }
}
