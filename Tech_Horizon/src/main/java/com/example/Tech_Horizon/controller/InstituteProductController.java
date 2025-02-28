package com.example.Tech_Horizon.controller;

import com.example.Tech_Horizon.dto.request.InstituteProductRequestDto;
import com.example.Tech_Horizon.dto.response.ResponseDto;
import com.example.Tech_Horizon.service.InstituteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/institute")
public class InstituteProductController
{
    private final InstituteProductService instituteProductService;

    @Autowired
    public InstituteProductController(InstituteProductService instituteProductService)
    {
        this.instituteProductService = instituteProductService;
    }



}
