package com.UserManagement.dto;

import com.UserManagement.entity.Inventory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Long id;
    private String itemName;
    private int quantity;
    private double price;
    private Long productId;

    public static InventoryDTO setResponse(Inventory inventory) {
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setId(inventory.getId());
        inventoryDTO.setItemName(inventory.getItemName());
        inventoryDTO.setQuantity(inventory.getQuantity());
        inventoryDTO.setPrice(inventory.getPrice());
        inventoryDTO.setProductId(inventory.getProduct().getId());
        return inventoryDTO;
    }
}
