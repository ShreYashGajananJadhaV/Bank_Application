package com.shreyash.BankApplication.repository;

import com.shreyash.BankApplication.authorities.AccountType;
import com.shreyash.BankApplication.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {


    public Account findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);

    List<Account> findByAccountType(AccountType accountType);


}
