package com.shreyash.BankApplication.SingletonClasses

import com.shreyash.BankApplication.authorities.AccountType
import com.shreyash.BankApplication.entity.Account
import com.shreyash.BankApplication.entity.Contact
import com.shreyash.BankApplication.entity.User
import java.math.BigDecimal
import java.time.LocalDateTime


object EntityObject {



    fun getUser():User{

        val user = User.builder()
            .ID(1)
            .gender("male")
            .address("Swargate,Pune")
            .password("Shreyash123")
            .lastName("Jadhav")
            .firstName("Shreyash")
            .customerID("3456Shreyash2")
            .build()

        return user
    }

    fun getContact(): Contact {

        var contact = Contact.builder()
            .contactID(1)
            .email("shreyash.jadhav@walchandsangli.ac.in")
            .panCard("GDUPA8721P")
            .phoneNumber("9845326548")
            .alternatePhoneNumber("8756347796")
            .build();
        return contact
    }

    fun getAccount(): Account {
        var account = Account.builder()
            .accountID(1)
            .accountNumber("2024221594")
            .accountBalance(BigDecimal.valueOf(3458.67))
            .pinCode("8533")
            .accountType(AccountType.SAVINGS)
            .modifiedAt(LocalDateTime.now())
            .createdAt(LocalDateTime.now().minusDays(2))
            .build()
        return account
    }


}