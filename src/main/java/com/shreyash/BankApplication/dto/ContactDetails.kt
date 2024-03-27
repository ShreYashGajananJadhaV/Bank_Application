package com.shreyash.BankApplication.dto;


import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
data class ContactDetails (

    val phoneNumber: String,
    val alternatePhoneNumber:String,
    val email:String,
    val panCard:String,

)
