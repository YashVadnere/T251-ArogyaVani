package com.example.Tech_Horizon.service;

import com.example.Tech_Horizon.dto.request.InstituteProductRequestDto;
import com.example.Tech_Horizon.entity.InstituteProduct;
import com.example.Tech_Horizon.repository.InstituteProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstituteProductService
{
    private final InstituteProductRepository instituteProductRepository;

    @Autowired
    public InstituteProductService(InstituteProductRepository instituteProductRepository)
    {
        this.instituteProductRepository = instituteProductRepository;
    }


    private InstituteProduct instituteProductDtoMapper(InstituteProductRequestDto dto)
    {
        InstituteProduct instituteProduct=new InstituteProduct();
        instituteProduct.setProductName(dto.getProductName());
        instituteProduct.setCategory(dto.getCategory());
        instituteProduct.setStockAvailable(dto.getStockAvailable());
        return instituteProduct;
    }
}
