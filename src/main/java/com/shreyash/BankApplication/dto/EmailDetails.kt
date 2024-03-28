package com.shreyash.BankApplication.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class EmailDetails (

    val recipient:String,
    val subject:String,
    val emailBody:String
)
