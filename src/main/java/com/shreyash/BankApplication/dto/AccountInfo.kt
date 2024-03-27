package com.shreyash.BankApplication.dto;

import com.shreyash.BankApplication.authorities.AccountType
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor
import lombok.Setter

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
data class AccountInfo(
    val customerID: String,
    val accountHolderName: String,
    val accountBalance: BigDecimal,
    val accountNumber: String,
    val accountType: AccountType
)
