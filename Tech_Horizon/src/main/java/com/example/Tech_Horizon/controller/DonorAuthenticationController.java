package com.example.Tech_Horizon.controller;

import com.example.Tech_Horizon.dto.request.SignInRequestDto;
import com.example.Tech_Horizon.dto.request.DonorSignUpRequestDto;
import com.example.Tech_Horizon.dto.response.ResponseDto;
import com.example.Tech_Horizon.service.DonorAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class DonorAuthenticationController
{

    private final DonorAuthenticationService donorAuthenticationService;

    @Autowired
    public DonorAuthenticationController(DonorAuthenticationService donorAuthenticationService) {
        this.donorAuthenticationService = donorAuthenticationService;
    }

    @PostMapping("/donor/sign-up")
    public ResponseEntity<ResponseDto> signUpDonor(@RequestBody DonorSignUpRequestDto dto)
    {
        return new ResponseEntity<>(donorAuthenticationService.signUpDonor(dto), HttpStatus.OK);
    }

    @PostMapping("/donor/sign-in")
    public ResponseEntity<ResponseDto> signInDonor(@RequestBody SignInRequestDto dto)
    {
        return new ResponseEntity<>(donorAuthenticationService.signInDonor(dto),HttpStatus.OK);
    }

}
