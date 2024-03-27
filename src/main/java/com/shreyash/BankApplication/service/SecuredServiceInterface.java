package com.shreyash.BankApplication.service;

import com.shreyash.BankApplication.dto.*;
import org.springframework.http.ResponseEntity;

public interface SecuredServiceInterface {

    /*to create account with every detail into database*/

    /*to get confidential details about account holded by the user*/
    ResponseEntity<AccountInfo> balanceEnquiry(EnquiryRequest enquiryRequest);


    ResponseEntity<BankResponse> createAccount(UserRequest userRequest);
    ResponseEntity<AccountInfo> creditAccount(CreditDebitRequest creditDebitRequest);

    ResponseEntity<AccountInfo> debitAccount(CreditDebitRequest creditDebitRequest);




}
