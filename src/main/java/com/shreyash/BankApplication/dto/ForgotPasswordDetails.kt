package com.shreyash.BankApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
data class ForgotPasswordDetails (

    val panCardNumber:String,
    val phoneNumber: String
)
