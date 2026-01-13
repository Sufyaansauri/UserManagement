package com.UserManagement.controller;

import com.UserManagement.dto.InventoryDTO;
import com.UserManagement.exceptions.Response;
import com.UserManagement.service.InventoryService;
import com.UserManagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/createInventory")
    public ResponseEntity <?> createInventory(@RequestBody InventoryDTO inventoryDTO) {
        Response createInventory = inventoryService.createProduct(inventoryDTO);
        return ResponseEntity.ok(inventoryService);
    }
}
