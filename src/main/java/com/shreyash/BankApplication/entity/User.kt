package com.shreyash.BankApplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
class User (

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val ID:Int?=null,
    val customerID:String,
    val firstName:String,
    val lastName:String,
    val gender:String,
    val address:String,
    var password:String,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "contactID")
    var contact:Contact? = null


)
