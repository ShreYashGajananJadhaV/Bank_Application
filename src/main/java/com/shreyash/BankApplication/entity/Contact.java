package com.shreyash.BankApplication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contactID;
    private String phoneNumber;
    private String alternatePhoneNumber;
    private String email;
    private String panCard;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_contactID", referencedColumnName = "contactID")
    private Set<Account> account;

    @OneToOne(mappedBy = "contact")
    private User user;

}
