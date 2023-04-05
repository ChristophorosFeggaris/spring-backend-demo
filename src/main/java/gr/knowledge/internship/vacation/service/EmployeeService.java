package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.CompanyRepository;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import gr.knowledge.internship.vacation.service.mapper.EmployeeMapper;
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
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private EmployeeMapper employeeMapper;

    private static final String NotFoundExceptionMessage = "Not Found";


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Transactional
    public EmployeeDTO save(EmployeeDTO employeeDTO){
        log.debug("Request to save Company : {}",employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getById(Long id){
        EmployeeDTO result;
        log.debug("Request to get Employee by id : {}",id);
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            result = employeeMapper.toDto(employee.get());
        }else {
            throw new NotFoundException(NotFoundExceptionMessage);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeesDTOs = new ArrayList<>();
        for(Employee employee : employees){
            EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
            employeesDTOs.add(employeeDTO);
        }
        return employeesDTOs;
    }

    @Transactional
    public EmployeeDTO updateEmployee (EmployeeDTO employeeDTO, Long id){
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Employee does not found!"));

        existingEmployee.setSurName(employeeDTO.getSurName());
        existingEmployee.setId(employeeDTO.getId());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setStartDate(employeeDTO.getStartDate());
        existingEmployee.setVacationDays(employeeDTO.getVacationDays());
        existingEmployee.setSalary(employeeDTO.getSalary());
        existingEmployee.setEmploymentType(employeeDTO.getEmploymentType());
        existingEmployee.setName(employeeDTO.getName());

        CompanyDTO companyDTO = employeeDTO.getEmployeeCompany();
        if(companyDTO!= null){
            Company company = existingEmployee.getEmployeeCompany();
            if(company==null){
                company = new Company();
            }
            company.setId(companyDTO.getId());
            company.setName(companyDTO.getName());
            company.setAddress(companyDTO.getAddress());
            company.setPhone(companyDTO.getPhone());
        }

        Employee updatedCompany = employeeRepository.save(existingEmployee);

        return employeeMapper.toDto(updatedCompany);
    }

    @Transactional
    public void deleteEmployee(Long id){
        employeeRepository.findById(id).orElseThrow();
        employeeRepository.deleteById(id);

    }
}
