package com.shreyash.BankApplication.Service;

import com.shreyash.BankApplication.DTO.EmailDetails;

public interface EmailServiceInterface {

    void sendEmail(EmailDetails emaildetails) throws Exception;


}
