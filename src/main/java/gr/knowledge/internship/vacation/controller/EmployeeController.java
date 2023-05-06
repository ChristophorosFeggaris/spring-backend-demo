package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.EmployeeService;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/newemployee")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO){
        log.debug("Rest request to save Company : {}",employeeDTO);
        EmployeeDTO result = employeeService.save(employeeDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id){
        log.debug("Rest request to get Employee by id : {}",id);
        EmployeeDTO result = employeeService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getallemployees")
    public List<EmployeeDTO> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @PutMapping("/updateemployee/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO){
        return employeeService.updateEmployee(employeeDTO, id);
    }

    @DeleteMapping("/deleteemployee/{id}")
    public void deleteEmployee(@PathVariable Long id){
        employeeService.deleteEmployee(id);
    }

}