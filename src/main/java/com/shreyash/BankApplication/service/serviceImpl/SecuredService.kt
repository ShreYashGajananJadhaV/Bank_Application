package com.shreyash.BankApplication.service.serviceImpl

import com.shreyash.BankApplication.authorities.AccountType
import com.shreyash.BankApplication.dto.*
import com.shreyash.BankApplication.entity.Account
import com.shreyash.BankApplication.repository.AccountRepository
import com.shreyash.BankApplication.repository.ContactRepository
import com.shreyash.BankApplication.repository.UserRepository
import com.shreyash.BankApplication.service.SecuredServiceInterface
import com.shreyash.BankApplication.utils.AccountUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.math.BigDecimal


@Service
class SecuredService : SecuredServiceInterface {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var contactRepository: ContactRepository

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder


    override fun createAccount(userRequest: UserRequest): ResponseEntity<BankResponse> {

        val panCardNumber = userRequest.panCardNumber
        var contact = contactRepository.findByPanCard(panCardNumber)

        var user = contact!!.user
        val accounts = contact.account

        val accountTypeByUser = userRequest.accountType

        val type: AccountType
        if (accountTypeByUser == AccountType.SAVINGS) {
            type = AccountType.SAVINGS
            for (account in accounts!!) {
                if (account.accountType == AccountType.SAVINGS) {
                    return ResponseEntity.ok(
                        BankResponse(
                            AccountUtils.ACCOUNT_EXISTS_CODE,
                            AccountUtils.ACCOUNT_EXISTS_MESSAGE,
                            AccountInfo(
                                customerID = contact.user!!.customerID,
                                accountHolderName = user!!.firstName,
                                accountBalance = account.accountBalance,
                                accountNumber = account.accountNumber,
                                accountType = AccountType.SAVINGS
                            )
                        )
                    )
                }
            }


            val newAccount = Account(
                accountNumber = AccountUtils.generateAccountNumber(),
                pinCode = passwordEncoder.encode(userRequest.pinCode),
                accountBalance = userRequest.creditMoney,
                accountType = type
            )

            newAccount.contact = contact
            accountRepository.save(newAccount)

            accounts.plusElement(newAccount)
            contact.account = accounts
            contact = contactRepository.save(contact)

            user!!.contact = contact
            user = userRepository.save(user)

            return ResponseEntity.ok(
                BankResponse(
                    responseCode = AccountUtils.ACCOUNT_CREATION_CODE,
                    responseMessage = AccountUtils.ACCOUNT_CREATION_MESSAGE,
                    accountInfo = AccountInfo(
                        customerID = contact.user!!.customerID,
                        accountHolderName = user.firstName,
                        accountBalance = newAccount.accountBalance,
                        accountNumber = newAccount.accountNumber,
                        accountType = AccountType.SAVINGS
                    )
                )
            )
        }
        type = AccountType.CURRENT
        for (account in accounts!!) {
            if (account.accountType == AccountType.CURRENT) {
                return ResponseEntity.ok(
                    BankResponse(
                        AccountUtils.ACCOUNT_EXISTS_CODE,
                        AccountUtils.ACCOUNT_EXISTS_MESSAGE,
                        AccountInfo(
                            contact.user!!.customerID,
                            user!!.firstName,
                            account.accountBalance,
                            account.accountNumber,
                            AccountType.CURRENT
                        )
                    )
                )
            }
        }
        val newAccount = Account(
            accountNumber = AccountUtils.generateAccountNumber(),
            pinCode = passwordEncoder.encode(userRequest.pinCode),
            accountBalance = userRequest.creditMoney,
            accountType = type
        )

        newAccount.contact = contact
        accountRepository.save(newAccount)

        accounts.plusElement(newAccount)
        contact.account = accounts
        contactRepository.save(contact)

        user!!.contact = contact
        userRepository.save(user)

        return ResponseEntity.ok(
            BankResponse(
                responseCode = AccountUtils.ACCOUNT_CREATION_CODE,
                responseMessage = AccountUtils.ACCOUNT_CREATION_MESSAGE,
                accountInfo = AccountInfo(
                    customerID = contact.user!!.customerID,
                    accountHolderName = user.firstName,
                    accountBalance = newAccount.accountBalance,
                    accountNumber = newAccount.accountNumber,
                    accountType = AccountType.CURRENT
                )
            )
        )
    }

    override fun balanceEnquiry(enquiryRequest: EnquiryRequest): ResponseEntity<AccountInfo> {
        val accountExists = accountRepository.existsByAccountNumber(enquiryRequest.accountNumber)
        if (!accountExists) {
            return ResponseEntity.ok(
                AccountInfo(
                    customerID = AccountUtils.USER_NO_DATA_FOUND,
                    accountHolderName = AccountUtils.USER_NO_DATA_FOUND,
                    accountBalance = BigDecimal.ZERO,
                    accountNumber = enquiryRequest.accountNumber,
                    accountType = AccountType.NOT_MENTIONED
                )
            )
        }

        val account = accountRepository.findByAccountNumber(enquiryRequest.accountNumber)
        if (!passwordEncoder.matches(enquiryRequest.pinCode, account!!.pinCode)) {
            return ResponseEntity.ok(
                AccountInfo(
                    customerID = AccountUtils.USER_INVALID_MESSAGE,
                    accountHolderName = AccountUtils.USER_INVALID_MESSAGE,
                    accountBalance = BigDecimal.ZERO,
                    accountNumber = AccountUtils.USER_INVALID_MESSAGE,
                    accountType = AccountType.NOT_MENTIONED
                )
            )
        }

        val contact = account.contact
        val user = contact!!.user

        return ResponseEntity.ok(
            AccountInfo(
                customerID = user!!.customerID,
                accountHolderName = user.firstName + " " + user.lastName,
                accountBalance = account.accountBalance,
                accountNumber = account.accountNumber,
                accountType = account.accountType
            )
        )

    }


    override fun creditAccount(creditDebitRequest: CreditDebitRequest): ResponseEntity<AccountInfo> {

        val accountExists = accountRepository.existsByAccountNumber(creditDebitRequest.accountNumber)
        if (!accountExists) {
            return ResponseEntity.ok(
                AccountInfo(
                    customerID = AccountUtils.USER_ABSENT_MESSAGE,
                    accountHolderName = AccountUtils.USER_ABSENT_MESSAGE,
                    accountBalance = BigDecimal.ZERO,
                    accountNumber = AccountUtils.USER_ABSENT_MESSAGE,
                    accountType = AccountType.NOT_MENTIONED
                )
            )
        }

        var account = accountRepository.findByAccountNumber(creditDebitRequest.accountNumber)
        val contact = account!!.contact
        val user = contact!!.user

        if (!passwordEncoder.matches(creditDebitRequest.pinCode, account.pinCode)) {
            return ResponseEntity.badRequest().body(
                AccountInfo(
                    customerID = AccountUtils.USER_INVALID_MESSAGE,
                    accountHolderName = AccountUtils.USER_INVALID_MESSAGE,
                    accountBalance = BigDecimal.ZERO,
                    accountNumber = AccountUtils.USER_INVALID_MESSAGE,
                    accountType = AccountType.NOT_MENTIONED
                )
            )
        }

        account.accountBalance = account.accountBalance.add(creditDebitRequest.amount)
        account = accountRepository.save(account)

        return ResponseEntity.ok(
            AccountInfo(
                customerID = user!!.customerID,
                accountHolderName = user.firstName + " " + user.lastName,
                accountBalance = account.accountBalance,
                accountNumber = account.accountNumber,
                accountType = account.accountType
            )
        )
    }

    override fun debitAccount(creditDebitRequest: CreditDebitRequest): ResponseEntity<AccountInfo> {
        val accountExists = accountRepository.existsByAccountNumber(creditDebitRequest.accountNumber)

        /* check if the account exists*/
        if (!accountExists) {
            return ResponseEntity.ok(
                AccountInfo(
                    customerID = AccountUtils.USER_ABSENT_MESSAGE,
                    accountHolderName = AccountUtils.USER_ABSENT_MESSAGE,
                    accountBalance = BigDecimal.ZERO,
                    accountNumber = AccountUtils.USER_ABSENT_MESSAGE,
                    accountType = AccountType.NOT_MENTIONED
                )
            )
        }
        var account = accountRepository.findByAccountNumber(creditDebitRequest.accountNumber)
        val contact = account!!.contact
        val user = contact!!.user

        if (!passwordEncoder.matches(creditDebitRequest.pinCode, account.pinCode)) {
            return ResponseEntity.badRequest().body(
                AccountInfo(
                    customerID = AccountUtils.USER_INVALID_MESSAGE,
                    accountHolderName = AccountUtils.USER_INVALID_MESSAGE,
                    accountBalance = BigDecimal.ZERO,
                    accountNumber = AccountUtils.USER_INVALID_MESSAGE,
                    accountType = AccountType.NOT_MENTIONED
                )
            )
        }

        val currentBalance = account.accountBalance
        val withdrawalAmount = creditDebitRequest.amount

        /*check if the withdrawal amount is less than amount in the account+500(safety measure)*/
        if (currentBalance.compareTo(withdrawalAmount.add(BigDecimal.valueOf(500))) <= 0) {
            return ResponseEntity.ok(
                AccountInfo(
                    customerID = user!!.customerID,
                    accountHolderName = AccountUtils.LESS_AMOUNT_TO_DEBIT,
                    accountBalance = account.accountBalance,
                    accountNumber = account.accountNumber,
                    accountType = account.accountType
                )
            )
        }

        /*amount successful withdrawal and confirmation*/
        account.accountBalance = account.accountBalance.subtract(creditDebitRequest.amount)
        account = accountRepository.save(account)
        return ResponseEntity.ok(
            AccountInfo(
                customerID = user!!.customerID,
                accountHolderName = user.firstName + " " + user.lastName,
                accountBalance = account.accountBalance,
                accountNumber = account.accountNumber,
                accountType = account.accountType
            )
        )
    }


    fun getAccounts(customerID: String?): ResponseEntity<List<AccountDetail>> {
        val user = userRepository.findByCustomerID(customerID!!)
        val contact = user!!.contact
        val accounts = contact!!.account

        val accountDetails: MutableList<AccountDetail> = ArrayList()

        for (account in accounts!!) {
            val accountDetail = AccountDetail(
                holderName = user.firstName + " " + user.lastName,
                accountNumber = account.accountNumber,
                createdDate = account.createdAt!!,
                balance = account.accountBalance,
                accountType = account.accountType
            )
            accountDetails.add(accountDetail)
        }
        return ResponseEntity.ok().body(accountDetails)
    }

}