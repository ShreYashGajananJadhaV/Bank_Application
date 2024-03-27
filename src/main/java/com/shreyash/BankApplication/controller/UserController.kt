package com.shreyash.BankApplication.controller

import com.shreyash.BankApplication.dto.*
import com.shreyash.BankApplication.service.serviceImpl.EmailService
import com.shreyash.BankApplication.service.serviceImpl.PermitSerivce
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:8080"])
@RequestMapping("/api")
@RestController
class UserController {

    @Autowired
    private lateinit var permitService:PermitSerivce

    @Autowired
    private lateinit var emailService: EmailService;



    @PostMapping("/create-profile")
    fun createProfile(@RequestBody createProfileRequest: CreateProfileRequest): ResponseEntity<CreateProfileResponse>{
        return permitService.createProfile(createProfileRequest);
    }

    @PostMapping("/forgot-password")
    fun forgotPassword(@RequestBody forgotpassDetails: ForgotPasswordDetails): ResponseEntity<String> {
        return emailService.forgotPasswordAuthentication(forgotpassDetails);
    }


    @PostMapping("/signin")
    fun signIn(@RequestBody signInRequest: SignInRequest): ResponseEntity<SignInResponse> {
        return permitService.signIn(signInRequest)
    }
}