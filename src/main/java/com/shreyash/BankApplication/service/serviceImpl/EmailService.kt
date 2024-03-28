package com.shreyash.BankApplication.service.serviceImpl

import com.shreyash.BankApplication.dto.ChangePasswordDetails
import com.shreyash.BankApplication.dto.EmailDetails
import com.shreyash.BankApplication.dto.ForgotPasswordDetails
import com.shreyash.BankApplication.repository.ContactRepository
import com.shreyash.BankApplication.repository.UserRepository
import com.shreyash.BankApplication.service.EmailServiceInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class EmailService: EmailServiceInterface{


    @Autowired
    private lateinit var mailSender: JavaMailSender

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var contactRepository: ContactRepository

    @Value("\${spring.mail.username}")
    private val mailFrom: String? = null


//    fun sendPhoneNotification(phoneNumber: String, message: String?) {
//        val mail = SimpleMailMessage()
//        mail.setTo("$phoneNumber@carrier.com")
//        mail.subject = "Phone Notification"
//        mail.text = message
//        mailSender!!.send(mail)
//    }


    @Throws(Exception::class)
    override fun sendEmail(emaildetails: EmailDetails) {
        try {
            val simplemailmessage = SimpleMailMessage()
            simplemailmessage.from = mailFrom
            simplemailmessage.setTo(emaildetails.recipient)
            simplemailmessage.subject = emaildetails.subject
            simplemailmessage.text = emaildetails.emailBody
            mailSender.send(simplemailmessage)
        } catch (e: Exception) {
            throw Exception("Email sending failed")
        }
    }


    // is authenticated process token validation is done before
    @Throws(Exception::class)
    override fun changePassword(changePasswordDetails: ChangePasswordDetails): String {
        val newPassword = changePasswordDetails.newPassword
        val customerID = changePasswordDetails.customerID
        if (userRepository.existsByCustomerID(customerID) == false) {
            return "ACCOUNT-NOT-EXIST"
        }
        val user = userRepository.findByCustomerID(customerID)
        val contact = user!!.contact
        user.password = passwordEncoder.encode(newPassword)
        contact!!.user = user
        userRepository.save(user)
        contactRepository.save(contact)
        val emailDetails = EmailDetails(
            contact.email,
            "Password CHANGE SUCCESS",
            """Password for the user: ${user.firstName} ${user.lastName} 

                                    with Customer-ID: ${user.customerID}

                                    has been changed successfully.Please login and confirm."""
        )

        sendEmail(emailDetails)
        return "Password changed successfully. CHECK THE MAIL"
    }


    // is not authenticated uses params phoneNum and panNum(No need for authentication. Sends token for next step of changing password)
    override fun forgotPasswordAuthentication(forgotpassDetails: ForgotPasswordDetails): ResponseEntity<String> {
        val panCardNumber = forgotpassDetails.panCardNumber
        if (contactRepository.existsByPanCard(panCardNumber) == false) {
            return ResponseEntity.notFound().build()
        }
        val user = contactRepository.findByPanCard(panCardNumber)!!.user
        return ResponseEntity.ok(user!!.customerID)
    }


}