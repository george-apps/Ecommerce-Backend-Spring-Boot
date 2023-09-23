package com.appsgeorge.EcommerceBackend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "cust_order_quantities")
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

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id",nullable = false)
    private CustOrder order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CustOrder getOrder() {
        return order;
    }

    public void setOrder(CustOrder order) {
        this.order = order;
    }
}
