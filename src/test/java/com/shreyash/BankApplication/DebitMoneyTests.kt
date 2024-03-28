package com.shreyash.BankApplication

import com.shreyash.BankApplication.service.serviceImpl.SecuredService
import com.shreyash.BankApplication.authorities.AccountType
import com.shreyash.BankApplication.SingletonClasses.EntityObject
import com.shreyash.BankApplication.dto.AccountInfo
import com.shreyash.BankApplication.dto.CreditDebitRequest
import com.shreyash.BankApplication.entity.Account
import com.shreyash.BankApplication.repository.AccountRepository
import com.shreyash.BankApplication.utils.AccountUtils
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import java.math.BigDecimal
import java.time.LocalDateTime

@SpringBootTest
@ExtendWith(MockitoExtension::class)
class DebitMoneyTests {


    @Autowired
    private lateinit var securedService: SecuredService

    @MockBean
    private lateinit var accountRepository: AccountRepository


    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder;


    @Test
    fun debitTestFalseAccount() {
        val creditDebitRequest = CreditDebitRequest(
            "2024221591",
            BigDecimal.valueOf(300),
            "4132"
        )

        `when`(accountRepository.existsByAccountNumber(creditDebitRequest.accountNumber)).thenReturn(
            false
        )

        val accountInfo = AccountInfo(
            AccountUtils.USER_ABSENT_MESSAGE,
            AccountUtils.USER_ABSENT_MESSAGE,
            BigDecimal.ZERO,
            AccountUtils.USER_ABSENT_MESSAGE,
            AccountType.NOT_MENTIONED
        )

        Assertions.assertThat(ResponseEntity.ok().body(accountInfo)).usingRecursiveComparison()
            .isEqualTo(securedService.creditAccount(creditDebitRequest))
    }

    @Test
    fun debitTestFalsePinCode() {

        val user = EntityObject.getUser()

        val contact = EntityObject.getContact()

        val account = EntityObject.getSavingsAccount()

        account.pinCode = passwordEncoder.encode(account.pinCode)
        user.password = passwordEncoder.encode(user.password)

        user.contact = contact
        contact.user = user
        contact.account = setOf(account)
        account.contact = contact


        val creditDebitRequest = CreditDebitRequest(
            "2024221594",
            BigDecimal.valueOf(3469.87),
            "4234"
        )


        `when`(accountRepository.existsByAccountNumber(creditDebitRequest.accountNumber)).thenReturn(
            true
        )

        `when`(accountRepository.findByAccountNumber(creditDebitRequest.accountNumber)).thenReturn(
            account
        )

        val accountInfo = AccountInfo(
            AccountUtils.USER_INVALID_MESSAGE,
            AccountUtils.USER_INVALID_MESSAGE,
            BigDecimal.ZERO,
            AccountUtils.USER_INVALID_MESSAGE,
            AccountType.NOT_MENTIONED
        )


        Assertions.assertThat(ResponseEntity.badRequest().body(accountInfo))
            .usingRecursiveComparison()
            .isEqualTo(securedService.debitAccount(creditDebitRequest))
    }

    @Test
    fun debitTestLessMoney() {


        val user = EntityObject.getUser()

        val contact = EntityObject.getContact()

        val account = EntityObject.getCurrentAccount()


        account.pinCode = passwordEncoder.encode(account.pinCode)
        user.password = passwordEncoder.encode(user.password)

        user.contact = contact
        contact.user = user
        contact.account = setOf(account)
        account.contact = contact



        val creditDebitRequest = CreditDebitRequest(
            "2024221594",
            BigDecimal.valueOf(6469.87),
            "8533"
        )


        `when`(accountRepository.existsByAccountNumber(creditDebitRequest.accountNumber)).thenReturn(
            true
        )

        `when`(accountRepository.findByAccountNumber(creditDebitRequest.accountNumber)).thenReturn(
            account
        )

        val accountInfo = AccountInfo(
            user.customerID,
            AccountUtils.LESS_AMOUNT_TO_DEBIT,
            account.accountBalance,
            account.accountNumber,
            account.accountType
        )

        Assertions.assertThat(ResponseEntity.ok(accountInfo))
            .usingRecursiveComparison()
            .isEqualTo(securedService.debitAccount(creditDebitRequest))


    }


    @Test
    fun debitTestSuccess() {


        val user = EntityObject.getUser()

        val contact = EntityObject.getContact()

        val account = EntityObject.getCurrentAccount()


        account.pinCode = passwordEncoder.encode(account.pinCode)
        user.password = passwordEncoder.encode(user.password)



        user.contact = contact
        contact.user = user
        contact.account = setOf(account)
        account.contact = contact

//        val acc = Account.builder().accountID(12).build()

        val creditDebitRequest = CreditDebitRequest(
            "2024221594",
            BigDecimal.valueOf(298.87),
            "8533"
        )

        `when`(accountRepository.existsByAccountNumber(creditDebitRequest.accountNumber)).thenReturn(
            true
        )

        `when`(accountRepository.findByAccountNumber(creditDebitRequest.accountNumber)).thenReturn(
            account
        )

        val debitBalance = account.accountBalance.subtract(creditDebitRequest.amount);
        account.accountBalance = debitBalance

        val accountInfo = AccountInfo(
            user.customerID,
            user.firstName + " " + user.lastName,
            account.accountBalance,
            account.accountNumber,
            account.accountType
        )

        val updatedAccount = Account(
            accountID = 1,
            accountNumber = account.accountNumber,
            accountBalance = debitBalance,
            pinCode = passwordEncoder.encode("8533"),
            accountType = account.accountType,
            createdAt = account.createdAt,
            modifiedAt = LocalDateTime.now()

        )


        `when`(accountRepository.save(account)).thenReturn(updatedAccount)

        val actual = securedService.debitAccount(creditDebitRequest)

        Assertions.assertThat(ResponseEntity.ok().body(accountInfo))
            .usingRecursiveComparison()
            .isEqualTo(actual)

//
//        val fruits = listOf("Apple","Orange","Banana")
//        val filteredFruits = fruits.filter{fruit -> fruit.length}
//        println(filteredFruits)
    }
}