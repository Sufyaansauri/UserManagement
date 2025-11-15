package com.UserManagement.service.impl;
import com.UserManagement.dto.UserDTO;
import com.UserManagement.entity.OTP;
import com.UserManagement.entity.User;
import com.UserManagement.exceptions.Response;
import com.UserManagement.exceptions.UMSResponse;
import com.UserManagement.repository.OTPRepository;
import com.UserManagement.repository.UserRepository;
import com.UserManagement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private JWTService jwtService;

    private final EmailService emailService;

   private UserServiceImpl(EmailService emailService){
       this.emailService = emailService;
   }

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Override
    public Response createUser(UserDTO userDTO) {
        Response response = new Response();
        User user = new User();

        try {
            if (userDTO == null) {
                response.setResponse(UMSResponse.USER_DATA_NULL);
                return response;
            }


            String email = userDTO.getEmail();

            if (email == null || email.trim().isEmpty()) {
                response.setMessage("Email cannot be empty.");
                return response;
            }
            String emailRegex = "^[A-Za-z0-9._-]+@[A-Za-z]+\\.[A-Za-z]{2,}$";

            if (!email.matches(emailRegex)) {
                response.setResponse(UMSResponse.INVALID_EMAIL_FORMAT);
                return response;
            }

            user.setUsername(userDTO.getUsername());
            user.setEmail(email);
            user.setContact(userDTO.getContact());
            user.setPassword(encoder.encode(userDTO.getPassword()));

            User createdUser = userRepository.save(user);
            UserDTO setResponse = UserDTO.setResponse(createdUser);

            response.setResponse(UMSResponse.USER_CREATED_SUCCESSFULLY);
            response.setData("data", setResponse);

            emailService.sendTemplateEmail(
                    userDTO.getEmail(),
                    "Welcome to Our Platform!",
                    "createUser",
                    Map.of("username", userDTO.getUsername(), "email", userDTO.getEmail())
            );



        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error creating user: " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response updateUser(Long id, UserDTO userDTO) {
        Response response = new Response();

        try {
            if (id == null || userDTO == null || !userRepository.existsById(id)) {
                response.setResponse(UMSResponse.USER_DATA_NULL);
                return response;
            }

            String email = userDTO.getEmail();
            String emailRegex = "^[A-Za-z0-9.+]+@[A-Za-z]+\\.[A-Za-z]{2,}$";

            if (email == null || !email.matches(emailRegex)) {
                response.setResponse(UMSResponse.INVALID_EMAIL_FORMAT);
                return response;
            }

            User user = userRepository.findUserById(id);
            user.setUsername(userDTO.getUsername());
            user.setEmail(email);
            user.setContact(userDTO.getContact());
            user.setPassword(encoder.encode(userDTO.getPassword()));
            User updatedUser = userRepository.save(user);

            UserDTO setResponse = UserDTO.setResponse(updatedUser);
            response.setResponse(UMSResponse.USER_UPDATED_SUCCESSFULLY);
            response.setData("data", setResponse);

            emailService.sendTemplateEmail(
                    userDTO.getEmail(),
                    "Your Account Has Been Updated",
                    "updateUser",
                    Map.of("username", userDTO.getUsername(), "email", userDTO.getEmail())
            );

        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error updating user: " + e.getMessage());
        }

        return response;
    }


    @Override
    public Response deleteUser(Long id) {
        Response response = new Response();
        if(id == null || !userRepository.existsById(id)){
            response.setResponse(UMSResponse.USER_NOT_FOUND);
            return response;
        }

        try{
            userRepository.deleteById(id);
            response.setResponse(UMSResponse.OPERATION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error deleting user: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(Long id) {
        Response response = new Response();
        if(id == null || !userRepository.existsById(id)){
            response.setResponse(UMSResponse.USER_NOT_FOUND);
            return response;
        }
        try{
            User user = userRepository.findUserById(id);
            UserDTO setResponse = UserDTO.setResponse(user);
            response.setResponse(UMSResponse.OPERATION_SUCCESS);
            response.setData("data",setResponse);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error fetching user: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try{
            List<User> allUsers = userRepository.findAll();

            response.setResponse(UMSResponse.OPERATION_SUCCESS);
            response.setData("data",allUsers);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error fetching users: " + e.getMessage());
        }
        return response;
    }


    @Override
    public Response verify(UserDTO userDTO) {
        Response response = new Response();
        try{
            if(userDTO == null){
                response.setResponse(UMSResponse.USER_DATA_NULL);
                return response;
            }
             User user = userRepository.findByUsernameAndPassword(
                     userDTO.getUsername(),
                     userDTO.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDTO.getUsername(),userDTO.getPassword(
            )));
            String token = jwtService.generateToken(userDTO.getUsername());
            response.setResponse(UMSResponse.USER_LOGIN_SUCCESS);
            response.setData("token",token);
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponse(UMSResponse.USER_LOGIN_FAILED);
            response.setMessage("Error during authentication: " + e.getMessage());
        }
        return response;
    }


//    @Override
//    public Response generateOTP(String email) {
//        Response response = new Response();
//        OTP otp = new OTP();
//
//        try{
//            if(email == null || email.trim().isEmpty()){
//                response.setResponse(UMSResponse.EMAIL_NULL);
//                return response;
//            }
//            String emailRegex = "^[A-Za-z0-9._-]+@[A-Za-z]+\\.[A-Za-z]{2,}$";
//
//            if (!email.matches(emailRegex)) {
//                response.setResponse(UMSResponse.INVALID_EMAIL_FORMAT);
//                return response;
//            }
//
//            User user = userRepository.findByEmail(email);
//            if(user == null){
//                response.setResponse(UMSResponse.USER_NOT_FOUND);
//                return response;
//            }
//
//
//            String generatedOTP = String.valueOf((int)(Math.random() * 900000) + 100000);
//
//            otp.setOtp(generatedOTP);
//            otp.setUser(user);
//            otp.setUsed(false);
//            otp.setCreatedAt(System.currentTimeMillis());
//            otp.setExpiresAt(System.currentTimeMillis() + 2 * 60 * 1000);
//
//            otpRepository.save(otp);
//
//
//            emailService.sendTemplateEmail(
//                    email,
//                    "Your One-Time Password (OTP)",
//                    "OTP",
//                    Map.of("username", user.getUsername(), "otp", generatedOTP)
//            );
//
//            response.setResponse(UMSResponse.OTP_SENT_SUCCESSFULLY);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setMessage("Error generating OTP: " + e.getMessage());
//        }
//        return response;
//    }

    @Override
    public Response generateOTP(String email) {
        Response response = new Response();

        try {
            if (email == null || email.trim().isEmpty()) {
                response.setResponse(UMSResponse.EMAIL_NULL);
                return response;
            }

            String emailRegex = "^[A-Za-z0-9._-]+@[A-Za-z]+\\.[A-Za-z]{2,}$";
            if (!email.matches(emailRegex)) {
                response.setResponse(UMSResponse.INVALID_EMAIL_FORMAT);
                return response;
            }

            User user = userRepository.findByEmail(email);
            if (user == null) {
                response.setResponse(UMSResponse.USER_NOT_FOUND);
                return response;
            }

            Long userId = user.getId();
            String generatedOTP = String.valueOf((int) (Math.random() * 900000) + 100000);
            long currentTime = System.currentTimeMillis();

            OTP existingOtp = otpRepository.findByUserId(userId);

            if (existingOtp != null) {
                existingOtp.setOtp(generatedOTP);
                existingOtp.setUsed(false);
                existingOtp.setCreatedAt(Timestamp.from(Instant.now()));
                existingOtp.setExpiresAt(Timestamp.from(Instant.now().plus(2, ChronoUnit.MINUTES)));
                otpRepository.save(existingOtp);
            } else {
                OTP newOtp = new OTP();
                newOtp.setOtp(generatedOTP);
                newOtp.setUser(user);
                newOtp.setUsed(false);
                newOtp.setCreatedAt(Timestamp.from(Instant.now()));
                newOtp.setExpiresAt(Timestamp.from(Instant.now().plus(2, ChronoUnit.MINUTES)));

                otpRepository.save(newOtp);
            }

            emailService.sendTemplateEmail(
                    email,
                    "Your One-Time Password (OTP)",
                    "OTP",
                    Map.of("username", user.getUsername(), "otp", generatedOTP)
            );

            response.setResponse(UMSResponse.OTP_SENT_SUCCESSFULLY);
            response.setMessage("OTP generated/updated successfully and sent to your email.");

        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error generating/updating OTP: " + e.getMessage());
        }

        return response;
    }


    @Override
    public Response validateOTP(String email, String otpInput) {
        Response response = new Response();

        try{
            if(email == null || email.trim().isEmpty()){
                response.setResponse(UMSResponse.EMAIL_NULL);
                return response;
            }
            String emailRegex = "^[A-Za-z0-9._-]+@[A-Za-z]+\\.[A-Za-z]{2,}$";

            if (!email.matches(emailRegex)) {
                response.setResponse(UMSResponse.INVALID_EMAIL_FORMAT);
                return response;
            }

            if(!otpInput .matches("^[0-9]{6}$") || otpInput.length() != 6 ){
                response.setResponse(UMSResponse.OTP_INVALID);
            }

            User user = userRepository.findByEmail(email);
            if(user == null){
                response.setResponse(UMSResponse.USER_NOT_FOUND);
                return response;
            }

            OTP otpRecord = otpRepository.findByOtp(otpInput);


            if(!otpRecord.getOtp().equals(otpInput)){
                response.setResponse(UMSResponse.OTP_INVALID);
                return response;
            }

            if(System.currentTimeMillis() > otpRecord.getExpiresAt().getTime()|| otpRecord.isUsed()){
                response.setResponse(UMSResponse.OTP_EXPIRED);
                return response;
            }

            if(otpRecord.getOtp().equals(otpInput)){
                otpRecord.setUsed(true);
                otpRepository.save(otpRecord);
                response.setResponse(UMSResponse.OPERATION_SUCCESS);
            } else {
                response.setResponse(UMSResponse.USER_NOT_FOUND);

            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error validating OTP: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response forgotPassword(UserDTO userDTO) {
        Response response = new Response();
        try {
            if (userDTO == null) {
                response.setResponse(UMSResponse.USER_DATA_NULL);
                return response;
            }

            User user = userRepository.findByEmail(userDTO.getEmail());
            if (user == null) {
                response.setResponse(UMSResponse.USER_NOT_FOUND);
                return response;
            }

            OTP otp = otpRepository.findByOtp(userDTO.getOtp());
            if (!otp.isUsed()
                    && System.currentTimeMillis() < otp.getExpiresAt().getTime()
                    && Objects.equals(otp.getOtp(), userDTO.getOtp())) {

                if (Objects.equals(userDTO.getPassword(), userDTO.getNewPassword())) {
                    user.setPassword(encoder.encode(userDTO.getPassword()));
                    userRepository.save(user);
                    response.setResponse(UMSResponse.OPERATION_SUCCESS);
                } else {
                    response.setResponse(UMSResponse.PASSWORD_MISMATCH);
                }

            } else{
                response.setResponse(UMSResponse.OTP_INVALID);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error resetting password: " + e.getMessage());
        }
        return response;
    }
}
