package com.UserManagement.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "otp")
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String otp;

    @OneToOne()
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    private boolean isUsed;

    private Timestamp createdAt;

    private Timestamp expiresAt;



}
