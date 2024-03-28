package com.shreyash.BankApplication.service.serviceImpl

import com.shreyash.BankApplication.dto.*
import com.shreyash.BankApplication.entity.Contact
import com.shreyash.BankApplication.entity.User
import com.shreyash.BankApplication.repository.ContactRepository
import com.shreyash.BankApplication.repository.UserRepository
import com.shreyash.BankApplication.service.PermitServiceInterface
import com.shreyash.BankApplication.utils.AccountUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class PermitSerivce :PermitServiceInterface{

    @Autowired
    lateinit var contactRepository: ContactRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var emailService: EmailService


    override fun createProfile(createProfileRequest: CreateProfileRequest): ResponseEntity<CreateProfileResponse> {

        val contactDetails = createProfileRequest.contact
        if (contactRepository.existsByPanCard(contactDetails.panCard) == true) {
            val user = contactRepository.findByPanCard(contactDetails.panCard)!!.user
            return ResponseEntity.ok(
                CreateProfileResponse(
                    user!!.customerID,
                    user.firstName + " " + user.lastName,
                    AccountUtils.PROFILE_EXISTS_MESSAGE
                )
            )
        }


        val user = User(
            firstName = createProfileRequest.firstName,
            lastName = createProfileRequest.lastName,
            gender = createProfileRequest.gender,
            address = createProfileRequest.address,
            password = passwordEncoder.encode(createProfileRequest.password),
            customerID = AccountUtils.generateCustomerId(
                createProfileRequest.firstName,
                createProfileRequest.contact.phoneNumber
            )

        )


        val contact = Contact(
            phoneNumber = contactDetails.phoneNumber,
            alternatePhoneNumber = contactDetails.alternatePhoneNumber,
            email = contactDetails.email,
            panCard = contactDetails.panCard,
        )


        user.contact = contact
        contact.user = user

        userRepository.save(user)
        contactRepository.save(contact)


        val emailDetails = EmailDetails(
            contactDetails.email,
            "PROFILE CREATE SUCCESS",
            """User profile created successfully with following details:

            Username: ${user.firstName} ${user.lastName}

            CustomerID: ${user.customerID}

            Address: ${user.address}

            Phone-Number: ${contactDetails.phoneNumber}

"""
        )

        emailService.sendEmail(emailDetails)

        return ResponseEntity.ok(
            CreateProfileResponse(
                customerID = user.customerID,
                username = user.firstName+" "+user.lastName,
                status = AccountUtils.PROFILE_CREATION_MESSAGE
            )
        )

    }


    override fun signIn(signInRequest: SignInRequest):ResponseEntity<SignInResponse>{

        val accountExists = userRepository.existsByCustomerID(signInRequest.customerID)
        if (accountExists == false) {
            return ResponseEntity.badRequest().body(
                SignInResponse(
                    status = AccountUtils.USER_ABSENT_MESSAGE,
                    username = AccountUtils.USER_NO_DATA_FOUND
                )
            )
        }

        val user = userRepository.findByCustomerID(signInRequest.customerID)

        val actualPassword = user!!.password
        val sentPassword = signInRequest.password

        return if (passwordEncoder.matches(sentPassword, actualPassword)) {
            //generate token and send it to user
            ResponseEntity.ok(
                SignInResponse(
                    AccountUtils.USER_ACTIVE_MESSAGE,
                    user!!.firstName + " " + user.lastName
                )
            )
        } else ResponseEntity.badRequest().body(
            SignInResponse(
                status = AccountUtils.USER_INVALID_MESSAGE,
                username = AccountUtils.USER_NO_DATA_FOUND
            )
        )

    }

}