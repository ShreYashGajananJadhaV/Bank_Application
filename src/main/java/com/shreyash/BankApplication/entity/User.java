package com.shreyash.BankApplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    private String customerID;
    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contactID")
    private  Contact contact;


}
