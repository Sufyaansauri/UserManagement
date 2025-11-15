package com.UserManagement.controller;
import com.UserManagement.dto.UserDTO;
import com.UserManagement.exceptions.Response;
import com.UserManagement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")

public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody UserDTO userDTO){
        Response verify = userService.verify(userDTO);
        return ResponseEntity.ok(verify);
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        Response createUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createUser);
    }

    @PutMapping("/updateUser/{id}")
   public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Response updateUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Response deleteUser = userService.deleteUser(id);
        return ResponseEntity.ok(deleteUser);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Response getUserById = userService.getUserById(id);
        return ResponseEntity.ok(getUserById);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        Response allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PostMapping("/generateOTP")
    public ResponseEntity<?> generateOTP(@RequestParam String email) {
        Response otpResponse = userService.generateOTP(email);
        return ResponseEntity.ok(otpResponse);
    }

    @PostMapping("/validateOTP")
    public ResponseEntity<?> validateOTP(@RequestParam String email, @RequestParam String otp) {
        Response validateOtpResponse = userService.validateOTP(email, otp);
        return ResponseEntity.ok(validateOtpResponse);
    }


    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody UserDTO userDTO){
        Response forgotPasswordResponse = userService.forgotPassword(userDTO);
        return ResponseEntity.ok(forgotPasswordResponse);
    }
}
