package com.UserManagement.service;

import com.UserManagement.dto.InventoryDTO;
import com.UserManagement.entity.Inventory;
import com.UserManagement.exceptions.Response;
import com.UserManagement.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Response createInventory(InventoryDTO inventoryDTO){
        Response response = new Response();
        try{
            if (inventoryDTO == null){
                response.setCode("400");
                response.setMessage("Inventory object is null");
                return response;
            }
            if(inventoryDTO.getItemName() == null || inventoryDTO.getItemName().trim().isEmpty()){
                response.setCode("400");
                response.setMessage("Item name cannot be empty");
                return response;
            }
            if(inventoryDTO.getQuantity() == null || inventoryDTO.getQuantity() <= 0){
                response.setCode("400");
                response.setMessage("Quantity cannot be empty");
                return response;
            }
            if(inventoryDTO.getLocation() == null || inventoryDTO.getItemName().trim().isEmpty()){
                response.setCode("400");
                response.setMessage("Location cannot be empty");
                return response;
            }
            if(inventoryDTO.getProductId() == null || inventoryDTO.getProductId() == 0){
                response.setCode("400");
                response.setMessage("Product Id cannot be empty");
                return response;
            }

            Inventory inventory = new Inventory();
            inventory.setItemName(inventoryDTO.getItemName());


        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error Creating Inventory"+ e.getMessage());
        }
        return response;
    }


}
