package com.appsgeorge.EcommerceBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "address",nullable = false,length = 220)
    private String address;

    @Column(name = "city",nullable = false,length = 60)
    private String city;

    @Column(name = "country",nullable = false,length = 60)
    private String country;

    @Column(name = "postCode",nullable = false,length = 20)
    private String postCode;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private LocalUser user;






}
