package com.shreyash.BankApplication.service.serviceImpl

import com.shreyash.BankApplication.authorities.AccountType
import com.shreyash.BankApplication.entity.Account
import com.shreyash.BankApplication.repository.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class SchedulerService {

    @Autowired
    lateinit var accountRepository:AccountRepository;

    @Scheduled(fixedDelay = 10000)
    fun updateData(){

        val accounts:List<Account> = accountRepository.findByAccountType(AccountType.SAVINGS);

        for (account in accounts) {
            var currentMoney = account.accountBalance
            val additionalMoney = currentMoney.divide(BigDecimal.valueOf(100))
            currentMoney = currentMoney.add(additionalMoney)
            account.accountBalance = currentMoney
            accountRepository.save(account)
        }

    }

}