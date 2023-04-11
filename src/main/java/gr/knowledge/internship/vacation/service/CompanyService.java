package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.CompanyRepository;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import gr.knowledge.internship.vacation.service.mapper.CompanyMapper;
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
public class CompanyService {

    @Autowired
    private final CompanyRepository companyRepository;

    @Autowired
    private  EmployeeRepository employeeRepository;

    @Autowired
    private final CompanyMapper companyMapper;

    private static final String NotFoundExceptionMessage = "Not Found";


    public CompanyService(CompanyRepository companyRepository,CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    //create company
    @Transactional
    public CompanyDTO save(CompanyDTO companyDTO){
        log.debug("Request to save Company : {}",companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    //get company by id
    @Transactional(readOnly = true)
    public CompanyDTO getById(Long id){
        CompanyDTO result;
        log.debug("Request to get Company by id : {}",id);
        Optional<Company> company = companyRepository.findById(id);
        if(company.isPresent()){
            result = companyMapper.toDto(company.get());
        }else {
            throw new NotFoundException(NotFoundExceptionMessage);
        }
        return result;
    }

    //get all companies

    @Transactional(readOnly = true)
    public List<CompanyDTO> getAllCompanies(){
        List<Company> companies = companyRepository.findAll();
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for(Company company : companies){
            CompanyDTO companyDTO = companyMapper.toDto(company);
            companiesDTO.add(companyDTO);
        }
        return companiesDTO;
    }

    //update company
    @Transactional
    public CompanyDTO updateCompany(CompanyDTO companyDTO, Long id) {

            Company existingCompany = companyRepository.findById(id).orElseThrow();
            existingCompany.setId(companyDTO.getId());
            existingCompany.setName(companyDTO.getName());
            existingCompany.setAddress(companyDTO.getAddress());
            existingCompany.setPhone(companyDTO.getPhone());

            Company updatedCompany = companyRepository.save(existingCompany);

            return companyMapper.toDto(updatedCompany);

    }

    @Transactional
    public double getMonthlyExpensesByCompany(Long companyId){
        List<Employee> employees = employeeRepository.montlyExpense(companyId);
        Double totalSalary = 0.0;
        for(Employee employee:employees){
            totalSalary += employee.getSalary();
        }
        return totalSalary;
    }

    //delete company

    @Transactional
    public void deleteCompany(Long id){
        companyRepository.findById(id).orElseThrow();
        companyRepository.deleteById(id);

    }

}
