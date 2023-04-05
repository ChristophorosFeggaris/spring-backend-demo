package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.BonusService;
import gr.knowledge.internship.vacation.service.EmployeeProductService;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.dto.EmployeeProductDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class EmployeeProductController {

    private final EmployeeProductService employeeProductService;

    public EmployeeProductController(EmployeeProductService employeeProductService) {
        this.employeeProductService = employeeProductService;
    }

    @PostMapping("/employeeproduct")
    public ResponseEntity<EmployeeProductDTO> createEmProduct(@RequestBody EmployeeProductDTO employeeProductDTO) {
        log.debug("Rest request to save EmployeeProduct : ", employeeProductDTO);
        EmployeeProductDTO result = employeeProductService.save(employeeProductDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/employeeproduct/{id}")
    public ResponseEntity<EmployeeProductDTO> getEmProduct(@PathVariable Long id) {
        log.debug("Rest request to get EmployeeProduct by id : ", id);
        EmployeeProductDTO result = employeeProductService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getallemplpyeeproducts")
    public List<EmployeeProductDTO> getAllEmProducts() {
        return employeeProductService.getAllEmployeeProducts();
    }

    @PutMapping("/updateemproduct/{id}")
    public EmployeeProductDTO updateEmProduct(@PathVariable Long id, @RequestBody EmployeeProductDTO employeeProductDTO){
        return employeeProductService.updateEmpProduct(id, employeeProductDTO);
    }

    @DeleteMapping("/deleteemproduct/{id}")
    public void deleteEmProduct(@PathVariable Long id){
        employeeProductService.deleteEmProduct(id);
    }
}
