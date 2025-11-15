package com.UserManagement.dto;
import com.UserManagement.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private double price;
    private Long categoryId;

    public static ProductDTO setResponse(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setPrice(product.getPrice());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        return productDTO;
    }
}
