package com.UserManagement.service;

import com.UserManagement.dto.ProductDTO;
import com.UserManagement.entity.Category;
import com.UserManagement.entity.Product;
import com.UserManagement.exceptions.Response;
import com.UserManagement.exceptions.UMSResponse;
import com.UserManagement.repository.CategoryRepository;
import com.UserManagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Response createProduct(ProductDTO productDTO) {
        Response response = new Response();
        Product product = new Product();
        try {
            if (productDTO == null) {
                response.setResponse(UMSResponse.PRODUCT_NOT_FOUND);
                response.setMessage("Product object is null");
                return response;
            }

            if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
                response.setResponse(UMSResponse.INVALID_USER_DATA);
                response.setMessage("Product name cannot be empty");
                return response;
            }

            if (productDTO.getPrice() <= 0) {
                response.setResponse(UMSResponse.INVALID_USER_DATA);
                response.setMessage("Product price must be greater than 0");
                return response;
            }

            if (productDTO.getCategoryId() == null || productDTO.getCategoryId() == 0) {
                response.setResponse(UMSResponse.INVALID_USER_DATA);
                response.setMessage("Product category is required");
                return response;
            }

            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());

            product.setCategory(category);

            Product savedProduct = productRepository.save(product);
            ProductDTO setResponse = ProductDTO.setResponse(savedProduct);

            response.setResponse(UMSResponse.PRODUCT_CREATED_SUCCESSFULLY);
            response.setData("product", setResponse);

        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error creating product: " + e.getMessage());
        }
        return response;
    }

    public Response updateProduct(Long id, ProductDTO productDTO){
        Response response = new Response();
        try {
            if (!productRepository.existsById(id)) {
                response.setResponse(UMSResponse.PRODUCT_NOT_FOUND);
                response.setMessage("Product with id " + id + " not found");
                return response;
            }
            if(productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
                response.setResponse(UMSResponse.INVALID_USER_DATA);
                response.setMessage("Product name cannot be empty");
                return response;
            }
            if(productDTO.getPrice() <= 0) {
                response.setResponse(UMSResponse.INVALID_USER_DATA);
                response.setMessage("Product price must be greater than 0");
                return response;
            }
            if(productDTO.getCategoryId() == null || productDTO.getCategoryId() == 0) {
                response.setResponse(UMSResponse.INVALID_USER_DATA);
                response.setMessage("Product category is required");
                return response;
            }

            Product product = productRepository.findProductById(id);
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setCategory(product.getCategory());

            Product updatedProduct = productRepository.save(product);

            response.setCode("200");
            response.setMessage("Product updated successfully");
            response.setData("product", updatedProduct);
        }catch(Exception e) {
            e.printStackTrace();
            response.setMessage("Error updating product: " + e.getMessage());
        }
        return response;
    }

    public Response deleteProduct(Long id){
        Response response = new Response();
        try {
            if (!productRepository.existsById(id)) {
                response.setResponse(UMSResponse.PRODUCT_NOT_FOUND);
                response.setMessage("Product with id " + id + " not found");
                return response;
            }
            Product deleteProduct = productRepository.findProductById(id);
            productRepository.delete(deleteProduct);
            response.setCode("200");
            response.setMessage("Product deleted successfully");
            response.setData("product", deleteProduct);
        }catch(Exception e) {
            e.printStackTrace();
            response.setResponse(UMSResponse.INTERNAL_SERVER_ERROR);
            response.setMessage("Error deleting product: " + e.getMessage());
        }
        return response;
    }

    public Response getProductById(Long id){
        Response response = new Response();
        try {
            if (!productRepository.existsById(id) || id == null) {
                response.setResponse(UMSResponse.PRODUCT_NOT_FOUND);
                response.setMessage("Product with id " + id + " not found");
                return response;
            }

            Product product = productRepository.findProductById(id);
            ProductDTO setResponse = ProductDTO.setResponse(product);


            response.setCode("200");
            response.setMessage("Product retrieved successfully");
            response.setData("product", setResponse);
        }catch(Exception e) {
            e.printStackTrace();
            response.setResponse(UMSResponse.INTERNAL_SERVER_ERROR);
            response.setMessage("Error retrieving product: " + e.getMessage());
        }
        return response;
    }

    public Response getAllProducts(){
        Response response = new Response();
        try {
           List<Product> allProducts = productRepository.findAll();
            response.setCode("200");
            response.setMessage("Products retrieved successfully");
            response.setData("products", allProducts);
        }catch(Exception e) {
            e.printStackTrace();
            response.setResponse(UMSResponse.INTERNAL_SERVER_ERROR);
            response.setMessage("Error retrieving products: " + e.getMessage());
        }
        return response;
    }

}
