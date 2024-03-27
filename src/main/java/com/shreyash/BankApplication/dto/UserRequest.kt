package com.shreyash.BankApplication.dto;

import com.shreyash.BankApplication.authorities.AccountType
import lombok.*;
import java.math.BigDecimal

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class UserRequest (
    @NonNull
    val panCardNumber:String,
    @NonNull
    val pinCode:String,
    @NonNull
    val accountType: AccountType,

    val creditMoney: BigDecimal

)
