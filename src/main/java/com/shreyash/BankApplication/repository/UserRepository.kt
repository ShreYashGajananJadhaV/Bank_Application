package com.shreyash.BankApplication.repository

import com.shreyash.BankApplication.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User,Int> {

    fun existsByCustomerID(customerID: String?): Boolean?
    fun findByCustomerID(customerID: String?): User?
}