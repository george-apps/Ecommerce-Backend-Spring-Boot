package com.appsgeorge.EcommerceBackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cust_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustOrder {

    @Id
    @GeneratedValue
    @Column(name = "id",nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private LocalUser user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id",nullable = false)
    private Address address;

    @OneToMany(mappedBy = "order",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<CustOrderQuantities> quantities = new ArrayList<>();


}
