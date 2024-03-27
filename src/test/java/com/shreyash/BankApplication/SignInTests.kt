package com.shreyash.BankApplication

import com.shreyash.BankApplication.SingletonClasses.EntityObject
import com.shreyash.BankApplication.dto.SignInRequest
import com.shreyash.BankApplication.dto.SignInResponse
import com.shreyash.BankApplication.repository.UserRepository
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

@SpringBootTest
@ExtendWith(MockitoExtension::class)
class SignInTests {

    @MockBean
    private lateinit var userRepository: UserRepository;

    @Autowired
    private lateinit var permitService: PermitService;

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder;
    @Test
    fun signInTestAccountNotExist(){

        val signInRequest = SignInRequest(
            "3436Shreyash2",
            "Shreyash123"
        )


        `when`(userRepository.existsByCustomerID(signInRequest.customerID)).thenReturn(
            false
        )

        val signInResponse = SignInResponse(
            AccountUtils.USER_ABSENT_MESSAGE,
            AccountUtils.USER_NO_DATA_FOUND
        )

        assertThat(ResponseEntity.badRequest().body(signInResponse))
            .usingRecursiveComparison()
            .isEqualTo(permitService.signIn(signInRequest))
    }

    @Test
    fun signInTestSuccess(){
        val user = EntityObject.getUser()
        val signInRequest = SignInRequest(
            "3456Shreyash2",
            "Shreyash123"
        )

        user.password = passwordEncoder.encode(user.password)
        `when`(userRepository.existsByCustomerID(signInRequest.customerID)).thenReturn(
           true
        )

        `when`(userRepository.findByCustomerID(signInRequest.customerID)).thenReturn(
            user
        )

        val signInResponse = SignInResponse(
            AccountUtils.USER_ACTIVE_MESSAGE,
            user.firstName + " " + user.lastName
        )

        assertThat(ResponseEntity.ok().body(signInResponse))
            .usingRecursiveComparison()
            .isEqualTo(permitService.signIn(signInRequest))

    }

    @Test
    fun signInTestFalsePassword(){
        val user = EntityObject.getUser()
        val signInRequest = SignInRequest(
            "3456Shreyash2",
            "Shreyash1234"
        )

        user.password = passwordEncoder.encode(user.password)
        `when`(userRepository.existsByCustomerID(signInRequest.customerID)).thenReturn(
            true
        )

        `when`(userRepository.findByCustomerID(signInRequest.customerID)).thenReturn(
            user
        )

        val signInResponse = SignInResponse(
            AccountUtils.USER_INVALID_MESSAGE,
            AccountUtils.USER_NO_DATA_FOUND
        )

        assertThat(ResponseEntity.badRequest().body(signInResponse))
            .usingRecursiveComparison()
            .isEqualTo(permitService.signIn(signInRequest))

    }
}