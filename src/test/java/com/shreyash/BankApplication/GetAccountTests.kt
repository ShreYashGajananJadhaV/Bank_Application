package com.shreyash.BankApplication

import com.shreyash.BankApplication.SingletonClasses.EntityObject
import com.shreyash.BankApplication.dto.AccountDetail
import com.shreyash.BankApplication.repository.UserRepository
import com.shreyash.BankApplication.service.serviceImpl.SecuredService
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

@SpringBootTest
@ExtendWith(MockitoExtension::class)
class GetAccountTests {

    @MockBean
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var securedService: SecuredService


    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder;

    @Test
    fun getAccountsTest() {
        val user = EntityObject.getUser()

        val contact = EntityObject.getContact()

        val account = EntityObject.getAccount()


        account.pinCode = passwordEncoder.encode(account.pinCode)
        user.password = passwordEncoder.encode(user.password)




        user.contact = contact
        contact.user = user
        contact.account = setOf(account)
        account.contact = contact

        `when`(userRepository.findByCustomerID("3456Shreyash2")).thenReturn(
            user
        )

        val accountDetail = AccountDetail(
            user.firstName + " " + user.lastName,
            account.accountNumber,
            account.createdAt,
            account.accountBalance,
            account.accountType
        )

        assertThat(ResponseEntity.ok().body(listOf(accountDetail))).usingRecursiveComparison()
            .isEqualTo(securedService.getAccounts("3456Shreyash2"))
    }




}