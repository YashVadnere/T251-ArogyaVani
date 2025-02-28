package com.example.Tech_Horizon.controller;

import com.example.Tech_Horizon.dto.request.SignInRequestDto;
import com.example.Tech_Horizon.dto.request.SupplierSignUpRequestDto;
import com.example.Tech_Horizon.dto.response.ResponseDto;
import com.example.Tech_Horizon.service.SupplierAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SupplierAuthenticationController
{
    private final SupplierAuthenticationService supplierAuthenticationService;

    @Autowired
    public SupplierAuthenticationController(
            SupplierAuthenticationService supplierAuthenticationService
    ) {
        this.supplierAuthenticationService = supplierAuthenticationService;
    }

    @PostMapping("/supplier/sign-up")
    public ResponseEntity<ResponseDto> signUpSupplier(@RequestBody SupplierSignUpRequestDto dto)
    {
        return new ResponseEntity<>(supplierAuthenticationService.signUpSupplier(dto), HttpStatus.OK);
    }

    @PostMapping("/supplier/sign-in")
    public ResponseEntity<ResponseDto> signInSupplier(@RequestBody SignInRequestDto dto)
    {
        return new ResponseEntity<>(supplierAuthenticationService.signInSupplier(dto),HttpStatus.OK);
    }

}
