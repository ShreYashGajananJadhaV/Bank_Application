package com.shreyash.BankApplication.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailDetails {

    private String recipient;
    private String subject;
    private String emailBody;

}
