package com.appsgeorge.EcommerceBackend.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.w3c.dom.Text;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "verification_token")
public class VerificationToken {

    @Id
    @GeneratedValue
    @Column(name = "id",nullable = false)
    private Long id;


    @Column(name = "token",nullable = false,unique = true,columnDefinition = "TEXT")
    private String token;

    @Column(name = "createdAt_timestamp",nullable = false)
    private Timestamp createdAtTimestamp;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    private LocalUser user;

}
