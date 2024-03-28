package com.shreyash.BankApplication.service;

import com.shreyash.BankApplication.dto.ChangePasswordDetails;
import com.shreyash.BankApplication.dto.EmailDetails;
import com.shreyash.BankApplication.dto.ForgotPasswordDetails;
import org.hibernate.engine.transaction.jta.platform.internal.ResinJtaPlatform;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface EmailServiceInterface {

    void sendEmail(EmailDetails emaildetails) throws Exception;

    String changePassword(ChangePasswordDetails changePasswordDetails) throws Exception;
    ResponseEntity<String> forgotPasswordAuthentication(ForgotPasswordDetails forgotpassDetails);


}
