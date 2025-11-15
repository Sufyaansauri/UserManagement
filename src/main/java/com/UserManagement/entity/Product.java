package com.UserManagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;

    @OneToOne()
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category category;

}
