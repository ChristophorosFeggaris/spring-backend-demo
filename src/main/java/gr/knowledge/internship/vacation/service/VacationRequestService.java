package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.domain.VacationRequest;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.VacationRequestRepository;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import gr.knowledge.internship.vacation.service.mapper.VacationRequestMapper;
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
public class VacationRequestService {

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    private VacationRequestMapper vacationRequestMapper;

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

}
