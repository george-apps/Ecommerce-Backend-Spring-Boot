package com.appsgeorge.EcommerceBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name ="name",nullable = false,unique = true)
    private String name;

    @Column(name ="price",nullable = false)
    private double price;

    @Column(name ="short_desc",nullable = false)
    private String Short_desc;

    @Column(name ="long_desc")
    private String long_desc;

    @OneToOne(mappedBy = "product",cascade = CascadeType.REMOVE,optional = false,orphanRemoval = true)
    private Inventory inventory;

}
