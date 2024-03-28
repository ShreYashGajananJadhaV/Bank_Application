package com.shreyash.BankApplication.entity;

import com.shreyash.BankApplication.authorities.AccountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "accounts")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Account (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val accountID:Long? = null,
    val accountNumber:String,
    var accountBalance:BigDecimal,
    @CreationTimestamp
    val createdAt:LocalDateTime? =  null,
    @UpdateTimestamp
    val modifiedAt:LocalDateTime? = null,
    var pinCode:String,
    @Enumerated(EnumType.STRING)
    val accountType:AccountType,

    @ManyToOne
    @JoinColumn(name = "fk_contactID")
    var contact:Contact?=null

    )

