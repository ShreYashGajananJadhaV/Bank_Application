package com.shreyash.BankApplication.repository;

import com.shreyash.BankApplication.entity.User;
import org.reactivestreams.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {


    public Boolean existsByCustomerID(String customerID);
    public User findByCustomerID(String customerID);

}
