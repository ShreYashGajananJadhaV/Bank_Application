package com.shreyash.BankApplication.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.annotation.Nullable


@Data
@NoArgsConstructor
@AllArgsConstructor
class CreateProfileResponse(
    val customerID: String,
    val username: String,
    val status: String
)