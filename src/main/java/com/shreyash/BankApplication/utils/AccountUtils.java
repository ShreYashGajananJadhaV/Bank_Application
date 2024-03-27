package com.shreyash.BankApplication.utils;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.Year;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "101";
    public static final String ACCOUNT_EXISTS_MESSAGE = "Account with the provided number already exists";

    public static final String ACCOUNT_CREATION_CODE = "202";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account successfully created with provided creadentials";

    public static final String PROFILE_CREATION_MESSAGE = "USER PROFILE CREATION SUCCESS";

    public static final String PROFILE_EXISTS_MESSAGE = "USER PROFILE ALREADY EXISTS";

    public static final String USER_ABSENT_MESSAGE = "USER CANNOT BE FOUND PLEASE VERIFY THE CREDENTIALS BEFORE ENTERING";

    public static final String USER_ACTIVE_MESSAGE = "USER SUCCESSFULLY LOGGED IN";

    public static final String USER_INVALID_MESSAGE = "USER WRONG [Username/Password] ENTERED";

    public static final String USER_NO_DATA_FOUND = "---NO--DATA---";

    public static final String LESS_AMOUNT_TO_DEBIT = "Bank Balance is less to debit the required money";

    private static final  AtomicInteger atomicInteger = new AtomicInteger(0);


    private static int min = 100000;
    private static int max = 900000;

    public static String generateAccountNumber(){
        Year currYear = Year.now();

        int randomNumber = new Random().nextInt(max)+min;


        return String.valueOf(currYear)+String.valueOf(randomNumber);
    }
//
//    public static String generateRandomAuthCode(){
//        int x = ((int)(Math.random() * 100000)) % 10;
//        return String.valueOf(x);
//    }


    public static String generateCustomerId(String username, String phoneNumber){
        return phoneNumber.substring(6)+username+atomicInteger.incrementAndGet();
    }


}
