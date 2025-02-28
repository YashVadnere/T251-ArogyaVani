package com.example.Tech_Horizon.controller;

import com.example.Tech_Horizon.service.InstituteProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
