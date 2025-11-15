package com.UserManagement.controller;

import com.UserManagement.dto.ProductDTO;
import com.UserManagement.exceptions.Response;
import com.UserManagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/createProduct")
    public ResponseEntity <?> createProduct(@RequestBody ProductDTO productDTO) {
        Response createProduct = productService.createProduct(productDTO);
        return ResponseEntity.ok(createProduct);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity <?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Response updateProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity <?> deleteProduct(@PathVariable Long id) {
        Response deleteProduct = productService.deleteProduct(id);
        return ResponseEntity.ok(deleteProduct);
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity <?> getProductById(@PathVariable Long id) {
        Response getProductById = productService.getProductById(id);
        return ResponseEntity.ok(getProductById);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity <?> getAllProducts() {
        Response allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }
}
