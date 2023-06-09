package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.CompanyService;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2

@RequestMapping("/api")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @CrossOrigin
    @PostMapping("/newcompany")
    public ResponseEntity<CompanyDTO> save(@RequestBody CompanyDTO companyDTO){
        log.debug("Rest request to save Company : {}",companyDTO);
        CompanyDTO result = companyService.save(companyDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/company/{id}")
    public CompanyDTO getCompanyById(@PathVariable Long id){
        return companyService.getById(id);
    }


    @CrossOrigin
    @GetMapping("/company")
    public List<CompanyDTO> getAllCompanies(){
        return companyService.getAllCompanies();
    }

    @CrossOrigin
    @PutMapping(value="/updatecompany/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompanyDTO updateCompany(@PathVariable Long id,@RequestBody CompanyDTO companyDTO){
        return companyService.updateCompany(companyDTO, id);
    }

    @CrossOrigin
    @DeleteMapping("/deletecompany/{id}")
    public void deleteCompany(@PathVariable Long id){
        companyService.deleteCompany(id);
    }

    @CrossOrigin
    @GetMapping("/getmonthlyexpensebycompany/{companyId}")
    public double getMonltyExpense(@PathVariable Long companyId){
        return companyService.getMonthlyExpensesByCompany(companyId);
    }

}
