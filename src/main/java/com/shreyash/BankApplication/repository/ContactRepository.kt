package com.shreyash.BankApplication.repository;

import com.shreyash.BankApplication.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {


    Boolean existsByPhoneNumber(String phoneNumber);

    Contact findByPhoneNumber(String PhoneNumber);

    Boolean existsByPanCard(String pancardNumber);

    Contact findByPanCard(String pancardNumber);

}
