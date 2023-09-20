package com.appsgeorge.EcommerceBackend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cust_order_quantities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustOrderQuantities {

    @Id
    @GeneratedValue
    @Column(name = "id",nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Column(name = "quantity",nullable = false)
    private int quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id",nullable = false)
    private CustOrder order;

}
