package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ProductDTO implements Serializable {

    Long id;

    @Size(max=255)
    String name;

    @Size(max=1000)
    String description;

    @Size(max=255)
    String barcode;
}
