package com.shreyash.BankApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
 data class SignInRequest (

    val customerID: String,
    val password: String

)
