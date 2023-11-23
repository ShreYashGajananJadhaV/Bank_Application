package com.shreyash.BankApplication.Service;

import com.shreyash.BankApplication.DTO.BankResponse;
import com.shreyash.BankApplication.DTO.CreditDebitRequest;
import com.shreyash.BankApplication.DTO.EnquiryRequest;
import com.shreyash.BankApplication.DTO.UserRequest;

import javax.swing.*;

public interface UserServiceInterface {

    /*to create account with every detail into database*/
    BankResponse createAccount(UserRequest user) throws Exception;

    /*to get confidential details about account holded by the user*/
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);

    /*to get only details which are not very confidential of account holded by the user*/
    String nameEnquiry(EnquiryRequest enquiryRequest);


    BankResponse creditAccount(CreditDebitRequest creditDebitRequest);

    BankResponse debitAccount(CreditDebitRequest creditDebitRequest);





}
