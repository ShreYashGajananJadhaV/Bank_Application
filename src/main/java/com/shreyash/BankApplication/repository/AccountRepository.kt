package com.shreyash.BankApplication.repository

import com.shreyash.BankApplication.authorities.AccountType
import com.shreyash.BankApplication.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account,Long>{

    fun findByAccountNumber(accountNumber: String?): Account?

    fun existsByAccountNumber(accountNumber: String?): Boolean

    fun findByAccountType(accountType: AccountType?): List<Account?>


}