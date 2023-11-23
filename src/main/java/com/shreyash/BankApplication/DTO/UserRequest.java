package com.shreyash.BankApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String address;
    private String phoneNumber;
    private String alternatePhoneNumber;

}
