package com.shreyash.BankApplication

import com.shreyash.BankApplication.authorities.AccountType
import com.shreyash.BankApplication.SingletonClasses.EntityObject
import com.shreyash.BankApplication.dto.AccountInfo
import com.shreyash.BankApplication.dto.EnquiryRequest
import com.shreyash.BankApplication.repository.AccountRepository
import com.shreyash.BankApplication.utils.AccountUtils
import org.assertj.core.api.Assertions.assertThat
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

@SpringBootTest
@ExtendWith(MockitoExtension::class)
class BalanceEnquiryTests {

    @MockBean
    private lateinit var accountRepository: AccountRepository;

    @Autowired
    private lateinit var securedService: SecuredService;

    @Autowired
    private lateinit var  passwordEncoder: PasswordEncoder;

    @Test
    fun balanceTestAccountNotExist() {

        val enquiryRequest = EnquiryRequest(
            "2024567348",
            "8533"
        );

        `when`(accountRepository.existsByAccountNumber(enquiryRequest.accountNumber)).thenReturn(
            false
        )
        val accountInfo =
            AccountInfo(
                AccountUtils.USER_NO_DATA_FOUND,
                AccountUtils.USER_NO_DATA_FOUND,
                BigDecimal.ZERO,
                enquiryRequest.accountNumber,
                AccountType.NOT_MENTIONED
            )
        assertThat(ResponseEntity.ok(accountInfo))
            .usingRecursiveComparison()
            .isEqualTo(securedService.balanceEnquiry(enquiryRequest))

    }

    @Test
    fun balanceTestFalsePinCode() {

        val enquiryRequest = EnquiryRequest(
            "2024567348",
            "4233"
        );
        `when`(accountRepository.existsByAccountNumber(enquiryRequest.accountNumber)).thenReturn(
            true
        )
        val account = EntityObject.getAccount()


        `when`(accountRepository.findByAccountNumber(enquiryRequest.accountNumber)).thenReturn(
            account
        )

        val accountInfo = AccountInfo(
            AccountUtils.USER_INVALID_MESSAGE,
            AccountUtils.USER_INVALID_MESSAGE,
            BigDecimal.ZERO,
            AccountUtils.USER_INVALID_MESSAGE,
            AccountType.NOT_MENTIONED
        )
        assertThat(ResponseEntity.ok().body(accountInfo))
            .usingRecursiveComparison()
            .isEqualTo(securedService.balanceEnquiry(enquiryRequest))


    }

    @Test
    fun balanceTestSuccess() {
        val user = EntityObject.getUser()
        val contact = EntityObject.getContact()
        val account = EntityObject.getAccount()

        account.pinCode = passwordEncoder.encode(account.pinCode)
        account.contact = contact
        contact.account = setOf(account)
        user.contact = contact
        contact.user = user

        val enquiryRequest = EnquiryRequest(
            "2024221594",
            "8533"
        );

        `when`(accountRepository.existsByAccountNumber(enquiryRequest.accountNumber)).thenReturn(
            true
        )


        `when`(accountRepository.findByAccountNumber(enquiryRequest.accountNumber)).thenReturn(
            account
        )

        val accountInfo = AccountInfo(
            user.getCustomerID(),
            user.getFirstName() + " " + user.getLastName(),
            account.accountBalance,
            account.accountNumber,
            account.accountType
        )

        assertThat(ResponseEntity.ok().body(accountInfo))
            .usingRecursiveComparison()
            .isEqualTo(securedService.balanceEnquiry(enquiryRequest))

    }
}