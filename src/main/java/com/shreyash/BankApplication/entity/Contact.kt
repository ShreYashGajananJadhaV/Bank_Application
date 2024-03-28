package com.shreyash.BankApplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "contacts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
data class Contact (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val contactID:Long? = null,
    val phoneNumber:String,
    val alternatePhoneNumber:String,
    val email:String,
    val panCard:String,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "fk_contactID", referencedColumnName = "contactID")
    var account: Set<Account>? = null,

    @OneToOne(mappedBy = "contact")
    var user:User? = null

    )
