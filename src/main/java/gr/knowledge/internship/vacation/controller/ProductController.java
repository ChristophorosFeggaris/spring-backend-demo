package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.domain.Product;
import gr.knowledge.internship.vacation.service.ProductService;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import gr.knowledge.internship.vacation.service.dto.ProductDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO){
        log.debug("Rest request to save Company : {}",productDTO);
        ProductDTO result = productService.save(productDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id){
        log.debug("Rest request to get Product by id : {}",id);
        ProductDTO result = productService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/getallproducts")
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }

    @PutMapping("/updateproduct/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        return productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/deleteproduct/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

    @GetMapping("/productsbycompany/{companyId}")
    public Map<String, List<Product>> getProductsByCompany(@PathVariable Long companyId){
        return productService.getProductsPerCompany(companyId);
    }
}
