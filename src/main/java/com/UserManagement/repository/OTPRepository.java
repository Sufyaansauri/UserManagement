package com.UserManagement.repository;
import com.UserManagement.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface OTPRepository extends JpaRepository<OTP,Long> {

    OTP findByOtp(String otp);

    OTP findByUserId(Long userId);
}
