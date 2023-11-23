package com.shreyash.BankApplication.Utils;

import com.shreyash.BankApplication.DTO.BankResponse;
import com.shreyash.BankApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Year;
import java.util.Random;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "101";
    public static final String ACCOUNT_EXISTS_MESSAGE = "Account with the provided number already exists";

    public static final String ACCOUNT_CREATION_CODE = "202";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account successfully created with provided creadentials";


    public static final String ACCOUNT_NO_EXISTS_MESSAGE = "No such Account exists";
    public static final String ACCOUNT_NO_EXISTS_CODE = "-1";


    public static final String INSUFFICIENT_ACCOUNT_BALANCE_CODE ="-1";

    public static final String INSUFFICIENT_ACCOUNT_BALANCE_MESSAGE = "withdrawal amount is too high checking the current balance of your account";

    public static final String DEBIT_TRANSATION_SUCCESSFUL_CODE = "303";
    public static final String DEBIT_TRANSACTION_SUCCESSFUL_MESSAGE = "Debited amount successfully";

    public static final String CREDIT_TRANSACTION_SUCCESSFUL_CODE = "404";
    public static final String CREDIT_TRANSACTION_SUCCESSFUL_MESSAGE = "Credited amount successfully";


    private static int min = 100000;
    private static int max = 900000;

    public static String generateAccountNumber(){
        Year currYear = Year.now();

        int randomNumber = new Random().nextInt(max)+min;


        return String.valueOf(currYear)+String.valueOf(randomNumber);
    }


}
