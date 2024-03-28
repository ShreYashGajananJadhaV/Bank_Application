package com.shreyash.BankApplication.service;

import com.shreyash.BankApplication.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface PermitServiceInterface {

    public ResponseEntity<CreateProfileResponse> createProfile(CreateProfileRequest userRequest) throws Exception;

    public ResponseEntity<SignInResponse> signIn(SignInRequest signInRequest);


}
