package com.shreyash.BankApplication.repository

import com.shreyash.BankApplication.entity.Contact
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ContactRepository: JpaRepository<Contact,Long> {

    fun existsByPhoneNumber(phoneNumber: String?): Boolean?

    fun findByPhoneNumber(phoneNumber: String?): Contact?

    fun existsByPanCard(panCardNumber: String?): Boolean?

    fun findByPanCard(panCardNumber: String?): Contact?
}