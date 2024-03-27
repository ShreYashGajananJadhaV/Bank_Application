package com.shreyash.BankApplication.dto

import com.shreyash.BankApplication.authorities.AccountType
import lombok.AllArgsConstructor
import lombok.Data
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.math.BigDecimal
import java.time.LocalDateTime


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class AccountDetail(

    val holderName: String,
    val accountNumber: String,
    val createdDate: LocalDateTime,
    val balance: BigDecimal,
    val accountType: AccountType


)