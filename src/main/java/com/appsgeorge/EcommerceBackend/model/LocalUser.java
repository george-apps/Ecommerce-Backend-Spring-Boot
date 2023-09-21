package com.appsgeorge.EcommerceBackend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "local_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalUser {
    @Id
    @GeneratedValue
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name ="first_name",nullable = false)
    private String firstName;

    @Column(name ="last_name",nullable = false)
    private String lastName;

    @Column(name ="username",nullable = false,unique = true)
    private String username;

    @Column(name ="email",nullable = false,unique = true,length = 250)
    private String email;

    @Column(name ="password",nullable = false,length = 500)
    private String password;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();



}
