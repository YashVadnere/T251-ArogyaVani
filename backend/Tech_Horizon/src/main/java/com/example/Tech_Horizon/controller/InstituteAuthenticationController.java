package com.example.Tech_Horizon.controller;

import com.example.Tech_Horizon.dto.request.InstituteSignUpRequestDto;
import com.example.Tech_Horizon.dto.request.SignInRequestDto;
import com.example.Tech_Horizon.dto.response.ResponseDto;
import com.example.Tech_Horizon.service.InstituteAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class InstituteAuthenticationController
{
    private final InstituteAuthenticationService instituteAuthenticationService;

    @Autowired
    public InstituteAuthenticationController(InstituteAuthenticationService instituteAuthenticationService)
    {
        this.instituteAuthenticationService = instituteAuthenticationService;
    }

    @PostMapping("/institute/sign-up")
    public ResponseEntity<ResponseDto> signUpInstitute(@RequestBody InstituteSignUpRequestDto dto)
    {
        return new ResponseEntity<>(instituteAuthenticationService.signUpInstitute(dto), HttpStatus.OK);
    }

    @PostMapping("/institute/sign-in")
    public ResponseEntity<ResponseDto> signInIntitute(@RequestBody SignInRequestDto dto)
    {
        return new ResponseEntity<>(instituteAuthenticationService.signInIntitute(dto),HttpStatus.OK);
    }

}
