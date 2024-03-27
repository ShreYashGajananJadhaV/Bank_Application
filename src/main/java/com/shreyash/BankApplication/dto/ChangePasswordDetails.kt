package com.shreyash.BankApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
data class ChangePasswordDetails (

    val newPassword: String,
    val customerID:String

)
