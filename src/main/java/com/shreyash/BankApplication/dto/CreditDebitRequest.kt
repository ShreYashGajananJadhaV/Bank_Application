package com.shreyash.BankApplication.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
data class CreditDebitRequest (
    val accountNumber:String,
    val amount:BigDecimal,
    val pinCode:String
)
