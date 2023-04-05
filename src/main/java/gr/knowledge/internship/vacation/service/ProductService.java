package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.domain.Product;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.ProductRepository;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import gr.knowledge.internship.vacation.service.dto.ProductDTO;
import gr.knowledge.internship.vacation.service.mapper.ProductMapper;
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
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    private static final String NotFoundExceptionMessage = "Not Found";

    @Transactional
    public ProductDTO save(ProductDTO productDTO){
        log.debug("Request to save Product : ",productDTO);
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Transactional(readOnly = true)
    public ProductDTO getById(Long id){
        ProductDTO result;
        log.debug("Request to get Product by id : ",id);
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            result = productMapper.toDTO(product.get());
        }else {
            throw new NotFoundException(NotFoundExceptionMessage);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for(Product product : products){
            ProductDTO productDTO = productMapper.toDTO(product);
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO){
        log.debug("Request to update Product: ", productDTO);
        Product product = productMapper.toEntity(productDTO);
        if(productRepository.existsById(id)){
            Product updatedProduct = productRepository.save(product);
        }else{
            throw new NotFoundException("The Product "+product+"does not exists");
        }
        return productMapper.toDTO(product);
    }

    @Transactional
    public void deleteProduct(Long id){

        productRepository.deleteById(id);

    }

}
