package com.shreyash.BankApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
data class BankResponse (

    val responseCode:String,
    val responseMessage:String,
    val accountInfo: AccountInfo,
){


}

