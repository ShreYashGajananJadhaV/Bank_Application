package com.shreyash.BankApplication.dto

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor


@Data
@NoArgsConstructor
@AllArgsConstructor
class CreateProfileRequest (

    val firstName: String,
    val lastName: String,
    val gender: String,
    val address: String,
    val password: String,
    val contact: ContactDetails

)