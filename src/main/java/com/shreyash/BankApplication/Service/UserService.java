package com.shreyash.BankApplication.Service;

import com.shreyash.BankApplication.DTO.*;
import com.shreyash.BankApplication.Entity.User;
import com.shreyash.BankApplication.Repository.UserRepository;
import com.shreyash.BankApplication.Utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.math.BigDecimal;

@Service
public class UserService  implements UserServiceInterface{


    @Autowired
    UserRepository repository ;

    @Autowired
    EmailService emailService;

    @Override
    public BankResponse createAccount(UserRequest user) throws Exception {

        if (repository.existsByEmail(user.getEmail())){
            return new BankResponse().builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User newuser = User.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .address(user.getAddress())
                .alternatePhoneNumber(user.getAlternatePhoneNumber())
                .accountNumber(AccountUtils.generateAccountNumber())
                .phoneNumber(user.getPhoneNumber())
                .accountBalance(BigDecimal.ZERO)
                .build();


        repository.save(newuser);

        EmailDetails emaildetails = EmailDetails.builder().recipient(newuser.getEmail()).subject("ACCOUNT CREATION").emailBody("CONGRATULTIONS your account has been successfully created and saved").build();
        emailService.sendEmail(emaildetails);
        return BankResponse.builder()
                .accountInfo(AccountInfo.builder().accountNumber(newuser.getAccountNumber()).accountHolderName(newuser.getFirstName()+" "+newuser.getLastName()).accountBalance(BigDecimal.ZERO).build())
                .responseCode(AccountUtils.ACCOUNT_CREATION_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .build();

    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        boolean accountExists= repository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!accountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NO_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NO_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User user = repository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountNumber(user.getAccountNumber())
                        .accountBalance(user.getAccountBalance())
                        .accountHolderName(user.getFirstName()+" "+user.getLastName())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        boolean accountExists = repository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!accountExists){
            return AccountUtils.ACCOUNT_NO_EXISTS_MESSAGE;
        }
        User user = repository.findByAccountNumber(enquiryRequest.getAccountNumber());

        return user.getFirstName()+" " +user.getLastName();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest creditDebitRequest) {
        boolean accountExists = repository.existsByAccountNumber(creditDebitRequest.getAccountNumber());

        if (!accountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NO_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NO_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }


        User user = repository.findByAccountNumber(creditDebitRequest.getAccountNumber());
        user.setAccountBalance(user.getAccountBalance().add(creditDebitRequest.getAmount()));
        repository.save(user);

        return BankResponse.builder()
                .responseCode(AccountUtils.CREDIT_TRANSACTION_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.CREDIT_TRANSACTION_SUCCESSFUL_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(user.getAccountBalance())
                        .accountNumber(user.getAccountNumber())
                        .accountHolderName(user.getFirstName()+" "+user.getLastName())
                        .build())
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest creditDebitRequest) {
        boolean accountExists = repository.existsByAccountNumber(creditDebitRequest.getAccountNumber());
/* check if the account exists*/
        if (!accountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NO_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NO_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }


        User user = repository.findByAccountNumber(creditDebitRequest.getAccountNumber());
        BigDecimal currentBalance = user.getAccountBalance().add(BigDecimal.valueOf(1000));
        BigDecimal withdrawalAmount = creditDebitRequest.getAmount();

        /*check if the withdrawal amount is less than amount in the account+1000(safety measure)*/
        if (currentBalance.compareTo(withdrawalAmount) <= 0){
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_ACCOUNT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_ACCOUNT_BALANCE_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountBalance(user.getAccountBalance())
                            .accountNumber(user.getAccountNumber())
                            .accountHolderName(user.getFirstName()+" "+user.getLastName())
                            .build())
                    .build();
        }

        /*amount successful withdrawal and confirmation*/
        user.setAccountBalance(user.getAccountBalance().subtract(creditDebitRequest.getAmount()));
        repository.save(user);

        return BankResponse.builder()
                .responseCode(AccountUtils.DEBIT_TRANSATION_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.DEBIT_TRANSACTION_SUCCESSFUL_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(user.getAccountBalance())
                        .accountNumber(user.getAccountNumber())
                        .accountHolderName(user.getFirstName()+" "+user.getLastName())
                        .build())
                .build();
    }


}
