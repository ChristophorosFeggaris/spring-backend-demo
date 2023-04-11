package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.*;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.CompanyRepository;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
import gr.knowledge.internship.vacation.repository.VacationRequestRepository;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import gr.knowledge.internship.vacation.service.mapper.VacationRequestMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class VacationRequestService {

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private VacationRequestMapper vacationRequestMapper;
    @Autowired
    private EmployeeRepository employeeRepository;

    public VacationRequestService(VacationRequestRepository vacationRequestRepository, VacationRequestMapper vacationRequestMapper) {
        this.vacationRequestRepository = vacationRequestRepository;
        this.vacationRequestMapper = vacationRequestMapper;
    }

    private static final String NotFoundExceptionMessage = "Not Found";

    @Transactional
    public VacationRequestDTO save(VacationRequestDTO vacationRequestDTO){
        log.debug("Request to save Vacation Request: ",vacationRequestDTO);
        VacationRequest vacationRequest = vacationRequestMapper.toEntity(vacationRequestDTO);
        vacationRequest = vacationRequestRepository.save(vacationRequest);
        return vacationRequestMapper.toDTO(vacationRequest);
    }

    @Transactional(readOnly = true)
    public VacationRequestDTO getById(Long id){
        VacationRequestDTO result;
        log.debug("Request to get Vacation Request by id : ",id);
        Optional<VacationRequest> vacationRequest = vacationRequestRepository.findById(id);
        if(vacationRequest.isPresent()){
            result = vacationRequestMapper.toDTO(vacationRequest.get());
        }else {
            throw new NotFoundException(NotFoundExceptionMessage);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<VacationRequestDTO> getAllRequests(){
        List<VacationRequest> allRequests = vacationRequestRepository.findAll();
        List<VacationRequestDTO> requestDTOs = new ArrayList<>();
        for(VacationRequest request : allRequests){
            VacationRequestDTO requestDTO = vacationRequestMapper.toDTO(request);
            requestDTOs.add(requestDTO);
        }
        return requestDTOs;
    }

    @Transactional
    public VacationRequestDTO updateRequest(Long id, VacationRequestDTO vacationRequestDTO){
        log.debug("Request to update Vacation Request: ", vacationRequestDTO);
        VacationRequest vacationRequest = vacationRequestMapper.toEntity(vacationRequestDTO);
        if(vacationRequestRepository.existsById(id)){
            VacationRequest updatedRequest = vacationRequestRepository.save(vacationRequest);
        }else{
            throw new NotFoundException("The bonus "+vacationRequest+"does not found");
        }
        return vacationRequestMapper.toDTO(vacationRequest);
    }

    @Transactional
    public void deleteRequest(Long id){
        vacationRequestRepository.deleteById(id);
    }

    //task2
    @Transactional
    public VacationRequestDTO askForVacation(Request4Vac request){

        Employee employee = employeeRepository.findById(request.getEmployeeId()).orElseThrow();

        Long requestDays = ChronoUnit.DAYS.between(request.getStartDate(),request.getEndDate()) + 1 - request.getHoliday();

        if(employee.getVacationDays() < requestDays){
            throw new RuntimeException("Not enough remaining days");
        }

        VacationRequest vacationRequest = new VacationRequest();
        vacationRequest.setVacreqEmployee(employee);
        vacationRequest.setStartDate(request.getStartDate());
        vacationRequest.setEndDate(request.getEndDate());
        vacationRequest.setDays(request.getHoliday());
        vacationRequest.setStatus(String.valueOf(RequestStatus.PENDING));
        vacationRequestRepository.save(vacationRequest);

        return vacationRequestMapper.toDTO(vacationRequest);
    }

    public List<VacationRequestDTO> requestByCompany( Long companyId,
                                                      String status,
                                                       LocalDate startDate,
                                                      LocalDate endDate){
        return vacationRequestRepository.vacRequestByCompany(companyId, status, startDate, endDate);
    }

    public VacationRequestDTO updateVacationRequest(Long vacationId, String status){
        VacationRequest vacationRequest = vacationRequestRepository.findById(vacationId).orElseThrow(() -> new NotFoundException("The id does not found!"));
        if(vacationRequest.getStatus().equalsIgnoreCase("approved")){
            Employee employee = vacationRequest.getVacreqEmployee();
            employee.setVacationDays(employee.getVacationDays() - vacationRequest.getDays());
            vacationRequest.setStatus("approved");
            vacationRequestRepository.save(vacationRequest);
            employeeRepository.save(employee);
        } else if (vacationRequest.getStatus().equalsIgnoreCase("rejected")){
            vacationRequest.setStatus("rejected");
        }
        return vacationRequestMapper.toDTO(vacationRequest);
    }

}
