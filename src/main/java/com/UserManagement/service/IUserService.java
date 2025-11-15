package com.UserManagement.service;

import com.UserManagement.dto.UserDTO;
import com.UserManagement.exceptions.Response;

public interface IUserService {

    Response createUser(UserDTO userDTO);

    Response updateUser(Long id, UserDTO userDTO);

    Response deleteUser(Long id);

    Response getUserById(Long id);

    Response getAllUsers();

    Response verify(UserDTO userDTO);

    Response generateOTP(String email);

    Response validateOTP(String email, String otp);

    Response forgotPassword(UserDTO userDTO);
}
