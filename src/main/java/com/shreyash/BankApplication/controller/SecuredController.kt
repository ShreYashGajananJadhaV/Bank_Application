package com.shreyash.BankApplication.controller

import com.shreyash.BankApplication.dto.*
import com.shreyash.BankApplication.service.serviceImpl.EmailService
import com.shreyash.BankApplication.service.serviceImpl.SecuredService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/acc")
class SecuredController {

    @Autowired
    private lateinit var securedService: SecuredService;

    @Autowired
    private lateinit var emailService: EmailService;


    @PostMapping("/create-account")
    fun createAccount(
        @RequestBody userRequest: UserRequest): ResponseEntity<BankResponse> {
        return securedService.createAccount(userRequest)
    }
    @PostMapping("/balance")
    fun balanceEnquiry(
        @RequestBody enquiryRequest: EnquiryRequest): ResponseEntity<AccountInfo> {
        return securedService.balanceEnquiry(enquiryRequest)
    }

    @PutMapping("/credit")
    fun creditAccount(@RequestBody creditDebitRequest: CreditDebitRequest): ResponseEntity<AccountInfo> {
        return securedService.creditAccount(creditDebitRequest)
    }

    @PutMapping("/debit")
    fun debitAccount(@RequestBody creditDebitRequest: CreditDebitRequest): ResponseEntity<AccountInfo> {
        return securedService.debitAccount(creditDebitRequest)
    }


    @PostMapping("/change-password")
    fun changePassword(@RequestBody changePasswordDetails: ChangePasswordDetails): String {
        return emailService.changePassword(changePasswordDetails)
    }

    @PostMapping("/accounts")
    fun getAccounts(@RequestParam customerID:String): ResponseEntity<List<AccountDetail>>{
        return securedService.getAccounts(customerID);
    }

}