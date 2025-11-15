package com.UserManagement.dto;

import com.UserManagement.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String contact;
    private String password;
    private String otp;
    private String newPassword;


    public static UserDTO setResponse(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setContact(user.getContact());
        return userDTO;
    }
}