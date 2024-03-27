package com.shreyash.BankApplication

import com.shreyash.BankApplication.authorities.AccountType
import com.shreyash.BankApplication.SingletonClasses.EntityObject
import com.shreyash.BankApplication.dto.AccountInfo
import com.shreyash.BankApplication.dto.BankResponse
import com.shreyash.BankApplication.dto.UserRequest
import com.shreyash.BankApplication.repository.ContactRepository
import com.shreyash.BankApplication.service.serviceImpl.SecuredService
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


@SpringBootTest
@ExtendWith(MockitoExtension::class)
class CreateAccountTests {

    @MockBean
    private lateinit var contactRepository: ContactRepository;

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder;

    @Autowired
    private lateinit var securedService: SecuredService


    @Test
    fun savingsAccountExists() {

        val user = EntityObject.getUser()

        val contact = EntityObject.getContact()

        val account = EntityObject.getAccount()


        account.pinCode = passwordEncoder.encode(account.pinCode)
        user.password = passwordEncoder.encode(user.password)



        user.contact = contact
        contact.user = user
        contact.account = setOf(account)
        account.contact = contact


        val userRequest = UserRequest(
            "GDUPA8721P",
            "8533",
            AccountType.SAVINGS,
            BigDecimal.valueOf(1000)
        );

        `when`(contactRepository.findByPanCard(userRequest.panCardNumber)).thenReturn(
            contact
        )

        var bankResponse = BankResponse(
            AccountUtils.ACCOUNT_EXISTS_CODE,
            AccountUtils.ACCOUNT_EXISTS_MESSAGE,
            AccountInfo(
                contact.user.customerID,
                user.firstName,
                account.accountBalance,
                account.accountNumber,
                AccountType.SAVINGS
            )
        )
        Assertions.assertThat(ResponseEntity.ok().body(bankResponse))
            .usingRecursiveComparison()
            .isEqualTo(securedService.createAccount(userRequest))


    }



    @Test
    fun currentAccountExists(){

        val user = EntityObject.getUser()

        val contact = EntityObject.getContact()

        val account = EntityObject.getAccount()

        account.accountType = AccountType.CURRENT

        account.pinCode = passwordEncoder.encode(account.pinCode)
        user.password = passwordEncoder.encode(user.password)


        user.contact = contact
        contact.user = user
        contact.account = setOf(account)
        account.contact = contact


        val userRequest = UserRequest(
            "GDUPA8721P",
            "8533",
            AccountType.CURRENT,
            BigDecimal.valueOf(1000)
        );

        `when`(contactRepository.findByPanCard(userRequest.panCardNumber)).thenReturn(
            contact
        )



        Assertions.assertThat(ResponseEntity.ok(BankResponse(
                        AccountUtils.ACCOUNT_EXISTS_CODE,
                        AccountUtils.ACCOUNT_EXISTS_MESSAGE,
                        AccountInfo(
                                contact.getUser().getCustomerID(),
                                user.getFirstName(),
                                account.getAccountBalance(),
                                account.getAccountNumber(),
                                AccountType.CURRENT
                        ))))
            .usingRecursiveComparison()
            .isEqualTo(securedService.createAccount(userRequest))


    }

}