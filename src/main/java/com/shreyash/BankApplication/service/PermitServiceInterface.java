package com.shreyash.BankApplication.service;

import com.shreyash.BankApplication.dto.*;
import org.springframework.http.ResponseEntity;

public interface PermitServiceInterface {

    public ResponseEntity<CreateProfileResponse> createProfile(CreateProfileRequest userRequest) throws Exception;

    public ResponseEntity<SignInResponse> signIn(SignInRequest signInRequest);


}
