package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.domain.EmployeeProduct;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.BonusRepository;
import gr.knowledge.internship.vacation.repository.EmployeeProductRepository;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.dto.EmployeeProductDTO;
import gr.knowledge.internship.vacation.service.mapper.BonusMapper;
import gr.knowledge.internship.vacation.service.mapper.EmployeeProductMapper;
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
public class EmployeeProductService {

    @Autowired
    private EmployeeProductRepository employeeProductRepository;

    private EmployeeProductMapper employeeProductMapper;

    private static final String NotFoundExceptionMessage = "Not Found";

    public EmployeeProductService(EmployeeProductRepository employeeProductRepository, EmployeeProductMapper employeeProductMapper) {
        this.employeeProductRepository = employeeProductRepository;
        this.employeeProductMapper = employeeProductMapper;
    }

    @Transactional
    public EmployeeProductDTO save(EmployeeProductDTO employeeProductDTO){
        log.debug("Request to save EmployeeProduct : ",employeeProductDTO);
        EmployeeProduct employeeProduct = employeeProductMapper.toEntity(employeeProductDTO);
        employeeProduct = employeeProductRepository.save(employeeProduct);
        return employeeProductMapper.toDTO(employeeProduct);
    }

    @Transactional(readOnly = true)
    public EmployeeProductDTO getById(Long id){
        EmployeeProductDTO result;
        log.debug("Request to get EmployeeProduct by id : ",id);
        Optional<EmployeeProduct> employeeProduct = employeeProductRepository.findById(id);
        if(employeeProduct.isPresent()){
            result = employeeProductMapper.toDTO(employeeProduct.get());
        }else {
            throw new NotFoundException(NotFoundExceptionMessage);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<EmployeeProductDTO> getAllEmployeeProducts(){
        List<EmployeeProduct> allEmployeeProducts = employeeProductRepository.findAll();
        List<EmployeeProductDTO> employeeProductDTOS = new ArrayList<>();
        for(EmployeeProduct emProduct : allEmployeeProducts){
            EmployeeProductDTO emProductDTO = employeeProductMapper.toDTO(emProduct);
            employeeProductDTOS.add(emProductDTO);
        }
        return employeeProductDTOS;
    }

    @Transactional
    public EmployeeProductDTO updateEmpProduct(Long id, EmployeeProductDTO employeeProductDTO){
        log.debug("Request to update EmployeeProduct: ", employeeProductDTO);
        EmployeeProduct employeeProduct = employeeProductMapper.toEntity(employeeProductDTO);
        if(employeeProductRepository.existsById(id)){
            EmployeeProduct updatedEmProduct = employeeProductRepository.save(employeeProduct);
        }else{
            throw new NotFoundException("The bonus "+employeeProduct+"does not found");
        }
        return employeeProductMapper.toDTO(employeeProduct);
    }

    @Transactional
    public void deleteEmProduct(Long id){
        employeeProductRepository.deleteById(id);
    }
}
