package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.domain.Request4Vac;
import gr.knowledge.internship.vacation.domain.RequestByCompany;
import gr.knowledge.internship.vacation.domain.RequestStatus;
import gr.knowledge.internship.vacation.domain.UpdatedVacation;
import gr.knowledge.internship.vacation.service.VacationRequestService;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.LongAccumulator;

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

    @PostMapping("/askforvacation")
    public VacationRequestDTO ask4Vacation(@RequestBody Request4Vac request){
        return vacationRequestService.askForVacation(request);
    }

    @PostMapping("/vacrequestbycompany")
    public List<VacationRequestDTO> getRequestByCompany(@RequestBody RequestByCompany rqCompany){
        return vacationRequestService.requestByCompany(rqCompany.getCompanyId(), rqCompany.getStatus(), rqCompany.getStartDate(), rqCompany.getEndDate());
    }

    @PutMapping("/updatevacation")
    public VacationRequestDTO updateVacationRequest(@RequestParam Long vacationId, UpdatedVacation updatedVacation){
        return vacationRequestService.updateVacationRequest(updatedVacation.getVacationId(), updatedVacation.getStatus());
    }
}
